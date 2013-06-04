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

    // 曲库管理
    @RequestMapping("/frame/music_main.jhtml")
    public String musicMain() {
        return "frame/music_main";
    }

    @RequestMapping("/frame/music_left.jhtml")
    public String musicLeft() {
        return "frame/music_left";
    }

    @RequestMapping("/frame/music_right.jhtml")
    public String musicRight() {
        return "frame/music_right";
    }

    // 分类管理
    @RequestMapping("/frame/category_main.jhtml")
    public String categoryMain() {
        return "frame/category_main";
    }

    // 故事管理
    @RequestMapping("/frame/story_main.jhtml")
    public String storyMain() {
        return "frame/story_main";
    }

    // 用户管理
    @RequestMapping("/frame/user_main.jhtml")
    public String userMain() {
        return "frame/user_main";
    }

    @RequestMapping("/frame/user_left.jhtml")
    public String userLeft() {
        return "frame/user_left";
    }

    @RequestMapping("/frame/user_right.jhtml")
    public String userRight() {
        return "frame/user_right";
    }

    // 辅助管理
    @RequestMapping("/frame/support_main.jhtml")
    public String supportMain() {
        return "frame/support_main";
    }

    @RequestMapping("/frame/support_left.jhtml")
    public String supportLeft() {
        return "frame/support_left";
    }

    @RequestMapping("/frame/support_right.jhtml")
    public String supportRight() {
        return "frame/support_right";
    }

    // 资源管理
    @RequestMapping("/frame/resource_main.jhtml")
    public String resourceMain() {
        return "frame/resource_main";
    }

}
