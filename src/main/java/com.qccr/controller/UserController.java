package com.qccr.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qccr.common.Result;
import com.qccr.pojo.User;
import com.qccr.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author kk
 * @since 2020-02-23
 */
@RestController
@RequestMapping("/user")
@Api("用户模块")
// @CrossOrigin
public class UserController {

	@Autowired
	private UserService userService;

	// 注册方法，返回json
	// @RequestMapping("/register")
	@PostMapping("/register")
	@ApiOperation(value = "注册方法", httpMethod = "POST")
	public Result register(User user) {// 对象里的属性要与传过来的参数名一致，
		// user.setRegtime(new Date());
		// 调用业务逻辑层方法，插入到到DB，目前只考虑正向情况后面会统一处理异常
		userService.save(user);
		Result result = new Result("1", "注册成功");
		return result;
	}

	@PostMapping("/login")
	@ApiOperation(value = "登录方法", httpMethod = "POST")
	public Result login(User user) {// 对象里的属性要与传过来的参数名一致，
		Result result = null;
		try {
			UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
			// UsernamePasswordToken token = new UsernamePasswordToken();
			Subject subject = SecurityUtils.getSubject();
			subject.login(token);
			// 将sessionId返回去
			String sessionId = (String) SecurityUtils.getSubject().getSession().getId();
			// 得到当前登录的用户id放到data中
			User loginUser = (User) subject.getPrincipal();
			result = new Result("1", loginUser.getId(), sessionId);
		} catch (AuthenticationException e) {
			if (e instanceof UnknownAccountException) {
				result = new Result("0", "用户名错误");
			} else {
				result = new Result("0", "密码错误");
			}
			e.printStackTrace();
		}
		return result;
	}

	// 账号验重方法
	@GetMapping("/find")
	@ApiOperation(value = "账号验重方法", httpMethod = "GET")
	public Result find(String username) {
		Result result = null;
		// 调用业务逻辑层方法，查询DB非主键列，目前只考虑正向情况后面会统一处理异常
		QueryWrapper queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("username", username);
		User user = userService.getOne(queryWrapper);
		if (user == null) {
			result = new Result("1", "账号不存在，允许注册");
		} else {
			result = new Result("0", "账号已存在，不允许注册");
		}

		return result;
	}

	// 账号退出
	@GetMapping("/logout")
	@ApiOperation(value = "退出方法", httpMethod = "GET")
	public Result logout() {
		Result result = null;
		// 从shiro退出会话
		SecurityUtils.getSubject().logout();
		result = new Result("0", "账号未登录");
		return result;
	}

	@GetMapping("/unauth")
	@ApiOperation(value = "未授权方法", httpMethod = "GET")
	public Result unauth() {
		Result result = null;
		result = new Result("0", "账号未登录");
		return result;
	}
	@GetMapping("/self")
	@ApiOperation(value = "个人中心方法", httpMethod = "GET")
	public Result self() {
		Result resultUser = null;
		// 从shiro获取个人信息数据
		User mySelf = (User) SecurityUtils.getSubject().getPrincipal();
		System.out.println(mySelf.getId());
		System.out.println(mySelf.getUsername());
		System.out.println(mySelf.getPassword());
		System.out.println(mySelf.getRegtime());
		resultUser = new Result("1",mySelf, "查询个人信息成功");
		return resultUser;
	}

}
