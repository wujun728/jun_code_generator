package org.coderfun.boot.core.shiro;

import java.io.Serializable;
/**
 * 
 * session中的用户信息，可序列化，以便在集群中被复制或者能够持久保存
 * 
 * @author klguang[klguang@foxmail.com]
 * @date 2018年11月11日
 */
public class SessionUser implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3477548434407103726L;

	private Long id;

	private String loginName;

	private String name;

	private String avatar;

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

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

}
