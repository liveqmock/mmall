/**
 * OSSLogDao.java
 *
 * Copyright 2013 lemon sea, Inc. All Rights Reserved.
 */
package net.mm2018.mmall.oss.dao;

import net.mm2018.mmall.common.mybatis.MyBatisDao;
import net.mm2018.mmall.oss.domain.OSSLog;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 后台日志Dao.
 * User: Cosmo<cosmo.wang@ningmenghai.com>
 * Date: 13-3-8
 * Time: 上午11:35
 */
@MyBatisDao
@Repository
public interface OSSLogDao {

    /**
     * @param params 查询参数
     * @return 计算查询列表中对象数量
     */
    int countListByQuery(Map<String, Object> params);

    /**
     * @param params 删除条件参数
     * @return 批量删除
     */
    Integer deleteBatch(Map<String, Object> params);

    /**
     * @param logId 日志ID
     * @return 根据日志ID删除日志
     */
    Integer deleteByLogId(Integer logId);

    /**
     * @param logId 日志ID
     * @return 根据日志ID查找日志
     */
    OSSLog findByLogId(Integer logId);

    /**
     * @param params 查询条件
     * @return 根据条件查找日志列表
     */
    List<OSSLog> findListByQuery(Map<String, Object> params);

    /**
     * 保存日志
     *
     * @param bean 日志对象
     */
    void save(OSSLog bean);
}
