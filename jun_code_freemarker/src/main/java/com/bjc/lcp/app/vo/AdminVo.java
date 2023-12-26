package com.bjc.lcp.app.vo;

import lombok.Data;
import java.util.Date;
import java.util.List;
import java.io.Serializable;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import com.bjc.lcp.system.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * @description 管理员表
 * @author wujun
 * @date 2023-12-26
 */
@Data
@ApiModel("管理员表")
public class AdminVo  extends BaseEntity  implements Serializable {

    public interface Retrieve{}
    public interface Delete {}
    public interface Update {}
    public interface Create {}

    private static final long serialVersionUID = 1L;

    /**
    * 
    */
    @ApiModelProperty("") 
    @NotNull(message = "不能为空", groups = {Create.class,Update.class,Delete.class})
    @Size( max = 19,message = "长度限制19位")
    private Long id;

    /**
    * 用户名
    */
    @ApiModelProperty("用户名") 
    
    
    private String username;

    /**
    * 密码
    */
    @ApiModelProperty("密码") 
    
    
    private String password;

    /**
    * 最后登录时间
    */
    @ApiModelProperty("最后登录时间") 
    
    
    private Date lasloginDate;

    /**
    * 创建时间
    */
    @ApiModelProperty("创建时间") 
    
    
    private Date createDate;

    public AdminVo() {}
}
