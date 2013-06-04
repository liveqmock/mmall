/**
 * OSSPersonalController.java
 *
 * Copyright 2012 lemon sea, Inc. All Rights Reserved.
 */
package net.mm2018.mmall.oss.web.controller;

import cn.kxai.common.web.util.ResponseUtil;
import net.mm2018.mmall.oss.domain.OSSUser;
import net.mm2018.mmall.oss.domain.OSSUserExt;
import net.mm2018.mmall.oss.service.OSSLogSvc;
import net.mm2018.mmall.oss.service.OSSUserSvc;
import net.mm2018.mmall.oss.util.OSSUtil;
import net.mm2018.mmall.oss.util.WebErrors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 个人资料控制.
 * User: Cosmo<cosmo.wang@kaixinai.com>
 * Date: 12-3-25
 * Time: 下午5:51
 */
@Controller
public class OSSPersonalController {

    @Autowired
    private OSSUserSvc ossUserSvc;
    @Autowired
    private OSSLogSvc ossLogSvc;

    @RequestMapping("/personal/v_profile.jhtml")
    public String profileEdit(HttpServletRequest request, ModelMap model) {
        OSSUser user = OSSUtil.getUser(request);
        model.addAttribute("user", user);
        return "personal/profile";
    }

    @RequestMapping("/personal/o_profile.jhtml")
    public String profileModify(String origPwd, String newPwd, String email, String realName,
            HttpServletRequest request, ModelMap model) {
        OSSUser user = OSSUtil.getUser(request);
        WebErrors errors = validateProfileModify(user.getUserId(), origPwd, newPwd, email, realName);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        OSSUserExt ext = user.getOssUserExt();
        if (null == ext) {
            ext = new OSSUserExt();
        }
        ext.setRealName(realName);
        ossUserSvc.modifyUserExt(ext, user);
        ossUserSvc.modifyPwdEmail(user.getUserId(), newPwd, email);
        model.addAttribute("message", "操作成功");
        ossLogSvc.operating(request, "修改个人资料", "userId=" + user.getUserId());
        return profileEdit(request, model);
    }

    @RequestMapping("/personal/v_checkPwd.jhtml")
    public void checkPwd(String origPwd, HttpServletRequest request, HttpServletResponse response) {
        OSSUser user = OSSUtil.getUser(request);
        boolean pass = ossUserSvc.isValidPassword(user.getUserId(), origPwd);
        ResponseUtil.renderJson(response, pass ? "true" : "false");
    }

    /**
     * 验证资料修改
     *
     * @param userId   用户ID
     * @param origPwd  原密码
     * @param newPwd   新密码
     * @param email    电子邮箱
     * @param realName 真实姓名
     * @return WebErrors
     */
    private WebErrors validateProfileModify(Integer userId, String origPwd, String newPwd, String email,
            String realName) {
        WebErrors errors = WebErrors.create();
        if (errors.ifBlank(origPwd, "原密码", 32)) {
            return errors;
        }
        if (errors.ifMaxLength(newPwd, "新密码", 32)) {
            return errors;
        }
        if (errors.ifMaxLength(email, "电子邮箱", 100)) {
            return errors;
        }
        if (errors.ifMaxLength(realName, "真实姓名", 100)) {
            return errors;
        }
        if (!ossUserSvc.isValidPassword(userId, origPwd)) {
            errors.addError("原密码无效");
        }
        return errors;
    }
}
