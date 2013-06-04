/**
 * OSSRole.java
 *
 * Copyright 2012 lemon sea, Inc. All Rights Reserved.
 */
package net.mm2018.mmall.oss.domain;

import net.mm2018.mmall.oss.domain.base.BaseOSSRole;

import java.util.Collection;

/**
 * 后台角色.
 * User: Cosmo<cosmo.wang@kaixinai.com>
 * Date: 12-3-24
 * Time: 上午8:32
 */
@SuppressWarnings("serial")
public class OSSRole
        extends BaseOSSRole {

    /**
     * @param roles 角色集合
     * @return 获取角色ID数组
     */
    public static Integer[] fetchIds(Collection<OSSRole> roles) {
        if (null == roles) {
            return null;
        }
        Integer[] ids = new Integer[roles.size()];
        int i = 0;
        for (OSSRole role : roles) {
            ids[i++] = role.getRoleId();
        }
        return ids;
    }
}
