/**
 * BaseOSSRole.java
 *
 * Copyright 2012 lemon sea, Inc. All Rights Reserved.
 */
package net.mm2018.mmall.oss.domain.base;

import java.io.Serializable;
import java.util.Set;

/**
 * 后台角色.
 * User: Cosmo<cosmo.wang@kaixinai.com>
 * Date: 12-3-24
 * Time: 下午11:11
 */
@SuppressWarnings("serial")
public abstract class BaseOSSRole
	implements Serializable {

	private Integer roleId;
	private String roleName;
	private Integer priority;
	private Boolean mSuper;

	// collections
	private Set<String> perms;

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Boolean getmSuper() {
		return mSuper;
	}

	public void setmSuper(Boolean mSuper) {
		this.mSuper = mSuper;
	}

	public Set<String> getPerms() {
		return perms;
	}

	public void setPerms(Set<String> perms) {
		this.perms = perms;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("BaseOSSRole");
		sb.append("{roleId=").append(roleId);
		sb.append(", roleName='").append(roleName).append('\'');
		sb.append(", priority=").append(priority);
		sb.append(", mSuper=").append(mSuper);
		sb.append(", perms=").append(perms);
		sb.append('}');
		return sb.toString();
	}
}
