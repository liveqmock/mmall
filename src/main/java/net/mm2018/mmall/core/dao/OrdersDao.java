/**
 * OrdersDao.java
 *
 * Copyright 2013 lemon sea, Inc. All Rights Reserved.
 */
package net.mm2018.mmall.core.dao;

import net.mm2018.mmall.common.mybatis.MyBatisDao;
import net.mm2018.mmall.core.domain.Orders;
import org.springframework.stereotype.Repository;

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
}
