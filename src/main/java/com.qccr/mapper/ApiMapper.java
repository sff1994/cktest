package com.qccr.mapper;

import com.qccr.common.ApiListVO;
import com.qccr.common.ApiVO;
import com.qccr.pojo.Api;

import java.util.List;

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
public interface ApiMapper extends BaseMapper<Api> {

	@Select("SELECT * FROM api WHERE api_classification_id =#{apiClassificationId}")
	public List<Api> findApi(Integer apiClassificationId);
	
	@Select("SELECT t1.*,t2.`name` classificationName FROM api t1,api_classification t2 WHERE t2.project_id = #{projectId} AND t1.api_classification_id = t2.id")
	public List<ApiListVO> showApiListByProject(Integer projectId);
	
	@Select("SELECT t1.*,t2.`name` classificationName FROM api t1,api_classification t2 WHERE t2.id = #{apiClassificationId} AND t1.api_classification_id = t2.id")
	public List<ApiListVO> showApiListByApiClassification(Integer apiClassificationId);

	@Select("SELECT t1.*,t2.username createUsername FROM api t1,user t2 WHERE t1.id=#{apiId} and t1.create_user=t2.id")
	@Results({
		//列，属性
		@Result(column="id",property="id"),
		@Result(property="requestParams",column="id",
		many=@Many(select="com.qccr.mapper.ApiRequestParamMapper.findAll"))
	})
	public ApiVO findApiViewVO(Integer apiId);
}
