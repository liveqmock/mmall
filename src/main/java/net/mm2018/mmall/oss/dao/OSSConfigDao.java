/**
 * OSSConfigDao.java
 *
 * Copyright 2012 lemon sea, Inc. All Rights Reserved.
 */
package net.mm2018.mmall.oss.dao;

import net.mm2018.mmall.common.mybatis.MyBatisDao;
import net.mm2018.mmall.oss.domain.OSSConfig;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 后台配置数据操作接口.
 * User: Cosmo<cosmo.wang@kaixinai.com>
 * Date: 12-3-23
 * Time: 下午5:18
 */
@MyBatisDao
@Repository
public interface OSSConfigDao {
    /** @return 获取所有后台配置 */
    List<OSSConfig> findAll();

    /**
     * @param key key
     * @return 根据key获取配置
     */
    OSSConfig findByKey(String key);

    /**
     * @param cfgKey config key
     * @return 根据key查找value
     */
    String findValueByKey(String cfgKey);

    /**
     * 保存配置
     *
     * @param config 配置
     */
    void save(OSSConfig config);

    /**
     * 更新配置
     *
     * @param config 配置
     */
    void update(OSSConfig config);
}
