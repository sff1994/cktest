package com.qccr.controller;

import java.util.Date;
import java.util.List;

import com.qccr.common.ProjectHostVO;
import com.qccr.pojo.Host;
import com.qccr.service.HostListService;
import com.qccr.service.HostService;
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
	@Autowired
	private HostService hostService;
	@Autowired
	private HostListService hostListService;

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
	// 添加host方法
	@PostMapping("/host/addHost")
	@ApiOperation(value = "添加host方法", httpMethod = "POST")
	public Result addHost(Host host,String projectId) {
		// shirosessionmansger中管理的有userId
		Result result = null;
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		host.setCreateUser(user.getId());
		host.setCreateTime(new Date());
		host.setProjectId(projectId);
		hostService.save(host);
		result = new Result("1", "添加host成功");
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
//	@GetMapping("/{projectId}")
//	@ApiOperation(value = "项目id查询方法", httpMethod = "GET")
//	// 路径上面的变量
//	public Result getById(@PathVariable("projectId") Integer projectId) {
//		Result result = null;
//		Project projectInfo = projectService.getById(projectId);
//		result = new Result("1", projectInfo, "查询项目信息");
//		return result;
//	}
	// 根据项目主键id查询项目信息和host信息的方法
	@GetMapping("/{projectId}")
	@ApiOperation(value = "项目id查询方法", httpMethod = "GET")
	// 路径上面的变量
	public Result getById(@PathVariable("projectId") Integer projectId) {
		Result result = null;
		ProjectHostVO list =  hostListService.getWithProjectHost(projectId);
		result = new Result("1", list, "查询项目和host信息");
		return result;
	}


//	// 根据项目主键id查询host的方法
//	@GetMapping("/host/{projectId}")
//	@ApiOperation(value = "项目id查询host方法", httpMethod = "GET")
//	// 路径上面的变量
//	public Result getHostById(@PathVariable("projectId") Integer projectId) {
//		Result result = null;
//		Project projectInfo = hostService.sho(projectId);
//		result = new Result("1", projectInfo, "查询项目信息");
//		return result;
//	}


	// 保存项目方法
	@PutMapping("/updateById")
	@ApiOperation(value = "保存项目方法", httpMethod = "POST")
	public Result updateById(Integer projectId,Project project,Host hostOne) {
		Result result = null;
//
//		//同步更新到host表内
////		Host hostOne = new Host();
//		//在id被覆盖前先得到由前端传到的hostid
		//注意：若不重新获取一遍的话，会不同步
		hostOne.setId(project.getId());
		hostOne.setHostName(project.getHost());
//		//前端将hostid传到了project对象中，直接获取即可
		hostOne.setDescription(project.getDescription());
		hostService.updateById(hostOne);

		project.setId(projectId);
//		User user = (User) SecurityUtils.getSubject().getPrincipal();
//		project.setCreateUser(user.getId());
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
