package com.qccr.mapper;

import com.qccr.common.ApiClassificationVO;
import com.qccr.pojo.ApiClassification;

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
public interface ApiClassificationMapper extends BaseMapper<ApiClassification> {
	
	//两表延迟加载，先查询分类信息（List<Api>）,按需加载（即此时查另一张表）#{projectId}===?
	@Select("select * from api_classification where project_id =#{projectId}")
	@Results({
		//列，属性
		@Result(column="id",property="id"),
		@Result(column="project_id",property="projectId"),
		@Result(column="name",property="name"),
		@Result(property="apis",column="id",
		many=@Many(select="com.qccr.mapper.ApiMapper.findApi"))
	})
	public List<ApiClassificationVO> getWithApi(Integer projectId);
}
