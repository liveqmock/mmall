/**
 * OrdersSvc.java
 *
 * Copyright 2013 lemon sea, Inc. All Rights Reserved.
 */
package net.mm2018.mmall.core.service;

import cn.kxai.common.page.PageList;
import cn.kxai.common.page.Paginator;
import com.google.common.collect.Maps;
import net.mm2018.mmall.common.mybatis.PageQueryUtil;
import net.mm2018.mmall.core.dao.OrdersDao;
import net.mm2018.mmall.core.domain.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 订单Service.
 * User: Cosmo<cosmo.wang@ningmenghai.com>
 * Date: 13-6-7
 * Time: 下午8:16
 */
@Service
@Transactional
public class OrdersSvc {

    @Autowired
    private OrdersDao ordersDao;

    /**
     * 创建订单
     *
     * @param itemId   商品ID
     * @param itemName 商品名
     * @param contact  联系人
     * @param phone    联系电话
     * @param address  地址
     * @param remark   备注
     */
    public Orders create(Integer itemId, String itemName, Integer price, String contact, String phone, String address,
            String remark) {
        Orders bean = new Orders();
        bean.setItemId(itemId);
        bean.setItemName(itemName);
        bean.setPrice(price);
        bean.setContact(contact);
        bean.setPhone(phone);
        bean.setAddress(address);
        bean.setRemark(remark);
        bean.setStatus(0);
        ordersDao.save(bean);
        return bean;
    }

    /**
     * @param queryContact 联系人
     * @param queryPhone   联系电话
     * @param pageNo       页码
     * @param pageSize     大小
     * @return 根据条件获取订单列表
     */
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public PageList<Orders> getPageListByQuery(String queryContact, String queryPhone, Integer queryStatus,
            String queryStartDate, String queryEndDate, int pageNo, int pageSize) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("queryContact", queryContact);
        params.put("queryPhone", queryPhone);
        params.put("queryStatus", queryStatus);
        params.put("queryStartDate", queryStartDate);
        params.put("queryEndDate", queryEndDate);
        int totalResult = ordersDao.countListByQuery(params);
        Paginator paginator = new Paginator(pageNo, pageSize, totalResult);
        PageList<Orders> pageList = new PageList<Orders>(paginator);
        if (totalResult <= 0) {
            return pageList;
        }
        params = PageQueryUtil.attachPageQueryVariable(params, paginator);
        List<Orders> list = ordersDao.findListByQuery(params);
        pageList.setList(list);
        return pageList;
    }

    /**
     * @param orderId 订单ID
     * @return 根据订单ID获取订单
     */
    @Transactional(readOnly = true)
    public Orders getByOrderId(Integer orderId) {
        return ordersDao.findByOrderId(orderId);
    }

    /**
     * 修改订单状态
     *
     * @param orderId 订单ID
     * @param status  状态
     */
    public void modifyStatus(Integer orderId, Integer status) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("orderId", orderId);
        params.put("status", status);
        ordersDao.updateStatus(params);
    }

    /**
     * 修改快递单号
     *
     * @param ids      订单ID
     * @param expresses 快递单号
     */
    public void modifyExpress(Integer[] ids, String[] expresses) {
        Map<String, Object> params;
        for (int i = 0, n = ids.length; i < n; i++) {
            params = Maps.newHashMap();
            params.put("orderId", ids[i]);
            params.put("express", expresses[i]);
            ordersDao.updateExpress(params);
        }
    }
}
