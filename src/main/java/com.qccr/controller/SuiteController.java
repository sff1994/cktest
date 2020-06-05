package com.qccr.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qccr.common.Result;
import com.qccr.pojo.Suite;
import com.qccr.service.SuiteService;

import io.swagger.annotations.ApiOperation;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author kk
 * @since 2020-02-23
 */
@RestController
@RequestMapping("/suite")
public class SuiteController {
	@Autowired
	SuiteService suiteService;
	
	@GetMapping("/listAll")
	@ApiOperation(value = "项目外键id查询对应的分类信息方法", httpMethod = "GET")
	// 路径上面的变量
	public Result findAll(Integer projectId) {
		QueryWrapper queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("project_id", projectId);
		List<Suite> list = suiteService.list(queryWrapper);
		return new Result("1", list, "查询所有测试集合成功");
	}
}
