package org.coderfun.boot.core.entity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import klg.common.tree.EasyUITreeNode;

/**
 * 
 * @author klguang[klguang@foxmail.com]
 * @date 2018年9月20日
 */
@Entity
@Access(AccessType.FIELD)
@Table(name = "cf_boot_permission")
public class Permission extends EasyUITreeNode<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "type")
	private String type;

	@Column(name = "url")
	private String url;

	@Column(name = "perm_code")
	private String permCode;

	@Column(name = "description")
	private String description;

	// Constructors

	/** default constructor */
	public Permission() {
	}

	public Permission(Long parentId, String name, String type, String url, String permCode) {
		this.setParentId(parentId);
		this.setName(name);
		this.type = type;
		this.url = url;
		this.permCode = permCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPermCode() {
		return permCode;
	}

	public void setPermCode(String permCode) {
		this.permCode = permCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}