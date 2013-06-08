/**
 * MobileSimpleController.java
 *
 * Copyright 2013 lemon sea, Inc. All Rights Reserved.
 */
package net.mm2018.mmall.mobile.web.controller.simple;

import cn.kxai.common.web.util.RequestUtil;
import net.mm2018.mmall.core.domain.Orders;
import net.mm2018.mmall.core.service.OrdersSvc;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by IntelliJ IDEA.
 * User: Cosmo<cosmo.wang@ningmenghai.com>
 * Date: 13-6-4
 * Time: 下午4:12
 */
@Controller
public class MobileSimpleController {

    @Autowired
    private OrdersSvc ordersSvc;

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

    @RequestMapping(value = "/order/{id}", method = RequestMethod.GET)
    public String order(@PathVariable("id") Integer id, ModelMap model) {
        model.addAttribute("id", id);
        return "order_" + id;
    }

    @RequestMapping(value = "/order/{id}", method = RequestMethod.POST)
    public String orderSubmit(@PathVariable("id") Integer id, HttpServletRequest request, ModelMap model) {
        String contact = RequestUtil.getParameterForString(request, "contact", null);
        if (StringUtils.isBlank(contact)) {
            model.addAttribute("error", "请输入联系人!");
            return "order_" + id;
        }
        String phone = RequestUtil.getParameterForString(request, "phone", null);
        if (StringUtils.isBlank(phone)) {
            model.addAttribute("error", "请输入联系电话!");
            return "order_" + id;
        }
        String address = RequestUtil.getParameterForString(request, "address", null);
        String remark = RequestUtil.getParameterForString(request, "remark", null);
        String itemName = "未知";
        Integer price = 0;
        if (id == 1) {
            itemName = "IPhone4/4S专用高清微型投影仪";
            price = 599;
        } else if (id == 2) {
            itemName = "IPHONE4/4S 真皮手机套";
            price = 268;
        } else if (id == 3) {
            itemName = "IPHONE5 真皮手机套";
            price = 328;
        } else if (id == 4) {
            itemName = "IPAD MINI皮套";
            price = 128;
        } else if (id == 5) {
            itemName = "IPHONE车载充电套组";
            price = 118;
        } else if (id == 6) {
            itemName = "手机健康仪";
            price = 999;
        }
        Orders orders = ordersSvc.create(id, itemName, price, contact, phone, address, remark);
        model.addAttribute("orders", orders);
        model.addAttribute("id", id);
        return "order_success";
    }
}
