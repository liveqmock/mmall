/**
 * OSSUser.java
 *
 * Copyright 2012 lemon sea, Inc. All Rights Reserved.
 */
package net.mm2018.mmall.oss.domain;

import net.mm2018.mmall.oss.domain.base.BaseOSSUser;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 后台用户.
 * User: Cosmo<cosmo.wang@kaixinai.com>
 * Date: 12-3-24
 * Time: 下午11:10
 */
@SuppressWarnings("serial")
public class OSSUser
		extends BaseOSSUser {

	/** 初始化 */
	public void init() {
		Date now = new Date(System.currentTimeMillis());
		setRegisterTime(now);
		setLastLoginTime(now);
		setLoginCount(0);
		setErrorTime(null);
		setErrorCount(0);
		setErrorIP(null);
	}

	/** @return 是否管理员角色 */
	public boolean isSuper() {
		Set<OSSRole> roles = getRoles();
		if (null == roles) {
			return false;
		}
		for (OSSRole role : roles) {
			if (role.getmSuper()) {
				return true;
			}
		}
		return false;
	}

	/** @return 获取权限地址列表 */
	public Set<String> getPerms() {
		Set<OSSRole> roles = getRoles();
		if (null == roles) {
			return null;
		}
		Set<String> allPerms = new HashSet<String>();
		for (OSSRole role : roles) {
			allPerms.addAll(role.getPerms());
		}
		return allPerms;
	}

	/** @return 获取角色ID */
	public Integer[] getRoleIds() {
		Set<OSSRole> roles = getRoles();
		return OSSRole.fetchIds(roles);
	}
}
