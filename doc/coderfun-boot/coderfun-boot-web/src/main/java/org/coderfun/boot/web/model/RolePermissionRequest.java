package org.coderfun.boot.web.model;

import java.util.List;

public class RolePermissionRequest {
	private Long roleId;
	private List<Long> permissionIds;

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public List<Long> getPermissionIds() {
		return permissionIds;
	}

	public void setPermissionIds(List<Long> permissionIds) {
		this.permissionIds = permissionIds;
	}

}
