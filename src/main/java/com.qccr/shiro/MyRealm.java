package com.qccr.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qccr.pojo.User;
import com.qccr.service.UserService;

public class MyRealm extends AuthorizingRealm {
	@Autowired
	private UserService userService;
	// 授权管理
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * 自定义登录校验//身份验证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// 认证逻辑
		String username = token.getPrincipal().toString();
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.eq("username", username);
		User dbUser = userService.getOne(queryWrapper);
		if (dbUser != null) {
			SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(dbUser, dbUser.getPassword(), getName());
			return simpleAuthenticationInfo;
		}else{
			System.out.println("token为空");
		}
		return null;
	}

}
