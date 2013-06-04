/**
 * OSSWelcomeController.java
 *
 * Copyright 2012 lemon sea, Inc. All Rights Reserved.
 */
package net.mm2018.mmall.oss.web.controller;

import net.mm2018.mmall.oss.domain.OSSUser;
import net.mm2018.mmall.oss.util.OSSUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Properties;

/**
 * oss欢迎界面.
 * User: Cosmo<cosmo.wang@kaixinai.com>
 * Date: 12-3-25
 * Time: 上午10:44
 */
@Controller
public class OSSWelcomeController {

    @RequestMapping("index.jhtml")
    public String index() {
        return "index";
    }

    @RequestMapping("top.jhtml")
    public String top(HttpServletRequest request, ModelMap modelMap) {
        OSSUser user = OSSUtil.getUser(request);
        modelMap.addAttribute("user", user);
        return "top";
    }

    @RequestMapping("main.jhtml")
    public String main() {
        return "main";
    }

    @RequestMapping("left.jhtml")
    public String left() {
        return "left";
    }

    @RequestMapping("right.jhtml")
    public String right(HttpServletRequest request, ModelMap modelMap) {
        OSSUser user = OSSUtil.getUser(request);
        Properties properties = System.getProperties();
        Runtime runtime = Runtime.getRuntime();
        long freeMemory = runtime.freeMemory();
        long totalMemory = runtime.totalMemory();
        long usedMemory = totalMemory - freeMemory;
        long maxMemory = runtime.maxMemory();
        long usableMemory = maxMemory - totalMemory + freeMemory;
        modelMap.addAttribute("props", properties);
        modelMap.addAttribute("freeMemory", freeMemory);
        modelMap.addAttribute("totalMemory", totalMemory);
        modelMap.addAttribute("usedMemory", usedMemory);
        modelMap.addAttribute("maxMemory", maxMemory);
        modelMap.addAttribute("usableMemory", usableMemory);
        modelMap.addAttribute("user", user);
        return "right";
    }
}
