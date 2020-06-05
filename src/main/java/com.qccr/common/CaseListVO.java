package com.qccr.common;

import com.qccr.pojo.TestReport;

import lombok.Data;

@Data
public class CaseListVO {

	private String id;
	private String name;
	private String apiId;
	private String apiUrl;
	
	private TestReport testReport;
}
