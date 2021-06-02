package org.coderfun.boot.web.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author klguang[klguang@foxmail.com]
 * @date 2018年11月24日
 */
public class UserRoleRequestRequest {
	private Long userId;
	private List<Long> roleIds = new ArrayList<>();

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<Long> getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(List<Long> roleIds) {
		this.roleIds = roleIds;
	}

}
