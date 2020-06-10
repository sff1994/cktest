package com.qccr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qccr.common.ProjectHostVO;
import com.qccr.pojo.Project;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kk
 * @since 2020-02-23
 */
public interface HostListService extends IService<Project> {
    public ProjectHostVO getWithProjectHost(Integer projectId);
//    ProjectHostVO showHostListByProject(Integer projectId);
//    public ProjectHostVO showHostListByProject(Integer projectId);
}
