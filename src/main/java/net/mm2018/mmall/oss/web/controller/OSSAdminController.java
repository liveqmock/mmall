/**
 * OSSAdminController.java
 *
 * Copyright 2012 lemon sea, Inc. All Rights Reserved.
 */
package net.mm2018.mmall.oss.web.controller;

import cn.kxai.common.log.Log;
import cn.kxai.common.log.Logs;
import cn.kxai.common.page.PageList;
import cn.kxai.common.web.util.CookieUtil;
import cn.kxai.common.web.util.RequestUtil;
import cn.kxai.common.web.util.ResponseUtil;
import net.mm2018.mmall.oss.domain.OSSRole;
import net.mm2018.mmall.oss.domain.OSSUser;
import net.mm2018.mmall.oss.domain.OSSUserExt;
import net.mm2018.mmall.oss.service.OSSLogSvc;
import net.mm2018.mmall.oss.service.OSSRoleSvc;
import net.mm2018.mmall.oss.service.OSSUserSvc;
import net.mm2018.mmall.oss.util.OSSUtil;
import net.mm2018.mmall.oss.util.WebErrors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 管理员控制.
 * User: Cosmo<cosmo.wang@kaixinai.com>
 * Date: 12-3-25
 * Time: 下午9:25
 */
@Controller
public class OSSAdminController {

    private static Log log = Logs.get();
    @Autowired
    private OSSUserSvc ossUserSvc;
    @Autowired
    private OSSRoleSvc ossRoleSvc;
    @Autowired
    private OSSLogSvc ossLogSvc;

    @RequestMapping("/admin/v_list.jhtml")
    public String list(String queryUsername, String queryEmail, Boolean queryDisabled, Integer pageNo,
            HttpServletRequest request, ModelMap model) {
        OSSUser currentUser = OSSUtil.getUser(request);
        PageList<OSSUser> pagination = ossUserSvc
                .getPageByQuery(queryUsername, queryEmail, queryDisabled, true, currentUser.getRank(),
                        RequestUtil.cpn(pageNo), CookieUtil.getPageSize(request));
        model.addAttribute("pagination", pagination);
        model.addAttribute("queryUsername", queryUsername);
        model.addAttribute("queryEmail", queryEmail);
        model.addAttribute("queryDisabled", queryDisabled);

        return "admin/list";
    }

    @RequestMapping("/admin/v_add.jhtml")
    public String add(HttpServletRequest request, ModelMap model) {
        OSSUser user = OSSUtil.getUser(request);
        List<OSSRole> roleList = ossRoleSvc.getAll();
        model.addAttribute("roleList", roleList);
        model.addAttribute("currRank", user.getRank());
        return "admin/add";
    }

    @RequestMapping("/admin/v_edit.jhtml")
    public String edit(Integer id, String queryUsername, String queryEmail, Boolean queryDisabled,
            HttpServletRequest request, ModelMap model) {
        OSSUser user = OSSUtil.getUser(request);
        WebErrors errors = validateEdit(id);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        OSSUser admin = ossUserSvc.getByUserId(id);
        List<OSSRole> roleList = ossRoleSvc.getAll();

        model.addAttribute("ossAdmin", admin);
        model.addAttribute("roleIds", admin.getRoleIds());
        model.addAttribute("roleList", roleList);
        model.addAttribute("currRank", user.getRank());

        model.addAttribute("queryUsername", queryUsername);
        model.addAttribute("queryEmail", queryEmail);
        model.addAttribute("queryDisabled", queryDisabled);
        return "admin/edit";
    }

    @RequestMapping("/admin/o_create.jhtml")
    public String create(OSSUser bean, OSSUserExt ext, String username, String email, String password,
            Boolean viewonlyAdmin, Boolean selfAdmin, Integer rank, Integer[] roleIds, HttpServletRequest request,
            ModelMap model) {
        String ip = RequestUtil.getIpAddress(request);
        bean = ossUserSvc.createAdmin(username, email, password, ip, viewonlyAdmin, selfAdmin, rank, roleIds, ext);
        model.addAttribute("message", "添加成功!");
        log.infof("create admin id = %d", bean.getUserId());
        ossLogSvc.operating(request, "添加管理员", "userId=" + bean.getUserId());
        return "redirect:v_list.jhtml";
    }

    @RequestMapping("/admin/o_modify.jhtml")
    public String modify(OSSUser bean, OSSUserExt ext, String password, Integer[] roleIds, String queryUsername,
            String queryEmail, Boolean queryDisabled, Integer pageNo, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateEdit(bean.getUserId());
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        bean = ossUserSvc.modifyAdmin(bean, ext, password, roleIds);
        model.addAttribute("message", "修改成功!");
        log.infof("modify admin id = %d", bean.getUserId());
        ossLogSvc.operating(request, "修改管理员", "userId=" + bean.getUserId());
        return list(queryUsername, queryEmail, queryDisabled, pageNo, request, model);
    }

    @RequestMapping("/admin/o_remove.jhtml")
    public String remove(Integer[] ids, String queryUsername, String queryEmail, Boolean queryDisabled, Integer pageNo,
            HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateRemove(ids);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        Integer[] results = ossUserSvc.removeAdminByIds(ids);
        for (int i = 0, n = results.length; i < n; i++) {
            if (results[i] > 0) {
                log.infof("remove admin success!id = %d", ids[i]);
                ossLogSvc.operating(request, "删除管理员", "userId=" + ids[i]);
            } else {
                log.infof("remove admin failed!id = %d", ids[i]);
            }
        }
        model.addAttribute("message", "删除成功!");
        return list(queryUsername, queryEmail, queryDisabled, pageNo, request, model);
    }

    @RequestMapping("/admin/v_check_username.jhtml")
    public void checkUsername(String username, HttpServletResponse response) {
        String pass;
        if (StringUtils.isBlank(username)) {
            pass = "false";
        } else {
            pass = (null == ossUserSvc.getByUsername(username)) ? "true" : "false";
        }
        ResponseUtil.renderJson(response, pass);
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
     * 校验编辑ID
     *
     * @param id ID
     * @return WebErrors
     */
    private WebErrors validateEdit(Integer id) {
        WebErrors errors = WebErrors.create();
        vldExist(id, errors);
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
        OSSUser user = ossUserSvc.getByUserId(id);
        return errors.ifNotExist(user, OSSUser.class, id);
    }
}
