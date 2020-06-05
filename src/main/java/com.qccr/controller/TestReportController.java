package com.qccr.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.qccr.common.ReportVO;
import com.qccr.common.Result;
import com.qccr.pojo.TestReport;
import com.qccr.service.TestReportService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author kk
 * @since 2020-02-23
 */
@RestController
@RequestMapping("/testReport")
public class TestReportController {

	@Autowired
	TestReportService testReportService;
	
	@RequestMapping("/run")
	public Result run(Integer suiteId){
		List<TestReport> reportList = testReportService.run(suiteId);
		return new Result("1", reportList,"测试执行成功");
	}
	
	@RequestMapping("/findCaseRunResult")
	public Result findCaseRunResult(Integer caseId){
		TestReport report = testReportService.findByCaseId(caseId);
		return new Result("1", report,"测试报告成功");
	}
	
	@RequestMapping("/get")
	public Result get(Integer suiteId){
		ReportVO report = testReportService.getReport(suiteId);
		return new Result("1", report,"测试报告get成功");
	}
}
