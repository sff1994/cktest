package com.qccr.service.impl;

import com.qccr.pojo.Api;
import com.qccr.pojo.ApiRequestParam;
import com.qccr.common.ApiListVO;
import com.qccr.common.ApiRunResult;
import com.qccr.common.ApiVO;
import com.qccr.mapper.ApiMapper;
import com.qccr.service.ApiService;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

/**
 * <p>
 * 服务实现类
 * 后面讲put和delete也实现掉
 * </p>
 *
 * @author kk
 * @since 2020-02-23
 */
@Service
public class ApiServiceImpl extends ServiceImpl<ApiMapper, Api> implements ApiService {

	@Autowired
	ApiMapper apiMapper;

	@Override
	public List<ApiListVO> showApiListByProject(Integer projectId) {
		// TODO Auto-generated method stub
		return apiMapper.showApiListByProject(projectId);
	}

	@Override
	public List<ApiListVO> showApiListByApiClassification(Integer apiClassificationId) {
		// TODO Auto-generated method stub
		return apiMapper.showApiListByApiClassification(apiClassificationId);
	}

	@Override
	public ApiVO findApiViewVO(Integer apiId) {
		// TODO Auto-generated method stub
		return apiMapper.findApiViewVO(apiId);
	}

	@Override
	public ApiRunResult run(ApiVO apiRunVo){
		//TODO加一个有参构造函数来支持https，通过对url截取判断来调用方法
//		RestTemplate restTemplate = new RestTemplate(httpsFactory);
		// 远程调用,spring4版本以上都支持 只支持http协议
		RestTemplate restTemplate = new RestTemplate();
		String url = apiRunVo.getHost() + apiRunVo.getUrl();
		String method = apiRunVo.getMethod();
		List<ApiRequestParam> apiRequestParams = apiRunVo.getRequestParams();
		LinkedMultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		//{"json":["{"projectId":"9","name":"sfftestjson44"}"]}
		LinkedMultiValueMap<String, String> bodyParams = new LinkedMultiValueMap<String, String>();
		String paramStr = "?";
		String jsonStr = "";
		for (ApiRequestParam apiRequestParam : apiRequestParams) {
			if (apiRequestParam.getType() == 3) {// 头

				headers.add(apiRequestParam.getName(), apiRequestParam.getValue());
			}else if (apiRequestParam.getType() == 1) {
				paramStr += apiRequestParam.getName()+"="+apiRequestParam.getValue()+"&";
				
			}else if(apiRequestParam.getType() == 2) {
				// body 2 4,注意此时type==1时没有处理
				bodyParams.add(apiRequestParam.getName(), apiRequestParam.getValue());
			}else {
				//仅仅处理type==4的情况，适用正常的json的处理
				jsonStr = apiRequestParam.getValue();
				bodyParams.add(apiRequestParam.getName(), jsonStr);
			}
		}
		if (!"?".equals(paramStr)) {
			paramStr = paramStr.substring(0, paramStr.lastIndexOf("&"));
		}
		HttpEntity httpEntity = null;
		ResponseEntity responseEntity = null;
		ApiRunResult apiRunResult = new ApiRunResult();
		
		try {
			if ("get".equalsIgnoreCase(method)) {
				// httpEntity = new HttpEntity(headers);
				httpEntity = new HttpEntity(bodyParams, headers);
				responseEntity = restTemplate.exchange(url+paramStr, HttpMethod.GET, httpEntity, String.class);
			} else if ("post".equalsIgnoreCase(method)) {
				if (jsonStr=="") {
					httpEntity = new HttpEntity(bodyParams, headers);
					responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
				}else {
					httpEntity = new HttpEntity(jsonStr, headers);
					responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
				}
				
			}
			apiRunResult.setStatusCode(responseEntity.getStatusCodeValue() + "");
			apiRunResult.setBody(responseEntity.getBody().toString());
			HttpHeaders headerResult = responseEntity.getHeaders();
			// 将java--json字符串
			// apiRunResult.setHeaders(new ObjectMapper().writeValueAsString(headerResult));
			// fastjson
			apiRunResult.setHeaders(JSON.toJSONString(headerResult));
		} catch (HttpStatusCodeException e) {
			//注意此时有调用异常情况，往往没有body
			apiRunResult.setStatusCode(e.getRawStatusCode()+"");
			apiRunResult.setHeaders(JSON.toJSONString(e.getResponseHeaders()));
			apiRunResult.setBody(e.getResponseBodyAsString());
		}
		return apiRunResult;
	}

}
