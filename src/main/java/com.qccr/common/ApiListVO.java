package com.qccr.common;

import lombok.Data;
/**
 * 接口列表属性
 * @author shife
 *
 */
@Data
public class ApiListVO {
	private String id;
	private String name;
	private String method;
	private String url;
	private String classificationName;
}
