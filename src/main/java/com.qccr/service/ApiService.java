package com.qccr.service;

import com.qccr.common.ApiListVO;
import com.qccr.common.ApiRunResult;
import com.qccr.common.ApiVO;
import com.qccr.pojo.Api;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kk
 * @since 2020-02-23
 */
public interface ApiService extends IService<Api> {
	public List<ApiListVO> showApiListByProject(Integer projectId);
	public List<ApiListVO> showApiListByApiClassification(Integer apiClassificationId);
	public ApiVO findApiViewVO(Integer apiId);
	public ApiRunResult run(ApiVO apiRunVo);
	
}
