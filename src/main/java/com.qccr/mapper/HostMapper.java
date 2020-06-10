package com.qccr.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qccr.common.ProjectHostVO;
import com.qccr.pojo.Api;
import com.qccr.pojo.Host;
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
public interface HostMapper extends BaseMapper<Host> {
//select * from host where project_id =13
//    SELECT t1.*,t2.project_id,t2.host_name,t2.description hostdescription FROM project t1,`host` t2 WHERE t1.id=13 and t1.id=t2.project_id
    @Select("select * from host where project_id =#{projectId}")
    public List<Host> findHost(Integer projectId);
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
