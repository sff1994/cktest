package com.qccr.service;

import com.qccr.pojo.Suite;

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
public interface SuiteService extends IService<Suite> {
	List<Suite> findSuiteAndReleadtedCasesBy(Integer projectId);
}
