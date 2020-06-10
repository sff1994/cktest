package com.qccr.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qccr.common.ProjectHostVO;
import com.qccr.mapper.ApiMapper;
import com.qccr.mapper.HostListMapper;
import com.qccr.pojo.Project;
import com.qccr.service.HostListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class HostListServiceImp extends ServiceImpl<HostListMapper, Project> implements HostListService {

    @Autowired
    HostListMapper hostListMapper;
    public ProjectHostVO getWithProjectHost(Integer projectId){
        return hostListMapper.getWithProjectHost(projectId);
    }
}
