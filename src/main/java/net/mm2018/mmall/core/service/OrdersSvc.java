/**
 * OrdersSvc.java
 *
 * Copyright 2013 lemon sea, Inc. All Rights Reserved.
 */
package net.mm2018.mmall.core.service;

import net.mm2018.mmall.core.dao.OrdersDao;
import net.mm2018.mmall.core.domain.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Orders create(Integer itemId, String itemName, Integer price, String contact, String phone, String address, String remark) {
        Orders bean = new Orders();
        bean.setItemId(itemId);
        bean.setItemName(itemName);
        bean.setPrice(price);
        bean.setContact(contact);
        bean.setPhone(phone);
        bean.setAddress(address);
        bean.setRemark(remark);
        ordersDao.save(bean);
        return bean;
    }
}
