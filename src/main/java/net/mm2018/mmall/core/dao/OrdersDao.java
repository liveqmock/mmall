/**
 * OrdersDao.java
 *
 * Copyright 2013 lemon sea, Inc. All Rights Reserved.
 */
package net.mm2018.mmall.core.dao;

import net.mm2018.mmall.common.mybatis.MyBatisDao;
import net.mm2018.mmall.core.domain.Orders;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 订单Dao.
 * User: Cosmo<cosmo.wang@ningmenghai.com>
 * Date: 13-6-7
 * Time: 下午8:17
 */
@MyBatisDao
@Repository
public interface OrdersDao {

    /**
     * 保存
     *
     * @param bean 订单信息
     */
    void save(Orders bean);

    /**
     * @param params 联系人 + 电话
     * @return 计算列表对象数量
     */
    int countListByQuery(Map<String, Object> params);

    /**
     * @param params 联系人 + 电话 + 分页参数
     * @return 根据条件查找音乐列表
     */
    List<Orders> findListByQuery(Map<String, Object> params);

    /**
     * @param orderId 订单ID
     * @return 根据订单ID查找
     */
    Orders findByOrderId(Integer orderId);

    /**
     * 更新订单状态
     *
     * @param params 订单ID + 状态
     */
    void updateStatus(Map<String, Object> params);
}
