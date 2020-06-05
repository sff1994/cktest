package com.qccr.service;

import com.qccr.common.ApiVO;
import com.qccr.common.CaseEditVO;
import com.qccr.common.CaseListVO;
import com.qccr.pojo.Cases;

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
public interface CasesService extends IService<Cases> {
	public void add(Cases caseVo,ApiVO apiRunVO);
	List<CaseListVO> showCaseUnderProject(Integer projectId);
	List<CaseListVO> findCaseUnderSuite(Integer suiteId);
	CaseEditVO findCaseEditVO(Integer caseId);
	public void updateCase(CaseEditVO caseEditVo);
}
