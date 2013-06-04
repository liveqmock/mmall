/**
 * AdminContextInterceptor.java
 *
 * Copyright 2012 lemon sea, Inc. All Rights Reserved.
 */
package net.mm2018.mmall.oss.web.interceptor;

import cn.kxai.common.lang.ExceptionUtil;
import net.mm2018.mmall.oss.domain.OSSUser;
import net.mm2018.mmall.oss.service.OSSAuthenticationSvc;
import net.mm2018.mmall.oss.service.OSSUserSvc;
import net.mm2018.mmall.oss.util.OSSThreadVariable;
import net.mm2018.mmall.oss.util.OSSUtil;
import net.mm2018.mmall.oss.web.controller.OSSLoginController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

/**
 * 上下文信息拦截器.包括登录信息、权限信息
 *
 * User: Cosmo<cosmo.wang@kaixinai.com>
 * Date: 12-3-24
 * Time: 下午10:23
 */
public class AdminContextInterceptor
        extends HandlerInterceptorAdapter {

    public static final String PERMISSION_MODEL = "_oss_permission_key";

    @Autowired
    private OSSUserSvc ossUserSvc;
    @Autowired
    private OSSAuthenticationSvc ossAuthenticationSvc;

    private Integer adminId;
    private boolean auth = true;
    private String[] excludeUrls;

    private String loginUrl;

    private String returnUrl;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String uri = getURI(request);
        // 不在验证的范围内
        if (exclude(uri)) {
            return true;
        }

        OSSUser user = null;
        if (null != adminId && adminId > 0) {
            // 指定管理员(开发状态)
            user = ossUserSvc.getByUserId(adminId);
            if (null == user) {
                throw new IllegalStateException("User ID=" + adminId + " not found!");
            }
        } else {
            // 正常状态
            Integer userId = ossAuthenticationSvc.retrieveUserIdFromSession(request);
            if (null != userId) {
                user = ossUserSvc.getByUserId(userId);
            }
        }

        if (null != user) {
            // 设置密码为null,防止泄漏
            user.setPassword(null);
        }
        // 此时用户可以为null
        OSSUtil.setUser(request, user);
        // User加入线程变量
        OSSThreadVariable.setUser(user);

        // 用户为null跳转到登陆页面
        if (null == user) {
            response.sendRedirect(getLoginUrl(request));
            return false;
        }

        // 用户不是管理员,提示无权限.
        if (!user.getAdmin()) {
            request.setAttribute("message", "您不是管理员，禁止访问后台。");
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return false;
        }

        // 没有访问权限，提示无权限。
        boolean viewonly = user.getViewonlyAdmin();
        if (auth && !user.isSuper() && !passPermission(uri, user.getPerms(), viewonly)) {
            request.setAttribute("message", "没有该地址的访问授权。");
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        OSSUser user = OSSUtil.getUser(request);
        // 不控制权限时perm为null,PermistionDirective标签将以此作为依据不处理权限问题
        if (auth && null != user && !user.isSuper() && null != modelAndView && null != modelAndView.getModelMap() &&
                null != modelAndView.getViewName() && !modelAndView.getViewName().startsWith("redirect:")) {
            modelAndView.getModelMap().addAttribute(PERMISSION_MODEL, user.getPerms());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        OSSThreadVariable.removeUser();
    }

    /**
     * pass permission check
     *
     * @param uri      uri
     * @param perms    permission set
     * @param viewonly only view oss;
     * @return boolean
     */
    private boolean passPermission(String uri, Set<String> perms, boolean viewonly) {
        if (null == perms) {
            return false;
        }
        String u;
        int i;
        for (String perm : perms) {
            if (uri.startsWith(perm)) {
                // 只读管理员
                if (viewonly) {
                    // 获得最后一个 '/' 的URI地址。
                    i = uri.lastIndexOf('/');
                    if (i == -1) {
                        throw ExceptionUtil.makeThrow("uri must start width '/':%s", uri);
                    }
                    u = uri.substring(i + 1);
                    // 操作型地址被禁止
                    if (u.startsWith("o_")) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    /**
     * @param request HttpServletRequest
     * @return 获取登录url
     */
    private String getLoginUrl(HttpServletRequest request) {
        StringBuilder buff = new StringBuilder();
        if (loginUrl.startsWith("/")) {
            String ctx = request.getContextPath();
            if (!StringUtils.isBlank(ctx)) {
                buff.append(ctx);
            }
        }
        buff.append(loginUrl).append("?");
        buff.append(OSSLoginController.RETURN_URL).append("=").append(returnUrl);
        return buff.toString();
    }

    /**
     * @param request HttpServletRequest
     * @return 获得第二个路径分隔符的位置
     */
    private String getURI(HttpServletRequest request) {
        UrlPathHelper helper = new UrlPathHelper();
        String uri = helper.getOriginatingRequestUri(request);
        String ctxPath = helper.getOriginatingContextPath(request);
        int start = 0, i = 0, count = 2;
        if (!StringUtils.isBlank(ctxPath)) {
            count++;
        }
        while (i < count && start != -1) {
            start = uri.indexOf('/', start + 1);
            i++;
        }
        if (start <= 0) {
            throw new IllegalStateException("admin access path not like '/mmall/oss/...' pattern: " + uri);
        }
        return uri.substring(start);
    }

    /**
     * @param uri uri
     * @return 是否排除uri
     */
    private boolean exclude(String uri) {
        if (null != excludeUrls) {
            for (String excludeUrl : excludeUrls) {
                if (excludeUrl.equals(uri) || uri.contains(excludeUrl)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public void setAuth(boolean auth) {
        this.auth = auth;
    }

    public void setExcludeUrls(String[] excludeUrls) {
        this.excludeUrls = excludeUrls;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }
}
