/**
 * OSSUtil.java
 *
 * Copyright 2012 lemon sea, Inc. All Rights Reserved.
 */
package net.mm2018.mmall.oss.util;

import cn.kxai.common.log.Log;
import cn.kxai.common.log.Logs;
import cn.kxai.common.web.util.RequestUtil;
import net.mm2018.mmall.common.config.Constants;
import net.mm2018.mmall.oss.domain.OSSUser;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 提供OSS中使用的方法.
 * User: Cosmo<cosmo.wang@kaixinai.com>
 * Date: 12-3-24
 * Time: 下午10:31
 */
public final class OSSUtil {

    private static final String USER_KEY = "_oss_user_key";

    private static Log log = Logs.get();

    /**
     * @param request HttpServletRequest
     * @return 获得用户
     */
    public static OSSUser getUser(HttpServletRequest request) {
        return (OSSUser) request.getAttribute(USER_KEY);
    }

    /**
     * 设置用户
     *
     * @param request HttpServletRequest
     * @param user    用户
     */
    public static void setUser(HttpServletRequest request, OSSUser user) {
        request.setAttribute(USER_KEY, user);
    }

    /**
     * @param request HttpServletRequest
     * @return 判断用户登录时的IP是否在白名单中
     */
    public static boolean isWhiteIp4Login(HttpServletRequest request) {
        String ip = RequestUtil.getIpAddress(request);
        for (String whiteIp : Constants.getOSSLoginWhiteIps()) {
            if (StringUtils.startsWith(ip, whiteIp)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param url 上传地址
     * @return 判断上传地址的域名是否在白名单中
     */
    public static boolean isWhiteDomain4Upload(String url) {
        try {
            String host = new URL(url).getHost();
            for (String whiteDomain : Constants.getOSSUploadWhiteDomains()) {
                if (StringUtils.startsWith(host, whiteDomain)) {
                    return true;
                }
            }
        } catch (MalformedURLException e) {
            log.errorf("%s,url=%s", e.getMessage(), url);
        }
        return false;
    }

    /**
     * 去除标签
     *
     * @param html 代码
     * @return 文本
     */
    public static String removeTag(String html) {
        return html.replaceAll("< [^<]+>", "");
    }

    private OSSUtil() {
    }

}
