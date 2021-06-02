package org.coderfun.boot.core.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the cf_boot_role_permission database table.
 * 
 */
@Entity
@Table(name = "cf_boot_role_permission")
@Access(AccessType.FIELD)
public class RolePermission implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@Column(name = "permission_id")
	private Long permissionId;

	@Column(name = "role_id")
	private Long roleId;

	public RolePermission() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Long permissionId) {
		this.permissionId = permissionId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

}