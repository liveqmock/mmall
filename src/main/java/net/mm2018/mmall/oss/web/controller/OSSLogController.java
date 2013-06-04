/**
 * OSSLogController.java
 *
 * Copyright 2013 lemon sea, Inc. All Rights Reserved.
 */
package net.mm2018.mmall.oss.web.controller;

import cn.kxai.common.log.Log;
import cn.kxai.common.log.Logs;
import cn.kxai.common.page.PageList;
import cn.kxai.common.web.util.CookieUtil;
import cn.kxai.common.web.util.RequestUtil;
import net.mm2018.mmall.oss.domain.OSSLog;
import net.mm2018.mmall.oss.service.OSSLogSvc;
import net.mm2018.mmall.oss.util.WebErrors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 日志控制器.
 * User: Cosmo<cosmo.wang@ningmenghai.com>
 * Date: 13-3-8
 * Time: 下午2:10
 */
@Controller
public class OSSLogController {

    private static Log log = Logs.get();

    @Autowired
    private OSSLogSvc ossLogSvc;

    @RequestMapping("/log/v_list_operating.jhtml")
    public String listOperating(String queryUsername, String queryTitle, Integer pageNo, HttpServletRequest request,
            ModelMap model) {
        PageList<OSSLog> logPageList = ossLogSvc
                .getPageListByQuery(OSSLog.TypeEnum.OPERATING.getCode(), queryUsername, queryTitle,
                        RequestUtil.cpn(pageNo), CookieUtil.getPageSize(request));
        model.addAttribute("pagination", logPageList);
        model.addAttribute("pageNo", logPageList.getPage());
        model.addAttribute("queryUsername", queryUsername);
        model.addAttribute("queryTitle", queryTitle);
        return "log/list_operating";
    }

    @RequestMapping("/log/v_list_login_success.jhtml")
    public String listLoginSuccess(String queryUsername, String queryTitle, Integer pageNo, HttpServletRequest request,
            ModelMap model) {
        PageList<OSSLog> logPageList = ossLogSvc
                .getPageListByQuery(OSSLog.TypeEnum.LOGIN_SUCCESS.getCode(), queryUsername, queryTitle,
                        RequestUtil.cpn(pageNo), CookieUtil.getPageSize(request));
        model.addAttribute("pagination", logPageList);
        model.addAttribute("pageNo", logPageList.getPage());
        model.addAttribute("queryUsername", queryUsername);
        model.addAttribute("queryTitle", queryTitle);
        return "log/list_login_success";
    }

    @RequestMapping("/log/v_list_login_failure.jhtml")
    public String listLoginFailure(String queryTitle, Integer pageNo, HttpServletRequest request, ModelMap model) {
        PageList<OSSLog> logPageList = ossLogSvc
                .getPageListByQuery(OSSLog.TypeEnum.LOGIN_FAILURE.getCode(), null, queryTitle, RequestUtil.cpn(pageNo),
                        CookieUtil.getPageSize(request));
        model.addAttribute("pagination", logPageList);
        model.addAttribute("pageNo", logPageList.getPage());
        model.addAttribute("queryTitle", queryTitle);
        return "log/list_login_failure";
    }

    @RequestMapping("/log/o_remove_operating.jhtml")
    public String removeOperating(String queryUsername, String queryTitle, Integer[] ids, Integer pageNo,
            HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateRemove(ids);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        Integer[] logIds = ossLogSvc.removeByLogIds(ids);
        for (Integer logId : logIds) {
            log.infof("delete OSSLog id=%d", logId);
        }
        return listOperating(queryUsername, queryTitle, pageNo, request, model);
    }

    @RequestMapping("/log/o_remove_operating_batch.jhtml")
    public String removeOperatingBatch(Integer days, HttpServletRequest request, ModelMap model) {
        if (null == days) {
            days = 0;
        }
        ossLogSvc.removeBatch(OSSLog.TypeEnum.OPERATING.getCode(), days);
        model.addAttribute("message", "批量删除成功!");
        return listOperating(null, null, 1, request, model);
    }

    @RequestMapping("/log/o_remove_login_success.jhtml")
    public String removeLoginSuccess(String queryUsername, String queryTitle, Integer[] ids, Integer pageNo,
            HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateRemove(ids);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        Integer[] logIds = ossLogSvc.removeByLogIds(ids);
        for (Integer logId : logIds) {
            log.infof("delete OSSLog id=%d", logId);
        }
        return listLoginSuccess(queryUsername, queryTitle, pageNo, request, model);
    }

    @RequestMapping("/log/o_remove_login_success_batch.jhtml")
    public String removeLoginSuccessBatch(Integer days, HttpServletRequest request, ModelMap model) {
        if (null == days) {
            days = 0;
        }
        ossLogSvc.removeBatch(OSSLog.TypeEnum.LOGIN_SUCCESS.getCode(), days);
        model.addAttribute("message", "批量删除成功!");
        return listLoginSuccess(null, null, 1, request, model);
    }

    @RequestMapping("/log/o_remove_login_failure.jhtml")
    public String removeLoginFailure(String queryTitle, Integer[] ids, Integer pageNo, HttpServletRequest request,
            ModelMap model) {
        WebErrors errors = validateRemove(ids);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        Integer[] logIds = ossLogSvc.removeByLogIds(ids);
        for (Integer logId : logIds) {
            log.infof("delete OSSLog id=%d", logId);
        }
        return listLoginFailure(queryTitle, pageNo, request, model);
    }

    @RequestMapping("/log/o_remove_login_failure_batch.jhtml")
    public String removeLoginFailureBatch(Integer days, HttpServletRequest request, ModelMap model) {
        if (null == days) {
            days = 0;
        }
        ossLogSvc.removeBatch(OSSLog.TypeEnum.LOGIN_FAILURE.getCode(), days);
        model.addAttribute("message", "批量删除成功!");
        return listLoginFailure(null, 1, request, model);
    }

    /**
     * 校验删除Ids
     *
     * @param ids ID数组
     * @return boolean
     */
    private WebErrors validateRemove(Integer[] ids) {
        WebErrors errors = WebErrors.create();
        errors.ifEmpty(ids, "ids");
        for (Integer id : ids) {
            vldExist(id, errors);
        }
        return errors;
    }

    /**
     * 校验是否存在
     *
     * @param id     用户ID
     * @param errors 错误
     * @return boolean
     */
    private boolean vldExist(Integer id, WebErrors errors) {
        if (errors.ifNull(id, "id")) {
            return true;
        }
        OSSLog log = ossLogSvc.getByLogId(id);
        return errors.ifNotExist(log, OSSLog.class, id);
    }
}
