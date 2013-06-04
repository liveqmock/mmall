/**
 * AuthenticationException.java
 *
 * Copyright 2012 lemon sea, Inc. All Rights Reserved.
 */
package net.mm2018.mmall.oss.exception;

/**
 * 鉴权异常.
 * User: Cosmo<cosmo.wang@kaixinai.com>
 * Date: 12-3-24
 * Time: 下午2:38
 */
@SuppressWarnings("serial")
public class AuthenticationException
        extends Exception {

    private Object extraInformation;

    public AuthenticationException() {
    }

    public AuthenticationException(String msg) {
        super(msg);
    }

    public AuthenticationException(String msg, Object extraInformation) {
        super(msg);
        this.extraInformation = extraInformation;
    }

    /** @return 额外附加信息 or <code>null</code> */
    public Object getExtraInformation() {
        return extraInformation;
    }

    public void clearExtraInformation() {
        this.extraInformation = null;
    }
}
