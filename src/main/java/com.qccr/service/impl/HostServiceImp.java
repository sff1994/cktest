package com.qccr.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qccr.mapper.HostMapper;
import com.qccr.pojo.Host;
import com.qccr.service.HostService;
import org.springframework.stereotype.Service;

@Service
public class HostServiceImp extends ServiceImpl<HostMapper, Host> implements HostService {
}
