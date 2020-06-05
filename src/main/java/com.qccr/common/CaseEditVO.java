package com.qccr.common;

import java.util.ArrayList;
import java.util.List;

import com.qccr.pojo.ApiRequestParam;
import com.qccr.pojo.Cases;
import com.qccr.pojo.TestRule;

import lombok.Data;

@Data
public class CaseEditVO extends Cases {

//	private Integer id;
//	private String name;//用例名
	private String method;
	private String url;
	private Integer apiId;
	private String host;
	private List<ApiRequestParam> requestParams = new ArrayList<ApiRequestParam>();
	
	private List<TestRule> testRules = new ArrayList<TestRule>();
}
