/**
 * Constants.java
 *
 * Copyright 2013 lemon sea, Inc. All Rights Reserved.
 */
package net.mm2018.mmall.common.config;

import cn.kxai.common.lang.PropertiesLoader;
import org.apache.commons.lang3.StringUtils;

/**
 * 项目级别常量.
 * User: Cosmo<cosmo.wang@ningmenghai.com>
 * Date: 13-3-5
 * Time: 下午3:04
 */
public final class Constants {

    // oss controller返回消息key
    public static final String OSS_MESSAGE = "message";

    private static PropertiesLoader propertiesLoader =
            new PropertiesLoader("classpath:/config/constants.properties", "classpath:/config/ip.properties",
                    "classpath:/config/redis.properties");

    public static String get(String key) {
        return get(key, "");
    }

    public static String get(String key, String defaultValue) {
        return propertiesLoader.getProperty(key, defaultValue);
    }

    /** @return 返回域名 */
    public static String getDomain() {
        return propertiesLoader.getProperty("domain");
    }

    /** @return 获取资源网络地址前缀 */
    public static String getResourceUrlPrefix() {
        return propertiesLoader.getProperty("resource.url.prefix");
    }

    /** @return 获取QMS资源路径前缀 */
    public static String getQmsSourceUriPrefix() {
        return getResourceUriPrefix() + "/qms";
    }

    /** @return 获取资源路径前缀 */
    public static String getResourceUriPrefix() {
        return propertiesLoader.getProperty("resource.uri.prefix");
    }

    /** @return 获取后台登录白名单IP列表 */
    public static String[] getOSSLoginWhiteIps() {
        String whiteIps = propertiesLoader.getProperty("oss.login.white.ips");
        return StringUtils.split(whiteIps, '|');
    }

    /** @return 获取后台上传地址的白名单域名列表 */
    public static String[] getOSSUploadWhiteDomains() {
        String whiteDomains = propertiesLoader.getProperty("oss.upload.white.domains");
        return StringUtils.split(whiteDomains, '|');
    }

    private Constants() {
    }
}
