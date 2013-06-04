/**
 * OSSAuthentication.java
 *
 * Copyright 2012 lemon sea, Inc. All Rights Reserved.
 */
package net.mm2018.mmall.oss.domain;

import net.mm2018.mmall.oss.domain.base.BaseOSSAuthentication;

import java.sql.Timestamp;
import java.util.Date;

/**
 * 后台认证信息.
 * User: Cosmo<cosmo.wang@kaixinai.com>
 * Date: 12-3-24
 * Time: 下午12:49
 */
@SuppressWarnings("serial")
public class OSSAuthentication
        extends BaseOSSAuthentication {

    /** 初始化 */
    public void init() {
        Date now = new Timestamp(System.currentTimeMillis());
        setLoginTime(now);
        setUpdateTime(now);
    }
}
