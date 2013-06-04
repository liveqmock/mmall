/**
 * OSSThreadVariable.java
 *
 * Copyright 2012 lemon sea, Inc. All Rights Reserved.
 */
package net.mm2018.mmall.oss.util;

import net.mm2018.mmall.oss.domain.OSSUser;

/**
 * 后台线程变量
 * User: Cosmo<cosmo.wang@kaixinai.com>
 * Date: 12-3-24
 * Time: 下午10:23
 */
public final class OSSThreadVariable {

    /** 当前用户线程变量 */
    private static ThreadLocal<OSSUser> ossUserVariable = new ThreadLocal<OSSUser>();

    /** @return 获得当前用户 */
    public static OSSUser getUser() {
        return ossUserVariable.get();
    }

    /**
     * 设置当前用户
     *
     * @param user 用户
     */
    public static void setUser(OSSUser user) {
        ossUserVariable.set(user);
    }

    /** 移除当前用户 */
    public static void removeUser() {
        ossUserVariable.remove();
    }

    private OSSThreadVariable() {
    }
}
