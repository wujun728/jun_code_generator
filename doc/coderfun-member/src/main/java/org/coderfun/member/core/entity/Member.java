package org.coderfun.member.core.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import klg.common.dataaccess.entity.BaseEntity;

@Entity
@Table(name = "cf_member")
@Access(AccessType.FIELD)
public class Member extends BaseEntity<Long> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2941012465763457452L;

	/** 用户名 */
	private String username;
	/** 头像 */
	private String avatar;

	@JsonIgnore
	/** 密码 */
	private String password;

	// 不会持久化，也不会再json中打印
	@JsonIgnore
	@Transient
	private String plainPassword;

	@JsonIgnore
	/** 密码的盐 */
	private String salt;

	/** E-mail */
	private String email;

	/** 手机 */
	private String mobile;

	/** 连续登录失败次数 */
	private Integer loginFailureCount;

	/** 是否锁定 */
	private String locked;

	/** 锁定日期 */
	private Date lockedDate;

	/** 积分 */
	private Long point;

	/** 余额 */
	private BigDecimal balance;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getLoginFailureCount() {
		return loginFailureCount;
	}

	public void setLoginFailureCount(Integer loginFailureCount) {
		this.loginFailureCount = loginFailureCount;
	}

	public Date getLockedDate() {
		return lockedDate;
	}

	public void setLockedDate(Date lockedDate) {
		this.lockedDate = lockedDate;
	}

	public Long getPoint() {
		return point;
	}

	public void setPoint(Long point) {
		this.point = point;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getPlainPassword() {
		return plainPassword;
	}

	public void setPlainPassword(String plainPassword) {
		this.plainPassword = plainPassword;
	}

	public String getLocked() {
		return locked;
	}

	public void setLocked(String locked) {
		this.locked = locked;
	}

}
