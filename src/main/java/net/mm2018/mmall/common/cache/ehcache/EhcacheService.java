/**
 * Ehcache.java
 *
 * Copyright 2013 lemon sea, Inc. All Rights Reserved.
 */
package net.mm2018.mmall.common.cache.ehcache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * ehcache服务.
 * User: Cosmo<cosmo.wang@ningmenghai.com>
 * Date: 13-3-6
 * Time: 上午10:03
 */
@Scope("singleton")
@Component
public class EhcacheService {

    private static final String CACHE_NAME = "mmallCache";

    @Autowired
    private CacheManager cacheManager;

    private Cache cache;

    @PostConstruct
    public void init() {
        cache = cacheManager.getCache(CACHE_NAME);
    }

    @PreDestroy
    public void destroy() {
        cache = null;
    }

    /**
     * @param key 缓存key
     * @return 获取缓存的对象
     */
    public Object get(String key) {
        Element element = cache.get(key);
        if (null != element) {
            return element.getObjectValue();
        }
        return null;
    }

    /**
     * 对象放入缓存中
     *
     * @param key   缓存key
     * @param value 缓存对象
     */
    public void put(String key, Object value) {
        Element element = new Element(key, value);
        cache.put(element);
    }

    /**
     * 删除缓存对象
     *
     * @param key 缓存key
     */
    public void remove(String key) {
        cache.remove(key);
    }
}
