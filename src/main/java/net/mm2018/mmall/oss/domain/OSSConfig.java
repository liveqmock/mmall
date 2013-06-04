/**
 * OSSConfig.java
 *
 * Copyright 2012 lemon sea, Inc. All Rights Reserved.
 */
package net.mm2018.mmall.oss.domain;

import net.mm2018.mmall.oss.domain.base.BaseOSSConfig;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 后台配置.
 * User: Cosmo<cosmo.wang@kaixinai.com>
 * Date: 12-3-24
 * Time: 上午8:16
 */
@SuppressWarnings("serial")
public class OSSConfig
        extends BaseOSSConfig {

    /** 登录配置 */
    public static class ConfigLogin {
        public static final String LOGIN_ERROR_INTERVAL = "login_error_interval";
        public static final String LOGIN_ERROR_TIMES = "login_error_times";
        // 默认间隔30分钟
        private static final Integer DEFAULT_ERROR_INTERVAL = 30;
        // 默认错误次数3次
        private static final Integer DEFAULT_ERROR_TIMES = 3;

        private Map<String, String> attr;

        public static ConfigLogin create(Map<String, String> map) {
            ConfigLogin configLogin = new ConfigLogin();
            configLogin.setAttr(map);
            return configLogin;
        }

        public Integer getErrorInterval() {
            String interval = getAttr().get(LOGIN_ERROR_INTERVAL);
            if (NumberUtils.isDigits(interval)) {
                return Integer.parseInt(interval);
            } else {
                // 默认间隔30分钟
                return DEFAULT_ERROR_INTERVAL;
            }
        }

        public void setErrorInterval(Integer errorInterval) {
            if (null != errorInterval) {
                getAttr().put(LOGIN_ERROR_INTERVAL, errorInterval.toString());
            } else {
                getAttr().put(LOGIN_ERROR_INTERVAL, null);
            }
        }

        public Integer getErrorTimes() {
            String times = getAttr().get(LOGIN_ERROR_TIMES);
            if (NumberUtils.isDigits(times)) {
                return Integer.parseInt(times);
            } else {
                // 默认3次
                return DEFAULT_ERROR_TIMES;
            }
        }

        public void setErrorTimes(Integer errorTimes) {
            if (null != errorTimes) {
                getAttr().put(LOGIN_ERROR_TIMES, errorTimes.toString());
            } else {
                getAttr().put(LOGIN_ERROR_TIMES, null);
            }
        }

        public Map<String, String> getAttr() {
            if (null == attr) {
                attr = new HashMap<String, String>();
            }
            return attr;
        }

        public void setAttr(Map<String, String> attr) {
            this.attr = attr;
        }
    }
}
