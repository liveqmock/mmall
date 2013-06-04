/**
 * BadCredentialException.java
 *
 * Copyright 2012 lemon sea, Inc. All Rights Reserved.
 */
package net.mm2018.mmall.oss.exception;

/**
 * 认证信息错误异常.如:密码错误.
 * User: Cosmo<cosmo.wang@kaixinai.com>
 * Date: 12-3-24
 * Time: 下午1:45
 */
@SuppressWarnings("serial")
public class BadCredentialException
        extends AuthenticationException {

    public BadCredentialException() {
    }

    public BadCredentialException(String msg) {
        super(msg);
    }
}
