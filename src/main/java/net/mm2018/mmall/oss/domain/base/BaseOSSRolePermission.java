/**
 * BaseOSSRolePermission.java
 *
 * Copyright 2012 lemon sea, Inc. All Rights Reserved.
 */
package net.mm2018.mmall.oss.domain.base;

import java.io.Serializable;

/**
 * 后台角色权限.
 * User: Cosmo<cosmo.wang@kaixinai.com>
 * Date: 12-3-24
 * Time: 下午11:11
 */
@SuppressWarnings("serial")
public abstract class BaseOSSRolePermission
        implements Serializable {

    private Integer roleId;
    private String uri;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("BaseOSSRolePermission");
        sb.append("{roleId=").append(roleId);
        sb.append(", uri='").append(uri).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
