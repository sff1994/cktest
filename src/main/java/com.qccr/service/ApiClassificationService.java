package com.qccr.service;

import com.qccr.common.ApiClassificationVO;
import com.qccr.pojo.ApiClassification;

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
public interface ApiClassificationService extends IService<ApiClassification> {
	public List<ApiClassificationVO> getWithApi(Integer projectId);
	
}
