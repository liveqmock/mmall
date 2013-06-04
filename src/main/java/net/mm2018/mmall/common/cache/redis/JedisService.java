/**
 * JedisService.java
 *
 * Copyright 2013 lemon sea, Inc. All Rights Reserved.
 */
package net.mm2018.mmall.common.cache.redis;

import cn.kxai.common.lang.mapper.JsonMapper;
import cn.kxai.common.log.Log;
import cn.kxai.common.log.Logs;
import net.mm2018.mmall.common.config.Constants;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Set;

/**
 * redis服务.
 * User: Cosmo<cosmo.wang@ningmenghai.com>
 * Date: 13-3-20
 * Time: 上午10:19
 */
@Scope("singleton")
@Component
public class JedisService {

    private static Log log = Logs.get();

    private JedisPool pool;

    @PostConstruct
    public void init() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxActive(Integer.valueOf(Constants.get("redis.pool.maxActive")));
        poolConfig.setMaxIdle(Integer.valueOf(Constants.get("redis.pool.maxIdle")));
        poolConfig.setMaxWait(Long.valueOf(Constants.get("redis.pool.maxWait")));
        poolConfig.setTestOnBorrow(Boolean.valueOf(Constants.get("redis.pool.testOnBorrow")));
        poolConfig.setTestOnReturn(Boolean.valueOf(Constants.get("redis.pool.testOnReturn")));
        pool = new JedisPool(poolConfig, Constants.get("redis.host"), Integer.valueOf(Constants.get("redis.port")),
                Integer.valueOf(Constants.get("redis.timeout")));
    }

    @PreDestroy
    public void destroy() {
        pool.destroy();
    }

    /**
     * 设置对象
     *
     * @param key   缓存key
     * @param value 缓存对象
     */
    public void set(String key, Object value, int seconds) {
        try {
            String json = JsonMapper.nonDefaultBinder().toJson(value);
            set(key, json, seconds);
        } catch (Exception e) {
            log.error("set failed! key = " + key + ", value = " + value.toString(), e);
        }
    }

    /**
     * 设置对象
     *
     * @param key   缓存key
     * @param value 缓存对象
     */
    public void set(String key, String value, int seconds) {
        Jedis jedis = pool.getResource();
        try {
            jedis.set(key, value);
            if (seconds > 0) {
                jedis.expire(key, seconds);
            }
        } finally {
            pool.returnResource(jedis);
        }
    }

    /**
     * @param key   缓存key
     * @param clazz 对象类型
     * @return 根据缓存key获取缓存对象
     */
    public <T> T get(String key, Class<T> clazz) {
        try {
            String value = get(key);
            return JsonMapper.nonDefaultBinder().fromJson(value, clazz);
        } catch (Exception e) {
            log.error("get failed! key = " + key + ", clazz = " + clazz.toString(), e);
        }
        return null;
    }

    /**
     * @param key 缓存key
     * @return 根据缓存key获取缓存的value
     */
    public String get(String key) {
        Jedis jedis = pool.getResource();
        try {
            return jedis.get(key);
        } finally {
            pool.returnResource(jedis);
        }
    }

    /**
     * 删除缓存keys
     *
     * @param keys 缓存key数组
     */
    public void del(String... keys) {
        Jedis jedis = pool.getResource();
        try {
            jedis.del(keys);
        } finally {
            pool.returnResource(jedis);
        }
    }

    /**
     * 批量删除缓存Keys
     *
     * @param pattern 匹配
     */
    public void batchDel(String pattern) {
        Jedis jedis = pool.getResource();
        try {
            Set<String> keys = jedis.keys(pattern);
            if (null != keys && keys.size() > 0) {
                jedis.del((String[]) keys.toArray());
            }
        } finally {
            pool.returnResource(jedis);
        }
    }
}
