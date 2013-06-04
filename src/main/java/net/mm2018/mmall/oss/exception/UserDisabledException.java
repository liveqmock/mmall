/**
 * UserDisabledException.java
 *
 * Copyright 2012 lemon sea, Inc. All Rights Reserved.
 */
package net.mm2018.mmall.oss.exception;

/**
 * 用户被禁用异常.
 * User: Cosmo<cosmo.wang@kaixinai.com>
 * Date: 12-3-25
 * Time: 下午7:19
 */
@SuppressWarnings("serial")
public class UserDisabledException
        extends AccountStatusException {

    public UserDisabledException() {
    }

    public UserDisabledException(String msg) {
        super(msg);
    }

    public UserDisabledException(String msg, Object extraInformation) {
        super(msg, extraInformation);
    }
}
