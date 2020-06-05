package com.qccr.service.impl;

import com.qccr.pojo.Suite;
import com.qccr.mapper.SuiteMapper;
import com.qccr.service.SuiteService;
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
public class SuiteServiceImpl extends ServiceImpl<SuiteMapper, Suite> implements SuiteService {
	
	@Autowired
	SuiteMapper suiteMapper;

	@Override
	public List<Suite> findSuiteAndReleadtedCasesBy(Integer projectId) {
		// TODO Auto-generated method stub
		return suiteMapper.findSuiteAndReleadtedCasesBy(projectId);
	}
	
}
