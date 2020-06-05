package com.qccr.controller;

import java.util.Date;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.qccr.common.Result;
import com.qccr.pojo.ApiClassification;
import com.qccr.pojo.Suite;
import com.qccr.pojo.User;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qccr.common.ApiClassificationVO;
import com.qccr.service.ApiClassificationService;
import com.qccr.service.SuiteService;

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
@RequestMapping("/apiClassification")
public class ApiClassificationController {

	@Autowired
	ApiClassificationService apiClassificationService;
	@Autowired
	SuiteService suiteService;
	Result result = null;
	
	@GetMapping("/toIndex")
	public Result getWithApi(Integer projectId, Integer tab) {
		
		if (tab == 1) {
			// 接口列表
			List<ApiClassificationVO> list = apiClassificationService.getWithApi(projectId);
			result = new Result("1", list, "查询分类同时也延迟加载api");
		} else {
			// 测试集合
			List<Suite> listSuit = suiteService.findSuiteAndReleadtedCasesBy(projectId);
			result = new Result("1", listSuit, "查询用例集同时也延迟加载api");
		}
		return result;

	}

	// 添加分类方法
	@PostMapping("/addClassification")
	@ApiOperation(value = "添加分类方法", httpMethod = "POST")
	public Result addClassification(ApiClassification apiClassification, Integer projectId) {
		// shirosessionmansger中管理的有userId
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		apiClassification.setCreateUser(user.getId());
		apiClassification.setCreateTime(new Date());
		apiClassification.setProjectId(projectId);
		apiClassificationService.save(apiClassification);
		result = new Result("1", "添加分类成功");
		return result;
	}

	// 根据分类主键id查询方法
	@GetMapping("/{id}")
	@ApiOperation(value = "分类id查询方法", httpMethod = "GET")
	// 路径上面的变量
	public Result getClassificationById(@PathVariable("id") Integer id) {
		ApiClassification apiClassification = apiClassificationService.getById(id);
		result = new Result("1", apiClassification, "查询分类信息成功");
		return result;
	}

	// 根据项目外键id查询所有分类表数据方法
	@GetMapping("/findAll")
	@ApiOperation(value = "项目外键id查询对应的分类信息方法", httpMethod = "GET")
	// 路径上面的变量
	public Result findAll(Integer projectId) {
		QueryWrapper queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("project_id", projectId);
		List<ApiClassification> list = apiClassificationService.list(queryWrapper);
		result = new Result("1", list, "查询所有分类信息成功");
		return result;
	}

	// // 保存分类方法
	// @PostMapping("/updateClassificationById")
	// @ApiOperation(value = "保存项目方法", httpMethod = "POST")
	// public Result updateClassificationById(Integer id,ApiClassification
	// apiClassification) {
	// Result result = null;
	// apiClassification.setId(id);
	// apiClassificationService.updateById(apiClassification);
	// result = new Result("1", apiClassification, "保存分类成功");
	//
	// return null;
	// }
	// 保存分类方法
	@PostMapping("/{id}")
	@ApiOperation(value = "保存项目方法", httpMethod = "POST")
	public Result updateClassificationById(@PathVariable("id") Integer id, ApiClassification apiClassification) {
		apiClassification.setId(id);
		apiClassificationService.updateById(apiClassification);
		result = new Result("1", apiClassification, "保存分类成功");

		return result;
	}

	// 删除分类方法
	@DeleteMapping("/{id}")
	@ApiOperation(value = "删除分类方法", httpMethod = "GET")
	public Result delClassificationById(@PathVariable("id") Integer id) {
		// shirosessionmansger中管理的有userId
		ApiClassification apiClassification = apiClassificationService.getById(id);
		if (id.equals(apiClassificationService.getById(id).getId())) {
			apiClassificationService.removeById(apiClassification);
			result = new Result("0", "删除分类成功");
		} else {
			result = new Result("1", "删除分类失败");
		}

		return result;
	}
	
	//
	@PostMapping("/add2")
	public Result add2(@RequestBody String jsonStr){
//		System.out.println(jsonStr);
//		MultiValueMap返回的数据{"json":["{"projectId":"9","name":"sfftestjson44"}"]}
//		String value = jsonStr.substring(jsonStr.indexOf("[")+2, jsonStr.indexOf("]")-1);
//		System.out.println(value);
		//将jsonStr转成java对象
		ApiClassification apiClassification = JSON.parseObject(jsonStr, ApiClassification.class);
		apiClassificationService.save(apiClassification);
		result = new Result("1","test添加分类成功");
		return result;
	}

}
