package com.qccr.controller;


import java.util.List;

import com.qccr.common.ApiVO;
import com.qccr.common.CaseEditVO;
import com.qccr.common.CaseListVO;
import com.qccr.common.Result;
import com.qccr.pojo.Cases;
import com.qccr.service.CasesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;



/**
 * <p>
 *  把一次调用的接口数据固化到持续数据化的测试用例，可反复调用和执行
 *  是同一事务，写在服务层，同成功或同失败会滚。后面要将将编辑参数的保存也这样处理
 * </p>
 *1、根据projectId获取suite
 *2、添加到cases
 *3、批量添加到case_param_value
 * @author kk
 * @since 2020-02-23
 */
@RestController
@RequestMapping("/cases")
public class CasesController {
	@Autowired
	CasesService casesService ;

	@PostMapping("/add")
	public Result add(Cases caseVo, ApiVO apiRunVO) {
		// 添加到cases
		casesService.add(caseVo, apiRunVO);
		return new Result("1", "添加测试集成功");
	}
	@GetMapping("/showCaseUnderProject")
	public Result showCaseUnderProject(Integer projectId) {
		// 添加到cases
		List<CaseListVO> listCases = casesService.showCaseUnderProject(projectId);
		return new Result("1", listCases,"查询所有用例成功");
	}
	@GetMapping("/findCaseUnderSuite")
	public Result findCaseUnderSuite(Integer suiteId) {
		// 添加到cases
		List<CaseListVO> listCasesSuite = casesService.findCaseUnderSuite(suiteId);
		return new Result("1", listCasesSuite,"查询用例集成功");
	}
	
	@GetMapping("/toCaseEdit")
	public Result toCaseEdit(Integer caseId) {
		// 添加到cases
		CaseEditVO caseEditVo = casesService.findCaseEditVO(caseId);
		return new Result("1", caseEditVo,"跳转到用例详情成功");
	}
	
	@PutMapping("/update")
	public Result updateCase(CaseEditVO caseEditVo) {
		// 添加到cases
		 casesService.updateCase(caseEditVo);
		return new Result("1", caseEditVo,"更新用例成功");
	}
	
}
