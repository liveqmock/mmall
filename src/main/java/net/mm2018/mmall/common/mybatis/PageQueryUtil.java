/**
 * PageQueryUtil.java
 *
 * Copyright 2013 lemon sea, Inc. All Rights Reserved.
 */
package net.mm2018.mmall.common.mybatis;

import cn.kxai.common.page.Paginator;
import com.google.common.collect.Maps;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.util.ReflectionUtils;

import java.util.Map;

/**
 * Mybatis分页查询工具类.
 * User: Cosmo<cosmo.wang@ningmenghai.com>
 * Date: 13-3-5
 * Time: 下午2:32
 */
public final class PageQueryUtil {

    /**
     * @param parameter 参数
     * @return 附加查询参数
     */
    @SuppressWarnings({"unchecked"})
    public static Map attachPageQueryVariable(Object parameter, int pageNo, int rowsPerPage, int totalResult) {
        Paginator paginator = new Paginator(pageNo, rowsPerPage, totalResult);
        return attachPageQueryVariable(parameter, paginator.getOffset(), paginator.getLimit());
    }

    /**
     * @param parameter 参数
     * @param paginator 分页器
     * @return 附加查询参数
     */
    @SuppressWarnings({"unchecked"})
    public static Map attachPageQueryVariable(Object parameter, Paginator paginator) {
        return attachPageQueryVariable(parameter, paginator.getOffset(), paginator.getLimit());
    }

    /**
     * @param parameter 参数
     * @param offset    偏移量
     * @param limit     数量
     * @return 附加查询参数
     */
    @SuppressWarnings({"unchecked"})
    public static Map attachPageQueryVariable(Object parameter, int offset, int limit) {
        Map map = toParameterMap(parameter);
        map.put("offset", offset);
        map.put("limit", limit);
        return map;
    }

    /**
     * @param parameter 参数
     * @return 转换参数到map
     */
    @SuppressWarnings("unchecked")
    private static Map toParameterMap(Object parameter) {
        if (null == parameter) {
            return Maps.newHashMap();
        }
        if (parameter instanceof Map) {
            return (Map) parameter;
        } else {
            try {
                return PropertyUtils.describe(parameter);
            } catch (Exception e) {
                ReflectionUtils.handleReflectionException(e);
                return null;
            }
        }
    }

    private PageQueryUtil() {
    }
}
