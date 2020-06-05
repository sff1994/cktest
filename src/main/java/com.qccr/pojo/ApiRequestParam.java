package com.qccr.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author kk
 * @since 2020-02-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="ApiRequestParam对象", description="")
public class ApiRequestParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "关联的接口编号")
    private Integer apiId;

    @ApiModelProperty(value = "参数名称")
    private String name;

    @ApiModelProperty(value = "字段类型（string、int...）")
    private String paramType;

    @ApiModelProperty(value = "参数类型（1：query参数；2：body参数；3：header；4：body里的json参数）")
    private Integer type;

    @ApiModelProperty(value = "参数值示例")
    private String exampleData;

    @ApiModelProperty(value = "字段描述")
    private String description;

    //非常重要，表里没有此字段，仅用来临时封装数据处理。具体是在另外表中
    @TableField(exist=false)
    private String value;
    
    @TableField(exist=false)
    private Integer valueId;
    

}
