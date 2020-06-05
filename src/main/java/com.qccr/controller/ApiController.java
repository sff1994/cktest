package com.qccr.controller;


import java.util.Date;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.qccr.common.ApiListVO;
import com.qccr.common.ApiRunResult;
import com.qccr.common.ApiVO;
import com.qccr.common.Result;
import com.qccr.pojo.Api;
import com.qccr.pojo.User;
import com.qccr.service.ApiRequestParamService;
import com.qccr.service.ApiService;

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
@RequestMapping("/api")
public class ApiController {

	@Autowired
	ApiService apiService;
	@Autowired
	ApiRequestParamService apiRequestParamService;
	
	@GetMapping("/showApiUnderProject")
	@ApiOperation(value = "接口全列表查询方法", httpMethod = "GET")
	public Result showApiUnderProject(Integer projectId){
		Result result = null;
		List<ApiListVO> list = apiService.showApiListByProject(projectId);
		result = new Result("1", list);
		return result;
	}
	@GetMapping("/showApiUnderApiClassification")
	@ApiOperation(value = "接口分类列表查询方法", httpMethod = "GET")
	public Result showApiUnderApiClassification(Integer apiClassificationId){
		Result result = null;
		List<ApiListVO> listApiClassification = apiService.showApiListByApiClassification(apiClassificationId);
		result = new Result("1", listApiClassification);
		return result;
	}
	@PostMapping("/addApi")
	@ApiOperation(value = "添加接口分类详情方法", httpMethod = "POST")
	public Result addApi(Api api) {
		// shirosessionmansger中管理的有userId
		Result result = null;
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		api.setCreateUser(user.getId());
		api.setCreateTime(new Date());
		System.out.println("===="+api);
		apiService.save(api);
		result = new Result("1",api, "添加接口信息成功");
		return result;
	}
	@GetMapping("/toApiView")
	@ApiOperation(value = "接口详情查询方法", httpMethod = "GET")
	public Result findApiView(Integer apiId){
		Result result = null;
		ApiVO listApiViewVO = apiService.findApiViewVO(apiId);
		result = new Result("1", listApiViewVO);
		return result;
	}
	@PutMapping("/edit")
	public Result toApiEdit(ApiVO apiEdit){
		//1、直接根据apiId进行更新api
		apiService.updateById(apiEdit);
		//2、delete原来的apiRequestParam或者先查询数据库判断是否有再插入
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.eq("api_id", apiEdit.getId());
		apiRequestParamService.remove(queryWrapper);
		//3、insert apiRequestParam,将子集添加到总集合（前端方便操作做了区分）
		apiEdit.getRequestParams().addAll(apiEdit.getQueryParams());
		apiEdit.getRequestParams().addAll(apiEdit.getBodyParams());
		apiEdit.getRequestParams().addAll(apiEdit.getHeaderParams());
		apiEdit.getRequestParams().addAll(apiEdit.getBodyRawParams());
		//便利requestparams，循环判断其每个对象属性是否为null true remove
		apiRequestParamService.saveBatch(apiEdit.getRequestParams());
		Result result = new Result("1", "更新成功");
		return result;
		
	}
	@RequestMapping("/run")
	public Result apiRun(ApiVO apiRunVo) throws JsonProcessingException{
		
		ApiRunResult apiRunResult = apiService.run(apiRunVo);
		Result result = new Result("1", apiRunResult);
		return result;
		
	}
}
