/**
 * OSSUserSvcImpl.java
 *
 * Copyright 2012 lemon sea, Inc. All Rights Reserved.
 */
package net.mm2018.mmall.oss.service;

import cn.kxai.common.lang.DigestUtil;
import cn.kxai.common.page.PageList;
import cn.kxai.common.page.Paginator;
import com.google.common.collect.Maps;
import net.mm2018.mmall.common.mybatis.PageQueryUtil;
import net.mm2018.mmall.oss.dao.OSSUserDao;
import net.mm2018.mmall.oss.domain.OSSConfig;
import net.mm2018.mmall.oss.domain.OSSUser;
import net.mm2018.mmall.oss.domain.OSSUserExt;
import net.mm2018.mmall.oss.exception.BadCredentialException;
import net.mm2018.mmall.oss.exception.UsernameNotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

/**
 * 后台用户业务接口实现.
 * User: Cosmo<cosmo.wang@kaixinai.com>
 * Date: 12-3-23
 * Time: 下午6:25
 */
@Transactional
@Service
public class OSSUserSvc {

    @Autowired
    private OSSUserDao ossUserDao;
    @Autowired
    private OSSConfigSvc ossConfigSvc;

    @Transactional(readOnly = true)
    public OSSUser getByUserId(Integer userId) {
        return ossUserDao.findById(userId);
    }

    @Transactional(readOnly = true)
    public OSSUser getByUsername(String username) {
        return ossUserDao.findByUsername(username);
    }

    public OSSUser login(String username, String password, String ip)
            throws UsernameNotFoundException, BadCredentialException {
        OSSUser user = getByUsername(username);
        if (null == user) {
            throw new UsernameNotFoundException("username not found: " + username);
        }
        String md5Password = DigestUtil.md5Hex(password);
        if (!user.getPassword().equals(md5Password)) {
            modifyLoginError(user.getUserId(), ip);
            throw new BadCredentialException("password invalid");
        }
        modifyLoginSuccess(user.getUserId(), ip);
        return user;
    }

    @Transactional(readOnly = true)
    public Integer errorRemaining(String username) {
        if (StringUtils.isBlank(username)) {
            return null;
        }
        OSSUser user = getByUsername(username);
        if (null == user) {
            return null;
        }
        long now = System.currentTimeMillis();
        OSSConfig.ConfigLogin configLogin = ossConfigSvc.getConfigLogin();
        int maxErrorTimes = configLogin.getErrorTimes();
        int maxErrorInterval = configLogin.getErrorInterval() * 60 * 1000;
        Integer errorCount = user.getErrorCount();
        Date errorTime = user.getErrorTime();
        if (errorCount <= 0 || null == errorTime || errorTime.getTime() + maxErrorInterval < now) {
            return maxErrorTimes;
        }
        return maxErrorTimes - errorCount;
    }

    public void modifyLoginInfo(Integer userId, String ip) {
        Date now = new Date(System.currentTimeMillis());
        OSSUser user = getByUserId(userId);
        if (null != user) {
            user.setLoginCount(user.getLoginCount() + 1);
            user.setLastLoginIP(ip);
            user.setLastLoginTime(now);
            ossUserDao.updateLoginInfo(user);
        }
    }

    @Transactional(readOnly = true)
    public boolean isValidPassword(Integer userId, String password) {
        OSSUser user = getByUserId(userId);
        String md5Password = DigestUtil.md5Hex(password);
        return user.getPassword().equals(md5Password);
    }

    public OSSUserExt modifyUserExt(OSSUserExt ext, OSSUser user) {
        OSSUserExt entity = ossUserDao.findUserExtByUserId(user.getUserId());
        if (null == entity) {
            return createUserExt(ext, user);
        }
        ossUserDao.updateUserExt(ext);
        return entity;
    }

    public OSSUser createAdmin(String username, String email, String password, String ip, Boolean viewonlyAdmin,
            Boolean selfAdmin, Integer rank, Integer[] roleIds, OSSUserExt ext) {
        OSSUser user = new OSSUser();
        user.init();
        user.setUsername(username);
        user.setPassword(DigestUtil.md5Hex(password));
        user.setEmail(email);
        user.setRegisterIP(ip);
        user.setLastLoginIP(ip);
        user.setRank(rank);
        user.setAdmin(true);
        user.setViewonlyAdmin(viewonlyAdmin);
        user.setSelfAdmin(selfAdmin);
        user.setDisabled(false);
        ossUserDao.saveUser(user);
        ext.setUserId(user.getUserId());
        ossUserDao.saveUserExt(ext);
        if (null != roleIds) {
            Map<String, Object> params;
            for (Integer roleId : roleIds) {
                params = Maps.newHashMap();
                params.put("userId", user.getUserId());
                params.put("roleId", roleId);
                ossUserDao.saveUserRole(params);
            }
        }
        return user;
    }

