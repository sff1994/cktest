package com.qccr.common;

import java.util.ArrayList;
import java.util.List;

import com.qccr.pojo.Api;
import com.qccr.pojo.ApiRequestParam;

import lombok.Data;
@Data
public class ApiVO extends Api{

	private String createUsername;
	private String host;
	private String u_session_id;
	private String session_id;

	private List<ApiRequestParam> requestParams = new ArrayList<ApiRequestParam>();
	private List<ApiRequestParam> queryParams = new ArrayList<ApiRequestParam>();
	private List<ApiRequestParam> bodyParams = new ArrayList<ApiRequestParam>();
	private List<ApiRequestParam> headerParams = new ArrayList<ApiRequestParam>();
	private List<ApiRequestParam> bodyRawParams = new ArrayList<ApiRequestParam>();
	
}
