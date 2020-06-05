package com.qccr.service.impl;

import com.qccr.pojo.User;
import com.qccr.mapper.UserMapper;
import com.qccr.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kk
 * @since 2020-02-23
 */
@Service//告诉spring容器把UserServiceImpl类new出来
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
