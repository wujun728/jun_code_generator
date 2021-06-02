package org.coderfun.common.log.entity;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "cf_common_loginlog")
@Access(AccessType.FIELD)
public class LoginLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String successed;

	@Lob
	private String message;

	private String os;
	private String browser;

	private Date optime;
	private String opip;
	private String opusername;

	public String getSuccessed() {
		return successed;
	}

	public void setSuccessed(String successed) {
		this.successed = successed;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getOptime() {
		return optime;
	}

	public void setOptime(Date optime) {
		this.optime = optime;
	}

	public String getOpip() {
		return opip;
	}

	public void setOpip(String opip) {
		this.opip = opip;
	}

	public String getOpusername() {
		return opusername;
	}

	public void setOpusername(String opusername) {
		this.opusername = opusername;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

}
