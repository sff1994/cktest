package com.qccr.common;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.qccr.pojo.Host;
import com.qccr.pojo.Project;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ProjectHostVO extends Project {

	private List<Host> projecthostList;
	
}
