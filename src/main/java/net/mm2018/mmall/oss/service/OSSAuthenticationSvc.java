/**
 * OSSAuthenticationSvcImpl.java
 *
 * Copyright 2012 lemon sea, Inc. All Rights Reserved.
 */
package net.mm2018.mmall.oss.service;

import cn.kxai.common.lang.IdentityUtil;
import cn.kxai.common.log.Log;
import cn.kxai.common.log.Logs;
import cn.kxai.common.web.util.RequestUtil;
import net.mm2018.mmall.common.cache.ehcache.EhcacheService;
import net.mm2018.mmall.oss.dao.OSSAuthenticationDao;
import net.mm2018.mmall.oss.domain.OSSAuthentication;
import net.mm2018.mmall.oss.domain.OSSUser;
import net.mm2018.mmall.oss.exception.BadCredentialException;
import net.mm2018.mmall.oss.exception.UsernameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Date;

/**
 * 后台鉴权业务接口实现.
 * User: Cosmo<cosmo.wang@kaixinai.com>
 * Date: 12-3-24
 * Time: 下午2:42
 */
@Transactional
@Service
public class OSSAuthenticationSvc {
    /** 认证信息session key */
    public static final String AUTH_KEY = "_oss_auth_key";

    private static Log log = Logs.get();
    @Autowired
    private OSSAuthenticationDao ossAuthenticationDao;
    @Autowired
    private OSSUserSvc ossUserSvc;
    @Autowired
    private EhcacheService ehcacheService;

    // 过期时间,30分钟
    private int timeout = 120 * 60 * 1000;
    // 间隔时间,4小时
    private int interval = 4 * 60 * 60 * 1000;
    // 刷新时间
    private long refreshTime = getNextRefreshTime(System.currentTimeMillis(), this.interval);

    public OSSAuthentication login(String username, String password, String ip, HttpServletRequest request)
            throws UsernameNotFoundException, BadCredentialException {
        OSSUser user = ossUserSvc.login(username, password, ip);
        OSSAuthentication authentication = new OSSAuthentication();
        authentication.setUserId(user.getUserId());
        authentication.setUsername(user.getUsername());
        authentication.setEmail(user.getEmail());
        authentication.setLoginIP(ip);
        create(authentication);
        RequestUtil.setSessionAttribute(request, AUTH_KEY, authentication.getAuthenticationId());
        return authentication;
    }

    public void removeByAuthenticationId(String authenticationId) {
        Integer result = ossAuthenticationDao.deleteById(authenticationId);
        if (result > 0) {
            ehcacheService.remove(buildCacheKey(authenticationId));
        }
    }

    public OSSAuthentication retrieve(String authenticationId) {
        long current = System.currentTimeMillis();
        // 是否刷新数据库
        if (refreshTime < current) {
            refreshTime = getNextRefreshTime(current, interval);
            int count = ossAuthenticationDao.deleteExpire(new Date(current - timeout));
            log.infof("refresh Authentication, delete count: %d", count);
        }
        OSSAuthentication authentication = getById(authenticationId);
        if (null != authentication && authentication.getUpdateTime().getTime() + timeout > current) {
            authentication.setUpdateTime(new Timestamp(current));
            modifyUpdateTime(authentication);
            return authentication;
        }
        return null;
    }

    public Integer retrieveUserIdFromSession(HttpServletRequest request) {
        String authenticationId = (String) RequestUtil.getSessionAttribute(request, AUTH_KEY);
        if (null == authenticationId) {
            return null;
        }
        OSSAuthentication authentication = retrieve(authenticationId);
        return null == authentication ? null : authentication.getUserId();
    }

    /**
     * 设置刷新数据库时间,默认4小时
     *
     * @param interval 单位分钟
     */
    @Transactional(readOnly = true)
    public void setInterval(int interval) {
        this.interval = interval * 60 * 1000;
        this.refreshTime = getNextRefreshTime(System.currentTimeMillis(), this.interval);
    }

    /**
     * 设置认证过期时间,默认30分钟
     *
     * @param timeout 单位分钟
     */
    @Transactional(readOnly = true)
    public void setTimeout(int timeout) {
        this.timeout = timeout * 60 * 1000;
    }

    /**
     * create authentication
     *
     * @param authentication 认证信息
     * @return 认证信息
     */
    private OSSAuthentication create(OSSAuthentication authentication) {
        authentication.setAuthenticationId(IdentityUtil.uuid2());
        authentication.init();
        try {
            ossAuthenticationDao.save(authentication);
        } catch (Exception e) {
            return null;
        }
        return authentication;
    }

    /**
     * @param authenticationId ID
     * @return 根据ID获取认证信息
     */
    private OSSAuthentication getById(String authenticationId) {
        OSSAuthentication authentication = (OSSAuthentication) ehcacheService.get(buildCacheKey(authenticationId));
        if (null != authentication) {
            return authentication;
        }
        return ossAuthenticationDao.findById(authenticationId);
    }

    /**
     * @param current  当前时间
     * @param interval 间隔时间
     * @return 获得下一个刷新时间
     */
    private long getNextRefreshTime(long current, int interval) {
        return current + interval;
    }

    /**
     * 修改认证信息更新时间
     *
     * @param authentication 认证信息
     */
    private void modifyUpdateTime(OSSAuthentication authentication) {
        ossAuthenticationDao.updateAuthenticationTime(authentication);
        ehcacheService.put(buildCacheKey(authentication.getAuthenticationId()), authentication);
    }

    /**
     * @param authenticationId 授权id
     * @return 构建缓存key
     */
    private String buildCacheKey(String authenticationId) {
        return "OSSAuthentication." + authenticationId;
    }
}
