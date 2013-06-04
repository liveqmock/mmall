/**
 * OSSLogSvc.java
 *
 * Copyright 2013 lemon sea, Inc. All Rights Reserved.
 */
package net.mm2018.mmall.oss.service;

import cn.kxai.common.lang.CalendarUtil;
import cn.kxai.common.page.PageList;
import cn.kxai.common.page.Paginator;
import cn.kxai.common.web.util.RequestUtil;
import com.google.common.collect.Maps;
import net.mm2018.mmall.common.mybatis.PageQueryUtil;
import net.mm2018.mmall.oss.dao.OSSLogDao;
import net.mm2018.mmall.oss.dao.OSSUserDao;
import net.mm2018.mmall.oss.domain.OSSLog;
import net.mm2018.mmall.oss.domain.OSSUser;
import net.mm2018.mmall.oss.util.OSSUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 日志Service.
 * User: Cosmo<cosmo.wang@ningmenghai.com>
 * Date: 13-3-8
 * Time: 下午12:37
 */
@Transactional
@Service
public class OSSLogSvc {
    @Autowired
    private OSSLogDao logDao;
    @Autowired
    private OSSUserDao userDao;

    /**
     * @param logId 日志ID
     * @return 根据日志ID获取日志
     */
    @Transactional(readOnly = true)
    public OSSLog getByLogId(Integer logId) {
        return logDao.findByLogId(logId);
    }

    /**
     * @param type     类型
     * @param username 用户名
     * @param title    日志标题
     * @param pageNo   页码
     * @param pageSize 每页大小
     * @return 根据条件获取日志页
     */
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public PageList<OSSLog> getPageListByQuery(Integer type, String username, String title, Integer pageNo,
            Integer pageSize) {
        OSSUser user = null;
        if (!StringUtils.isBlank(username)) {
            user = userDao.findByUsername(username);
        }
        Map<String, Object> params = Maps.newHashMap();
        params.put("type", type);
        params.put("userId", null != user ? user.getUserId() : null);
        params.put("title", title);
        int totalResult = logDao.countListByQuery(params);
        PageList<OSSLog> logPageList;
        if (totalResult <= 0) {
            logPageList = new PageList<OSSLog>(pageNo, pageSize, 0);
            return logPageList;
        }
        Paginator paginator = new Paginator(pageNo, pageSize, totalResult);
        logPageList = new PageList<OSSLog>(paginator);
        List<OSSLog> list = logDao.findListByQuery(PageQueryUtil.attachPageQueryVariable(params, paginator));
        logPageList.setList(list);
        return logPageList;
    }

    /**
     * 登录失败
     *
     * @param request HttpServletRequest
     * @param title   日志标题
     * @param content 日志内容
     * @return 日志对象
     */
    public OSSLog loginFailure(HttpServletRequest request, String title, String content) {
        String ip = RequestUtil.getIpAddress(request);
        UrlPathHelper helper = new UrlPathHelper();
        String uri = helper.getOriginatingRequestUri(request);
        return create(OSSLog.TypeEnum.LOGIN_FAILURE.getCode(), null, ip, uri, title, content);
    }

    /**
     * 登录成功
     *
     * @param request HttpServletRequest
     * @param user    用户
     * @param title   日志标题
     * @return 日志对象
     */
    public OSSLog loginSuccess(HttpServletRequest request, OSSUser user, String title) {
        String ip = RequestUtil.getIpAddress(request);
        UrlPathHelper helper = new UrlPathHelper();
        String uri = helper.getOriginatingRequestUri(request);
        return create(OSSLog.TypeEnum.LOGIN_SUCCESS.getCode(), user, ip, uri, title, null);
    }

    /**
     * 操作日志
     *
     * @param request HttpServletRequest
     * @param title   日志标题
     * @param content 日志内容
     * @return 日志对象
     */
    public OSSLog operating(HttpServletRequest request, String title, String content) {
        OSSUser user = OSSUtil.getUser(request);
        String ip = RequestUtil.getIpAddress(request);
        UrlPathHelper helper = new UrlPathHelper();
        String uri = helper.getOriginatingRequestUri(request);
        return create(OSSLog.TypeEnum.OPERATING.getCode(), user, ip, uri, title, content);
    }

    /**
     * @param type 日志类型
     * @param days 天数
     * @return 批量移除
     */
    public Integer removeBatch(Integer type, Integer days) {
        Date date = null;
        if (null != days && days > 0) {
            date = CalendarUtil.add(Calendar.DAY_OF_MONTH, new Date(), -days);
        }
        Map<String, Object> params = Maps.newHashMap();
        params.put("type", type);
        params.put("before", date);
        return logDao.deleteBatch(params);
    }

    /**
     * @param logId 日志ID
     * @return 根据日志ID移除日志
     */
    public Integer removeByLogId(Integer logId) {
        if (logDao.deleteByLogId(logId) > 0) {
            return logId;
        }
        return 0;
    }

    /**
     * @param logIds 日志ID数组
     * @return 根据日志ID批量删除日志
     */
    public Integer[] removeByLogIds(Integer[] logIds) {
        Integer[] results = new Integer[logIds.length];
        for (int i = 0, n = logIds.length; i < n; i++) {
            results[i] = removeByLogId(logIds[i]);
        }
        return results;
    }

    /**
     * 保存日志
     *
     * @param type    日志类型
     * @param user    用户
     * @param ip      IP
     * @param url     url地址
     * @param title   日志标题
     * @param content 日志内容
     * @return 日志对象
     */
    private OSSLog create(Integer type, OSSUser user, String ip, String url, String title, String content) {
        OSSLog log = new OSSLog();
        log.setUserId(null != user ? user.getUserId() : null);
        log.setType(type);
        log.setIp(ip);
        log.setUrl(url);
        log.setTitle(title);
        log.setContent(content);
        logDao.save(log);
        return log;
    }
}
