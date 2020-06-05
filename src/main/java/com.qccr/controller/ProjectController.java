package com.qccr.controller;

import java.util.Date;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qccr.common.Result;
import com.qccr.pojo.Project;
import com.qccr.pojo.User;
import com.qccr.service.ProjectService;
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
@RequestMapping("/project")
@Api("项目模块")
public class ProjectController {

	@Autowired
	private ProjectService projectService;

	// 项目列表查询方法
	@GetMapping("/toList")
	@ApiOperation(value = "项目查询方法", httpMethod = "GET")
	public Result toList(Integer userId) {
		Result result = null;
		QueryWrapper queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("create_user", userId);
		List list = projectService.list(queryWrapper);
		result = new Result("1", list, "项目列表");
		return result;
	}

	// 添加项目方法
	@PostMapping("/add")
	@ApiOperation(value = "添加项目方法", httpMethod = "POST")
	public Result add(Project project) {
		// shirosessionmansger中管理的有userId
		Result result = null;
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		project.setCreateUser(user.getId());
		project.setCreateTime(new Date());
		projectService.save(project);
		result = new Result("1", "添加项目成功");
		return result;
	}
	// 用json的参数添加项目方法
	@PostMapping("/addProjectJson")
	@ApiOperation(value = "添加项目方法", httpMethod = "POST")
	public Result addProjectJson(@RequestBody Project project) {
		// shirosessionmansger中管理的有userId
		Result result = null;
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		project.setCreateUser(user.getId());
		projectService.save(project);
		result = new Result("1", "添加项目成功");
		return result;
	}

	// 根据项目主键id查询方法
	@GetMapping("/{projectId}")
	@ApiOperation(value = "项目id查询方法", httpMethod = "GET")
	// 路径上面的变量
	public Result getById(@PathVariable("projectId") Integer projectId) {
		Result result = null;
		Project projectInfo = projectService.getById(projectId);
		result = new Result("1", projectInfo, "查询项目信息");
		return result;
	}

	// 保存项目方法
	@PutMapping("/{projectId}")
	@ApiOperation(value = "保存项目方法", httpMethod = "POST")
	public Result updateById(@PathVariable("projectId") Integer projectId,Project project) {
		Result result = null;
		project.setId(projectId);
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		project.setCreateUser(user.getId());
		projectService.updateById(project);
		result = new Result("1",project, "保存项目信息");
		return result;
	}
	// 删除项目方法
		@GetMapping("/deleteById")
		@ApiOperation(value = "删除项目方法", httpMethod = "GET")
		public Result deleteById(Integer projectId) {
			// shirosessionmansger中管理的有userId
			Result result = null;
//			QueryWrapper queryWrapper = new QueryWrapper<>();
//			queryWrapper.eq("create_user", userId);
			Project projectInfo = projectService.getById(projectId);
			if (projectId.equals(projectService.getById(projectId).getId())) {
				projectService.removeById(projectId);
				result = new Result("0", "删除此项目成功");
			}else {
				result = new Result("1", "删除此项目失败");
			}
			
			
			
			return result;
		}
}
