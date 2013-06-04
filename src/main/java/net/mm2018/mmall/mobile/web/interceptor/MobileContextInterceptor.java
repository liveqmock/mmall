/**
 * MobileContextInterceptor.java
 *
 * Copyright 2013 lemon sea, Inc. All Rights Reserved.
 */
package net.mm2018.mmall.mobile.web.interceptor;

import cn.kxai.common.log.Log;
import cn.kxai.common.log.Logs;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 上下文信息拦截器.
 * User: Cosmo<cosmo.wang@ningmenghai.com>
 * Date: 13-6-4
 * Time: 下午3:26
 */
public class MobileContextInterceptor
        extends HandlerInterceptorAdapter {

    private static final String CSS = "css";
    private static final String NOW = "now";

    private static Log log = Logs.get();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        if (null != modelAndView) {
            // 设置时间
            modelAndView.getModelMap().addAttribute(NOW, new Date());
        }
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        super.afterConcurrentHandlingStarted(request, response, handler);
    }
}
