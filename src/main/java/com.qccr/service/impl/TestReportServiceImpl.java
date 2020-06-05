package com.qccr.service.impl;

import com.qccr.pojo.ApiRequestParam;
import com.qccr.pojo.TestReport;
import com.qccr.pojo.TestRule;
import com.qccr.pojo.User;
import com.qccr.common.CaseEditVO;
import com.qccr.common.ReportVO;
import com.qccr.mapper.TestReportMapper;
import com.qccr.service.TestReportService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONPath;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * <p>
 * 服务实现类
 * </p>
 *1、先根据suiteId查询
 *2、远程调用api执行
 *3、对test_report表先删除再插入
 * @author kk
 * @since 2020-02-23
 */
@Service
public class TestReportServiceImpl extends ServiceImpl<TestReportMapper, TestReport> implements TestReportService {

	@Autowired
	TestReportMapper testReportMapper;

	@Override
	public List<TestReport> run(Integer suiteId) {
		List<TestReport> reportList = new ArrayList<TestReport>();
		// 1、先根据suiteId查询
		List<CaseEditVO> list = testReportMapper.findCaseEditVoUnderSuite(suiteId);
		for (CaseEditVO caseEditVO : list) {
			// 2、远程调用api执行
			TestReport testReport = runAndGetReport(caseEditVO);
			reportList.add(testReport);
		}
		// 3、对test_report表先删除再插入
		testReportMapper.deleteById(suiteId);
		this.saveBatch(reportList);
		return reportList;
	}

	TestReport runAndGetReport(CaseEditVO caseEditVO) {
		TestReport testReportAll = new TestReport();
		RestTemplate restTemplate = new RestTemplate();
		String url = caseEditVO.getHost() + caseEditVO.getUrl();
		String method = caseEditVO.getMethod();
		List<ApiRequestParam> apiRequestParams = caseEditVO.getRequestParams();
		LinkedMultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		// {"json":["{"projectId":"9","name":"sfftestjson44"}"]}
		LinkedMultiValueMap<String, String> bodyParams = new LinkedMultiValueMap<String, String>();
		String paramStr = "?";
		String jsonStr = "";
		for (ApiRequestParam apiRequestParam : apiRequestParams) {
			if (apiRequestParam.getType() == 3) {// 头

				headers.add(apiRequestParam.getName(), apiRequestParam.getValue());
			} else if (apiRequestParam.getType() == 1) {
				paramStr += apiRequestParam.getName() + "=" + apiRequestParam.getValue() + "&";

			} else if (apiRequestParam.getType() == 2) {
				// body 2 4,注意此时type==1时没有处理
				bodyParams.add(apiRequestParam.getName(), apiRequestParam.getValue());
			} else {
				// 仅仅处理type==4的情况，适用正常的json的处理
				jsonStr = apiRequestParam.getValue();
				bodyParams.add(apiRequestParam.getName(), jsonStr);
			}
		}
		if (!"?".equals(paramStr)) {
			paramStr = paramStr.substring(0, paramStr.lastIndexOf("&"));
		}
		HttpEntity httpEntity = null;
		ResponseEntity responseEntity = null;

		try {
			if ("get".equalsIgnoreCase(method)) {
				// httpEntity = new HttpEntity(headers);
				httpEntity = new HttpEntity(bodyParams, headers);
				responseEntity = restTemplate.exchange(url + paramStr, HttpMethod.GET, httpEntity, String.class);
			} else if ("post".equalsIgnoreCase(method)) {
				if (jsonStr == "") {
					httpEntity = new HttpEntity(bodyParams, headers);
					testReportAll.setRequestBody(JSON.toJSONString(bodyParams));
					responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
				} else {
					httpEntity = new HttpEntity(jsonStr, headers);
					testReportAll.setRequestBody(bodyParams.toString());
					responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
				}

			}
			testReportAll.setCaseId(caseEditVO.getId());
			testReportAll.setRequestUrl(url);
			//将map子集强转城json字符串，因为属性命名是String
			testReportAll.setRequestHeaders(JSON.toJSONString(headers));
			testReportAll.setRequestBody(JSON.toJSONString(bodyParams));
			testReportAll.setResponseHeaders(JSON.toJSONString(responseEntity.getHeaders()));
			testReportAll.setResponseBody(responseEntity.getBody().toString());
			testReportAll.setPassFlag(assertByTestRule(responseEntity.getBody().toString(),caseEditVO.getTestRules()));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return testReportAll;
	}
	String assertByTestRule(String responseBody,List<TestRule> testRules){
		boolean flag = true;
		for (TestRule testRule : testRules) {
			Object value = JSONPath.read(responseBody, testRule.getExpression());
			String actual = (String) value;
			String op = testRule.getOperator();
			if (op.equals("=")) {
				if (!actual.equals(testRule.getExpected())) {
					flag = false;
				}
			}else {
				//contains包含关系
				if (!actual.contains(testRule.getExpected())) {
					flag = false;
				}
			}
		}
		if (!flag) {
			return "不通过";
		}
		return "通过";
		
	}

	@Override
	public TestReport findByCaseId(Integer caseId) {
		
		return testReportMapper.findByCaseId(caseId);
	}

	@Override
	public ReportVO getReport(Integer suiteId) {
		ReportVO reportVO = testReportMapper.getReport(suiteId);
		User mySelf = (User) SecurityUtils.getSubject().getPrincipal();
		reportVO.setUsername(mySelf.getUsername());
		reportVO.setCreateReportTime(new Date());
		return reportVO;
	}

}
