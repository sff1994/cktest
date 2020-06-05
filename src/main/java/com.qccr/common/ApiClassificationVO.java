package com.qccr.common;

import java.util.List;

import com.qccr.pojo.Api;
import com.qccr.pojo.ApiClassification;

import lombok.Data;

@Data
public class ApiClassificationVO extends ApiClassification {

	/**
	 * 
	 */
	//关联查询
	List<Api> apis;
}
