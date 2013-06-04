/**
 * BaseOSSConfig.java
 *
 * Copyright 2012 lemon sea, Inc. All Rights Reserved.
 */
package net.mm2018.mmall.oss.domain.base;

import java.io.Serializable;

/**
 * 后台配置.
 * User: Cosmo<cosmo.wang@kaixinai.com>
 * Date: 12-3-24
 * Time: 上午8:14
 */
@SuppressWarnings("serial")
public abstract class BaseOSSConfig
        implements Serializable {

    private String cfgKey;
    private String cfgValue;

    public String getCfgKey() {
        return cfgKey;
    }

    public void setCfgKey(String cfgKey) {
        this.cfgKey = cfgKey;
    }

    public String getCfgValue() {
        return cfgValue;
    }

    public void setCfgValue(String cfgValue) {
        this.cfgValue = cfgValue;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("BaseOSSConfig");
        sb.append("{cfgKey='").append(cfgKey).append('\'');
        sb.append(", cfgValue='").append(cfgValue).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
