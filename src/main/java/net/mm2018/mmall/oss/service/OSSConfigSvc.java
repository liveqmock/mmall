/**
 * OSSConfigSvcImpl.java
 *
 * Copyright 2012 lemon sea, Inc. All Rights Reserved.
 */
package net.mm2018.mmall.oss.service;

import net.mm2018.mmall.common.cache.ehcache.EhcacheService;
import net.mm2018.mmall.oss.dao.OSSConfigDao;
import net.mm2018.mmall.oss.domain.OSSConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台配置业务接口实现.
 * User: Cosmo<cosmo.wang@kaixinai.com>
 * Date: 12-3-24
 * Time: 下午1:59
 */
@Transactional
@Service
public class OSSConfigSvc {

    private static final String CACHE_KEY_ALL = "OSSConfig.All";

    @Autowired
    private OSSConfigDao ossConfigDao;
    @Autowired
    private EhcacheService ehcacheService;

    /**
     * 创建或修改
     *
     * @param map 配置map
     */
    public void createOrModify(Map<String, String> map) {
        if (null != map && map.size() > 0) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                createOrModify(entry.getKey(), entry.getValue());
            }
        }
    }

    @Transactional(readOnly = true)
    public OSSConfig.ConfigLogin getConfigLogin() {
        return OSSConfig.ConfigLogin.create(getAll());
    }

    /**
     * 创建或修改配置
     *
     * @param key   key
     * @param value value
     */
    private void createOrModify(String key, String value) {
        OSSConfig config = ossConfigDao.findByKey(key);
        if (null != config) {
            config.setCfgValue(value);
            ossConfigDao.update(config);
        } else {
            config = new OSSConfig();
            config.setCfgKey(key);
            config.setCfgValue(value);
            ossConfigDao.save(config);
        }
        ehcacheService.remove(CACHE_KEY_ALL);
    }

    /** @return 获取所有配置 */
    @SuppressWarnings("unchecked")
    private Map<String, String> getAll() {
        Object object = ehcacheService.get(CACHE_KEY_ALL);
        if (null != object) {
            return (Map<String, String>) object;
        }
        List<OSSConfig> list = ossConfigDao.findAll();
        Map<String, String> map = new HashMap<String, String>(list.size());
        for (OSSConfig config : list) {
            map.put(config.getCfgKey(), config.getCfgValue());
        }
        ehcacheService.put(CACHE_KEY_ALL, map);
        return map;
    }

}
