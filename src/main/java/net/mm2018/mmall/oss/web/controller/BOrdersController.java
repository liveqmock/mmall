/**
 * BOrdersController.java
 *
 * Copyright 2013 lemon sea, Inc. All Rights Reserved.
 */
package net.mm2018.mmall.oss.web.controller;

import cn.kxai.common.log.Log;
import cn.kxai.common.log.Logs;
import cn.kxai.common.page.PageList;
import cn.kxai.common.web.util.CookieUtil;
import cn.kxai.common.web.util.RequestUtil;
import net.mm2018.mmall.common.config.Constants;
import net.mm2018.mmall.core.domain.Orders;
import net.mm2018.mmall.core.service.OrdersSvc;
import net.mm2018.mmall.oss.service.OSSLogSvc;
import net.mm2018.mmall.oss.util.WebErrors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * orders管理.
 * User: Cosmo<cosmo.wang@ningmenghai.com>
 * Date: 13-6-14
 * Time: 上午10:29
 */
@Controller
public class BOrdersController {

    private static Log log = Logs.get();

    @Autowired
    private OrdersSvc ordersSvc;
    @Autowired
    private OSSLogSvc ossLogSvc;

    @RequestMapping("/orders/v_list.jhtml")
    public String list(String queryContact, String queryPhone, Integer queryStatus, String queryStartDate,
            String queryEndDate, Integer pageNo, HttpServletRequest request, ModelMap model) {
        log.infof("list.jhtml queryStatus = %s", String.valueOf(queryStatus));
        log.infof("list.jhtml queryStartDate = %s", queryStartDate);
        log.infof("list.jhtml queryEndDate = %s", queryEndDate);
        PageList<Orders> pageList = ordersSvc
                .getPageListByQuery(queryContact, queryPhone, queryStatus, queryStartDate, queryEndDate,
                        RequestUtil.cpn(pageNo), CookieUtil.getPageSize(request));
        model.addAttribute("pagination", pageList);
        model.addAttribute("queryContact", queryContact);
        model.addAttribute("queryPhone", queryPhone);
        model.addAttribute("queryStatus", queryStatus);
        model.addAttribute("queryStartDate", queryStartDate);
        model.addAttribute("queryEndDate", queryEndDate);
        model.addAttribute("pageNo", pageNo);
        return "orders/list";
    }

    @RequestMapping("/orders/o_modify_status.jhtml")
    public String modifyStatus(Integer orderId, Integer status, String queryContact, String queryPhone,
            Integer queryStatus, String queryStartDate, String queryEndDate, Integer pageNo, HttpServletRequest request,
            ModelMap model) {
        WebErrors errors = validateEdit(orderId);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        Orders orders = ordersSvc.getByOrderId(orderId);
        if (status <= orders.getStatus() && status != -1) {
            errors.addError("修改失败");
            return errors.showErrorPage(model);
        }
        ordersSvc.modifyStatus(orderId, status);
        model.addAttribute(Constants.OSS_MESSAGE, "修改成功!");
        log.infof("modify Status orderId = %d", orderId);
        ossLogSvc.operating(request, "修改订单状态", "orderId=" + orderId);
        return list(queryContact, queryPhone, queryStatus, queryStartDate, queryEndDate, pageNo, request, model);
    }

    @RequestMapping("/orders/o_modify_express.jhtml")
    public String modifyExpress(Integer[] id, String[] express, String queryContact, String queryPhone,
            Integer queryStatus, String queryStartDate, String queryEndDate, Integer pageNo, HttpServletRequest request,
            ModelMap model) {
        ordersSvc.modifyExpress(id, express);
        model.addAttribute(Constants.OSS_MESSAGE, "修改成功!");
        log.infof("modifyExpress orderId = " + Arrays.toString(id));
        ossLogSvc.operating(request, "修改快递单号", "id=" + Arrays.toString(id));
        return list(queryContact, queryPhone, queryStatus, queryStartDate, queryEndDate, pageNo, request, model);
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
        Orders bean = ordersSvc.getByOrderId(id);
        return errors.ifNotExist(bean, Orders.class, id);
    }
}