    public Integer[] removeAdminByIds(Integer[] ids) {
        Integer[] result = new Integer[ids.length];
        for (int i = 0, len = ids.length; i < len; i++) {
            result[i] = removeAdminById(ids[i]);
        }
        return result;
    }

    public OSSUser modifyAdmin(OSSUser user, OSSUserExt userExt, String password, Integer[] roleIds) {
        ossUserDao.updateUserExt(userExt);
        String email = user.getEmail();
        Integer rank = user.getRank();
        Boolean viewonlyAdmin = user.getViewonlyAdmin();
        Boolean selfAdmin = user.getSelfAdmin();
        Boolean disabled = user.getDisabled();
        user = getByUserId(user.getUserId());
        if (!StringUtils.isBlank(email)) {
            user.setEmail(email);
        } else {
            user.setEmail(null);
        }
        user.setRank(rank);
        user.setViewonlyAdmin(viewonlyAdmin);
        user.setSelfAdmin(selfAdmin);
        user.setDisabled(disabled);
        if (!StringUtils.isBlank(password)) {
            user.setPassword(DigestUtil.md5Hex(password));
        }
        ossUserDao.update(user);
        // 更新角色
        ossUserDao.deleteUserRoleByUserId(user.getUserId());
        if (null != roleIds) {
            Map<String, Object> params;
            for (Integer roleId : roleIds) {
                params = Maps.newHashMap();
                params.put("userId", user.getUserId());
                params.put("roleId", roleId);
                ossUserDao.saveUserRole(params);
            }
        }
        return user;
    }

    public void modifyPwdEmail(Integer userId, String password, String email) {
        OSSUser user = getByUserId(userId);
        if (StringUtils.isBlank(email)) {
            user.setEmail(null);
        } else {
            user.setEmail(email);
        }
        if (StringUtils.isNotBlank(password)) {
            user.setPassword(DigestUtil.md5Hex(password));
        }
        ossUserDao.updatePwdEmail(user);
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public PageList<OSSUser> getPageByQuery(String queryUsername, String queryEmail, Boolean queryDisabled,
            Boolean admin, Integer rank, int pageNo, int pageSize) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("queryUsername", queryUsername);
        params.put("queryEmail", queryEmail);
        params.put("queryDisabled", queryDisabled);
        params.put("admin", admin);
        params.put("rank", rank);
        int totalResult = ossUserDao.countListByQuery(params);
        Paginator paginator = new Paginator(pageNo, pageSize, totalResult);
        PageList<OSSUser> userPageList = new PageList<OSSUser>(paginator);
        if (totalResult <= 0) {
            return userPageList;
        }
        params = PageQueryUtil.attachPageQueryVariable(params, paginator);
        userPageList.setList(ossUserDao.findListByQuery(params));
        return userPageList;
    }

    /**
     * 创建扩展信息
     *
     * @param ext  用户扩展
     * @param user 用户
     * @return 用户扩展
     */
    private OSSUserExt createUserExt(OSSUserExt ext, OSSUser user) {
        ext.setUserId(user.getUserId());
        ossUserDao.saveUserExt(ext);
        return ext;
    }

    /**
     * 修改用户登录错误信息
     *
     * @param userId user id
     * @param ip     login ip
     */
    private void modifyLoginError(Integer userId, String ip) {
        OSSUser user = getByUserId(userId);
        Date now = new Timestamp(System.currentTimeMillis());
        OSSConfig.ConfigLogin configLogin = ossConfigSvc.getConfigLogin();
        int errorInterval = configLogin.getErrorInterval();
        Date errorTime = user.getErrorTime();
        user.setErrorIP(ip);
        if (null == errorTime || errorTime.getTime() + errorInterval * 60 * 1000 < now.getTime()) {
            user.setErrorTime(now);
            user.setErrorCount(1);
        } else {
            user.setErrorCount(user.getErrorCount() + 1);
        }
        ossUserDao.updateLoginError(user);
    }

    /**
     * 修改用户登录成功信息
     *
     * @param id user id
     * @param ip login ip
     */
    private void modifyLoginSuccess(Integer id, String ip) {
        OSSUser user = getByUserId(id);
        Date now = new Timestamp(System.currentTimeMillis());

        user.setLoginCount(user.getLoginCount() + 1);
        user.setLastLoginIP(ip);
        user.setLastLoginTime(now);

        user.setErrorCount(0);
        user.setErrorTime(null);
        user.setErrorIP(null);

        ossUserDao.updateLoginSuccess(user);
    }

    /**
     * 根据ID删除管理员
     *
     * @param userId 管理员ID
     * @return 删除结果
     */
    private Integer removeAdminById(Integer userId) {
        int result = ossUserDao.deleteById(userId);
        if (result > 0) {
            ossUserDao.deleteUserExtById(userId);
        }
        return result;
    }
}
