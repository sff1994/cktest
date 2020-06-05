package com.qccr.service.impl;

import com.qccr.pojo.ApiClassification;
import com.qccr.common.ApiClassificationVO;
import com.qccr.mapper.ApiClassificationMapper;
import com.qccr.service.ApiClassificationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kk
 * @since 2020-02-23
 */
@Service
public class ApiClassificationServiceImpl extends ServiceImpl<ApiClassificationMapper, ApiClassification> implements ApiClassificationService {

	@Autowired
	ApiClassificationMapper apiClassificationMapper;

	@Override
	public List<ApiClassificationVO> getWithApi(Integer projectId) {
		// TODO Auto-generated method stub
		return apiClassificationMapper.getWithApi(projectId);
	}

	
	

}
