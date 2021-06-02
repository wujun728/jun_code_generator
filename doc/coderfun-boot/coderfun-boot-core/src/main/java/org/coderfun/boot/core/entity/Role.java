package org.coderfun.boot.core.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author klguang[klguang@foxmail.com]
 * @date 2018年9月20日
 */
@Entity
@Access(AccessType.FIELD)
@Table(name = "cf_boot_role")
public class Role implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false, length = 20)
	private String name;

	@Column(name = "role_code", nullable = false, length = 20)
	private String roleCode;

	@Column(name = "description")
	private String description;

	@Column(name = "sort")
	private Short sort;

	// Constructors

	/** default constructor */
	public Role() {
	}

	/** minimal constructor */
	public Role(String name, String roleCode) {
		this.name = name;
		this.roleCode = roleCode;
	}

	// Property accessors

	public String getName() {
		return name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Short getSort() {
		return sort;
	}

	public void setSort(Short sort) {
		this.sort = sort;
	}
}