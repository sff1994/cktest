package com.qccr.mapper;

import com.qccr.common.CaseEditVO;
import com.qccr.common.ReportVO;
import com.qccr.pojo.TestReport;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author kk
 * @since 2020-02-23
 */
public interface TestReportMapper extends BaseMapper<TestReport> {

	@Select("SELECT DISTINCT t1.*, t6.id apiId, t6.method, t6.url url, t3. HOST FROM cases t1 JOIN suite t2 ON t1.suite_id = t2.id JOIN project t3 ON t2.project_id = t3.id JOIN case_param_value t4 ON t1.id = t4.case_id JOIN api_request_param t5 ON t4.api_request_param_id = t5.id JOIN api t6 ON t5.api_id = t6.id WHERE t2.id = #{suiteId}")
	@Results({
		//列，属性
		@Result(column="id",property="id"),
		@Result(property="requestParams",column="id",
		many=@Many(select="com.qccr.mapper.ApiRequestParamMapper.findByCase")),
		@Result(property="testRules",column="id",
		many=@Many(select="com.qccr.mapper.TestRuleMapper.findByCase"))
	})
	List<CaseEditVO> findCaseEditVoUnderSuite(Integer suiteId);
	
	@Delete("DELETE FROM test_report WHERE case_id IN (SELECT case_id FROM suite id=#{suiteId}")
	void deleteReport(Integer suiteId);
	
	@Select("select * from test_report where case_id = #{caseId}")
	TestReport findByCaseId(Integer caseId);
	
	@Select("select * from suite where id = #{suiteId}")
	@Results({
		//列，属性
		@Result(column="id",property="id"),
		@Result(property="caseList",column="id",
		many=@Many(select="com.qccr.mapper.CasesMapper.findCaseUnderSuite")),
	})
	public ReportVO getReport(Integer suiteId);
}
