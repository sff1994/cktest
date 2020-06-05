package com.qccr.service;

import com.qccr.common.ReportVO;
import com.qccr.pojo.TestReport;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kk
 * @since 2020-02-23
 */
public interface TestReportService extends IService<TestReport> {

	public List<TestReport> run (Integer suiteId);

	public TestReport findByCaseId(Integer caseId);

	public ReportVO getReport(Integer suiteId);
}
