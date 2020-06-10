package com.qccr.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Host对象", description="")
public class Host implements Serializable {

    @ApiModelProperty(value = "id主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "项目id")
    private String projectId;

    @ApiModelProperty(value = "host地址名")
    private String hostName;

    @ApiModelProperty(value = "host描述")
    private String description;

    @ApiModelProperty(value = "创建用户id")
    private Integer createUser;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

}
