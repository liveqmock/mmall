/**
 * MobileColorController.java
 *
 * Copyright 2013 lemon sea, Inc. All Rights Reserved.
 */
package net.mm2018.mmall.mobile.web.controller.color;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by IntelliJ IDEA.
 * User: Cosmo<cosmo.wang@ningmenghai.com>
 * Date: 13-6-4
 * Time: 下午4:11
 */
@Controller
public class MobileColorController {

    @RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
    public String info(@PathVariable("id") Integer id, ModelMap model) {
        model.addAttribute("id", id);
        return "info_" + id;
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable("id") Integer id, ModelMap model) {
        model.addAttribute("id", id);
        return "detail_" + id;
    }
}
