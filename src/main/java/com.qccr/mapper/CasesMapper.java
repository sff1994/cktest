package com.qccr.mapper;

import com.qccr.common.CaseEditVO;
import com.qccr.common.CaseListVO;
import com.qccr.pojo.Cases;
import java.util.List;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.One;
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
public interface CasesMapper extends BaseMapper<Cases> {

	@Select("SELECT * FROM cases WHERE suite_id = #{suiteId}")
	List<Cases> findAll(Integer suiteId);
	
	@Select("SELECT DISTINCT t1.*, t6.id apiId, t6.url apiUrl FROM cases t1 JOIN suite t2 ON t1.suite_id = t2.id JOIN project t3 ON t2.project_id = t3.id JOIN case_param_value t4 ON t1.id = t4.case_id JOIN api_request_param t5 ON t4.api_request_param_id = t5.id JOIN api t6 ON t5.api_id = t6.id WHERE t3.id = #{projectId}")
	@Results({
		//列，属性
		@Result(column="id",property="id"),
		@Result(property="testReport",column="id",
		one=@One(select="com.qccr.mapper.TestReportMapper.findByCaseId"))
	})
	List<CaseListVO> showCaseUnderProject(Integer projectId);
	@Select("SELECT DISTINCT t1.*,t6.id apiId, t6.url apiUrl FROM cases t1 JOIN suite t2 ON t1.suite_id = t2.id JOIN case_param_value t4 ON t1.id = t4.case_id JOIN api_request_param t5 ON t4.api_request_param_id = t5.id JOIN api t6 ON t5.api_id = t6.id WHERE t1.suite_id=#{suiteId}")
	@Results({
		//列，属性
		@Result(column="id",property="id"),
		@Result(property="testReport",column="id",
		one=@One(select="com.qccr.mapper.TestReportMapper.findByCaseId"))
	})
	List<CaseListVO> findCaseUnderSuite(Integer suiteId);
	
	@Select("SELECT DISTINCT t1.*, t6.id apiId, t6.method,t6.url FROM cases t1 JOIN case_param_value t2 ON t2.case_id = t1.id JOIN api_request_param t3 ON t2.api_request_param_id = t3.id JOIN api t6 ON t3.api_id = t6.id WHERE t1.id = #{caseId}")
	@Results({
		//列，属性
		@Result(column="id",property="id"),
		@Result(property="requestParams",column="id",
		many=@Many(select="com.qccr.mapper.ApiRequestParamMapper.findByCase")),
		@Result(property="testRules",column="id",
		many=@Many(select="com.qccr.mapper.TestRuleMapper.findByCase"))
	})
	CaseEditVO findCaseEditVO(Integer caseId);
}
