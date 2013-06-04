/**
 * OSSConfigController.java
 *
 * Copyright 2013 lemon sea, Inc. All Rights Reserved.
 */
package net.mm2018.mmall.oss.web.controller;

import cn.kxai.common.log.Log;
import cn.kxai.common.log.Logs;
import net.mm2018.mmall.oss.domain.OSSConfig;
import net.mm2018.mmall.oss.service.OSSConfigSvc;
import net.mm2018.mmall.oss.service.OSSLogSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 配置控制.
 * User: Cosmo<cosmo.wang@ningmenghai.com>
 * Date: 13-3-7
 * Time: 下午6:07
 */
@Controller
public class OSSConfigController {

    private static Log log = Logs.get();

    @Autowired
    private OSSConfigSvc configSvc;
    @Autowired
    private OSSLogSvc ossLogSvc;

    @RequestMapping("/config/v_login_edit.jhtml")
    public String loginEdit(ModelMap model) {
        model.addAttribute("configLogin", configSvc.getConfigLogin());
        return "config/login_edit";
    }

    @RequestMapping("config/o_login_modify.jhtml")
    public String loginModify(HttpServletRequest request, OSSConfig.ConfigLogin configLogin, ModelMap model) {
        configSvc.createOrModify(configLogin.getAttr());
        model.addAttribute("message", "修改成功!");
        log.info("update login config.");
        ossLogSvc.operating(request, "修改登录设置", null);
        return loginEdit(model);
    }
}
