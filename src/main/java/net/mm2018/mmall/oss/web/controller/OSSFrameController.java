/**
 * OSSFrameController.java
 *
 * Copyright 2012 lemon sea, Inc. All Rights Reserved.
 */
package net.mm2018.mmall.oss.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 框架控制.
 * User: Cosmo<cosmo.wang@kaixinai.com>
 * Date: 12-3-25
 * Time: 下午5:40
 */
@Controller
public class OSSFrameController {

    // 系统管理
    @RequestMapping("/frame/system_main.jhtml")
    public String systemMain() {
        return "frame/system_main";
    }

    @RequestMapping("/frame/system_left.jhtml")
    public String systemLeft() {
        return "frame/system_left";
    }

    @RequestMapping("/frame/system_right.jhtml")
    public String systemRight() {
        return "frame/system_right";
    }

    // 订单管理
    @RequestMapping("/frame/orders_main.jhtml")
    public String ordersMain() {
        return "frame/orders_main";
    }

    @RequestMapping("/frame/orders_left.jhtml")
    public String ordersLeft() {
        return "frame/orders_left";
    }

    @RequestMapping("/frame/orders_right.jhtml")
    public String ordersRight() {
        return "frame/orders_right";
    }

}
