package org.coderfun.boot.core.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import klg.common.utils.DatePartten;

/**
 * 
 * @author klguang[klguang@foxmail.com]
 * @date 2018年9月20日
 */

@Entity
@Access(AccessType.FIELD)
@Table(name = "cf_boot_user")
public class User implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@Column(name = "login_name", nullable = false, length = 30)
	private String loginName;

	@Column(name = "name", nullable = false, length = 20)
	private String name;

	@JsonIgnore
	@Column(name = "password", nullable = false)
	private String password;
	// 不会持久化，也不会再json中打印
	@JsonIgnore
	@Transient
	private String plainPassword;

	@JsonIgnore
	@Column(name = "salt")
	private String salt;

	@DateTimeFormat(pattern = DatePartten.DATE)
	@Column(name = "birthday")
	private Date birthday;

	@Column(name = "gender")
	private String gender;

	@Column(name = "email")
	private String email;

	@Column(name = "phone")
	private String phone;

	@Column(name = "avatar")
	private String avatar;

	@Column(name = "create_date")
	private Date createDate;

	@Column(name = "state")
	private String state;

	@Column(name = "description")
	private String description;

	// Constructors

	/** default constructor */
	public User() {
	}

	/** minimal constructor */
	public User(String loginName, String name, String password) {
		this.loginName = loginName;
		this.name = name;
		this.password = password;
	}

	// Property accessors

	// 不持久化到数据库，也不显示在Restful接口的属性.
	@Transient
	@JsonIgnore
	public String getPlainPassword() {
		return plainPassword;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setPlainPassword(String plainPassword) {
		this.plainPassword = plainPassword;
	}
}