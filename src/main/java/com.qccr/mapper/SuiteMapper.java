package com.qccr.mapper;

import com.qccr.pojo.Suite;

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
public interface SuiteMapper extends BaseMapper<Suite> {
	@Select("SELECT * FROM suite WHERE project_id=#{projectId}")
	@Results({
		//列，属性
		@Result(column="id",property="id"),
		@Result(property="cases",column="id",
		many=@Many(select="com.qccr.mapper.CasesMapper.findAll"))
	})
	List<Suite> findSuiteAndReleadtedCasesBy(Integer projectId);
}
