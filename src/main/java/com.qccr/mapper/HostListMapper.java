package com.qccr.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qccr.common.ProjectHostVO;
import com.qccr.pojo.Project;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author kk
 * @since 2020-02-23
 */
public interface HostListMapper extends BaseMapper<Project> {
    //两表延迟加载，先查询分类信息（List<Api>）,按需加载（即此时查另一张表）#{projectId}===?
    @Select("select * from project where id =#{projectId}")
    @Results({
            //列，属性
            @Result(column="id",property="id"),
            @Result(column="host",property="host"),
            @Result(property="projecthostList",column="id",
                    many=@Many(select="com.qccr.mapper.HostMapper.findHost"))
    })
    public ProjectHostVO getWithProjectHost(Integer projectId);

//    @Select("select * from host where projectId=#{projectId}")
//    public ProjectHostVO findHostAll(Integer projectId);
////
////    public ProjectHostVO showHostListByProject(Integer projectId);
//@Select("SELECT t1.*,t2.project_id,t2.host_name,t2.description hostdescription FROM project t1,`host` t2 WHERE t1.id=#{projectId} and t1.id=t2.project_id")
//@Results({
//        //列，属性
//        @Result(column="id",property="id"),
//        @Result(property="hostList",column="id",
//                many=@Many(select="com.qccr.mapper.HostMapper.findHostAll"))
//})
//public ProjectHostVO showHostListByProject(Integer projectId);
}
