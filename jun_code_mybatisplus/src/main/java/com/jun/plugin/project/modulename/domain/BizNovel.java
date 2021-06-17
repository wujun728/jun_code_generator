package com.jun.plugin.project.modulename.domain;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

/**
 * <p>
 * 
 *</p>
 *
 * @author jun
 * @date 2021-06-17
 */
@ApiModel(description = "")
public class BizNovel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("")
    private Integer id;

    @ApiModelProperty("")
    private String type;

    @ApiModelProperty("")
    private String url;

    @ApiModelProperty("")
    private String title;

    @ApiModelProperty("")
    private String content;

    @ApiModelProperty("")
    private String contentDetail;

    @ApiModelProperty("")
    private String uptime;

    @ApiModelProperty("")
    private Date createtime;

    @ApiModelProperty("")
    private Integer fileid;


    public void setId (Integer  id){
        this.id=id;
    }
    public Integer getId(){
        return this.id;
    }

    public void setType (String  type){
        this.type=type;
    }
    public String getType(){
        return this.type;
    }

    public void setUrl (String  url){
        this.url=url;
    }
    public String getUrl(){
        return this.url;
    }

    public void setTitle (String  title){
        this.title=title;
    }
    public String getTitle(){
        return this.title;
    }

    public void setContent (String  content){
        this.content=content;
    }
    public String getContent(){
        return this.content;
    }

    public void setContentDetail (String  contentDetail){
        this.contentDetail=contentDetail;
    }
    public String getContentDetail(){
        return this.contentDetail;
    }

    public void setUptime (String  uptime){
        this.uptime=uptime;
    }
    public String getUptime(){
        return this.uptime;
    }

    public void setCreatetime (Date  createtime){
        this.createtime=createtime;
    }
    public Date getCreatetime(){
        return this.createtime;
    }

    public void setFileid (Integer  fileid){
        this.fileid=fileid;
    }
    public Integer getFileid(){
        return this.fileid;
    }
}