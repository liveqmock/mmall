/**
 * OSSRolePermission.java
 *
 * Copyright 2012 lemon sea, Inc. All Rights Reserved.
 */
package net.mm2018.mmall.oss.domain;

import net.mm2018.mmall.oss.domain.base.BaseOSSRolePermission;

/**
 * 后台角色权限.
 * User: Cosmo<cosmo.wang@kaixinai.com>
 * Date: 12-3-24
 * Time: 上午8:33
 */
@SuppressWarnings("serial")
public class OSSRolePermission
        extends BaseOSSRolePermission {

    public OSSRolePermission(Integer roleId, String uri) {
        this.setRoleId(roleId);
        this.setUri(uri);
    }
}
