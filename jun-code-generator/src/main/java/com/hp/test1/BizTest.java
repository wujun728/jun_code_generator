package com.hp.test1;
import java.io.Serializable;
import lombok.Data;
import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * @description 客户信息
 * @author wujun
 * @date 2024-12-18
 */
@Data
@ApiModel("客户信息")
public class BizTest implements Serializable {

    private static final long serialVersionUID = 1L;

    
    @ApiModelProperty("")
    private Long id;

    
    @ApiModelProperty("客户名称")
    private String cusname;

    
    @ApiModelProperty("注册金额")
    private String money;

    
    @ApiModelProperty("客户描述")
    private String cusdesc;

    
    @ApiModelProperty("客户全称")
    private String fullname;

    
    @ApiModelProperty("注册时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    
    @ApiModelProperty("客户类型")
    private String custype;

    
    @ApiModelProperty("")
    private String state;

    
    @ApiModelProperty("备注")
    private String remark;

    public BizTest() {
    }

}
