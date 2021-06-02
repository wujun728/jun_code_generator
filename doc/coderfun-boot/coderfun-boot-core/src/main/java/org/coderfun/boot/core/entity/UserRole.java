package org.coderfun.boot.core.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the cf_boot_user_role database table.
 * 
 */
@Entity
@Table(name = "cf_boot_user_role")
@Access(AccessType.FIELD)
public class UserRole implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@Column(name = "role_id")
	private Long roleId;

	@Column(name = "user_id")
	private Long userId;

	public UserRole() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}