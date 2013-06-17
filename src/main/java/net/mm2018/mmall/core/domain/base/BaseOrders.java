/**
 * BaseOrders.java
 *
 * Copyright 2013 lemon sea, Inc. All Rights Reserved.
 */
package net.mm2018.mmall.core.domain.base;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单.
 * User: Cosmo<cosmo.wang@ningmenghai.com>
 * Date: 13-6-7
 * Time: 下午8:06
 */
@SuppressWarnings("serial")
public class BaseOrders
        implements Serializable {

    // 订单ID
    private Integer orderId;
    // 商品ID
    private Integer itemId;
    // 商品名
    private String itemName;
    // 价格
    private Integer price;
    // 联系人
    private String contact;
    // 用户电话
    private String phone;
    // 用户地址
    private String address;
    // 用户备注
    private String remark;
    // 快递单号
    private String express;
    // 状态 -1删除 0下单 1回访 2发货 3签收 4完成
    private Integer status;
    // 创建时间
    private Date createTime;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getExpress() {
        return express;
    }

    public void setExpress(String express) {
        this.express = express;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BaseOrders{");
        sb.append("orderId=").append(orderId);
        sb.append(", itemId=").append(itemId);
        sb.append(", itemName='").append(itemName).append('\'');
        sb.append(", price=").append(price);
        sb.append(", contact='").append(contact).append('\'');
        sb.append(", phone='").append(phone).append('\'');
        sb.append(", address='").append(address).append('\'');
        sb.append(", remark='").append(remark).append('\'');
        sb.append(", express='").append(express).append('\'');
        sb.append(", status=").append(status);
        sb.append(", createTime=").append(createTime);
        sb.append('}');
        return sb.toString();
    }
}
