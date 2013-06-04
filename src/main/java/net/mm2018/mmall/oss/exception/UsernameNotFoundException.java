/**
 * UsernameNotFoundException.java
 *
 * Copyright 2012 lemon sea, Inc. All Rights Reserved.
 */
package net.mm2018.mmall.oss.exception;

/**
 * 用户名没有找到异常.
 * User: Cosmo<cosmo.wang@kaixinai.com>
 * Date: 12-3-24
 * Time: 下午2:38
 */
@SuppressWarnings("serial")
public class UsernameNotFoundException
        extends AuthenticationException {

    public UsernameNotFoundException() {
    }

    public UsernameNotFoundException(String msg) {
        super(msg);
    }
}
