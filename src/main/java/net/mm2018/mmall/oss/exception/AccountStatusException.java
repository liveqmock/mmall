/**
 * AccountStatusException.java
 *
 * Copyright 2012 lemon sea, Inc. All Rights Reserved.
 */
package net.mm2018.mmall.oss.exception;

/**
 * 账号状态异常.
 * User: Cosmo<cosmo.wang@kaixinai.com>
 * Date: 12-3-25
 * Time: 下午7:18
 */
@SuppressWarnings("serial")
public class AccountStatusException
        extends AuthenticationException {

    public AccountStatusException() {
    }

    public AccountStatusException(String msg) {
        super(msg);
    }

    public AccountStatusException(String msg, Object extraInformation) {
        super(msg, extraInformation);
    }
}
