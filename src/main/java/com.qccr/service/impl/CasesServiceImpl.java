package com.qccr.service.impl;

import com.qccr.pojo.ApiRequestParam;
import com.qccr.pojo.CaseParamValue;
import com.qccr.pojo.Cases;
import com.qccr.common.ApiVO;
import com.qccr.common.CaseEditVO;
import com.qccr.common.CaseListVO;
import com.qccr.mapper.CasesMapper;
import com.qccr.service.CaseParamValueService;
import com.qccr.service.CasesService;
import com.qccr.service.TestRuleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author kk
 * @since 2020-02-23
 */
@Service
public class CasesServiceImpl extends ServiceImpl<CasesMapper, Cases> implements CasesService {

	@Autowired
	CaseParamValueService caseParamValueService;
	@Autowired
	CasesMapper casesMapper;
	@Autowired
	TestRuleService testRuleService;

	@Override
	@PostMapping("/add")
	public void add(Cases caseVo, ApiVO apiRunVO) {
		// 添加到cases
		this.save(caseVo);
		// 批量添加到case_param_value
		List<ApiRequestParam> requestParams = apiRunVO.getRequestParams();

		List<CaseParamValue> caseParamValues = new ArrayList<CaseParamValue>();
		for (ApiRequestParam apiRequestParam : requestParams) {
			CaseParamValue caseParamValue = new CaseParamValue();
			caseParamValue.setCaseId(caseVo.getId());
			caseParamValue.setApiRequestParamId(apiRequestParam.getId());
			caseParamValue.setApiRequestParamValue(apiRequestParam.getValue());
			caseParamValues.add(caseParamValue);
		}
		caseParamValueService.saveBatch(caseParamValues);

	}

	@Override
	public List<CaseListVO> showCaseUnderProject(Integer projectId) {
		// TODO Auto-generated method stub
		return casesMapper.showCaseUnderProject(projectId);
	}

	@Override
	public List<CaseListVO> findCaseUnderSuite(Integer suiteId) {
		// TODO Auto-generated method stub
		return casesMapper.findCaseUnderSuite(suiteId);
	}

	@Override
	public CaseEditVO findCaseEditVO(Integer caseId) {
		// TODO Auto-generated method stub
		return casesMapper.findCaseEditVO(caseId);
	}

	@Override
	public void updateCase(CaseEditVO caseEditVo) {
		// 此时只能更新case表和case_param_value表，还需要更新test_rule表
		updateById(caseEditVo);
		List<ApiRequestParam> requestParams = caseEditVo.getRequestParams();
		
		List<CaseParamValue> list = new ArrayList<CaseParamValue>();
		for (ApiRequestParam apiRequestParam : requestParams) {
			CaseParamValue caseParamValue = new CaseParamValue();
			caseParamValue.setId(apiRequestParam.getValueId());
			caseParamValue.setApiRequestParamId(apiRequestParam.getId());
			caseParamValue.setApiRequestParamValue(apiRequestParam.getValue());
			caseParamValue.setCaseId(caseEditVo.getId());
			
			list.add(caseParamValue);
		}
		caseParamValueService.updateBatchById(list);
		//根据case_id先删除test_rule，再insert到test_rule
		QueryWrapper queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("case_id", caseEditVo.getId());
		testRuleService.remove(queryWrapper);
		testRuleService.saveBatch(caseEditVo.getTestRules());
		
	}
}
