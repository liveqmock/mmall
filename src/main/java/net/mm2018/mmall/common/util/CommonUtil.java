/**
 * CommonUtil.java
 *
 * Copyright 2013 lemon sea, Inc. All Rights Reserved.
 */
package net.mm2018.mmall.common.util;

import com.google.common.collect.Lists;
import net.mm2018.mmall.common.config.Constants;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.List;

/**
 * Common工具类.
 * User: Cosmo<cosmo.wang@ningmenghai.com>
 * Date: 13-3-8
 * Time: 下午7:43
 */
public final class CommonUtil {

    /**
     * @param sourceUri 原路径
     * @return 获取网络地址
     */
    public static String getResourceUrl(String sourceUri) {
        if (null == sourceUri) {
            return "";
        }
        return sourceUri.replace(Constants.getResourceUriPrefix(), Constants.getResourceUrlPrefix());
    }

    /**
     * @param sourceUrl 原网络地址
     * @return 获取路径
     */
    public static String getResourceUri(String sourceUrl) {
        if (null == sourceUrl) {
            return "";
        }
        return sourceUrl.replace(Constants.getResourceUrlPrefix(), Constants.getResourceUriPrefix());
    }

    private CommonUtil() {
    }

    /**
     * @param ids id字符串
     * @return 转换ID字符串为int列表
     */
    public static List<Integer> convertIdString2IntList(String ids) {
        if (StringUtils.isBlank(ids)) {
            return null;
        }
        String[] tempIds = StringUtils.split(ids, ',');
        if (null == tempIds || tempIds.length <= 0) {
            return null;
        }
        List<Integer> result = Lists.newArrayList();
        Integer id;
        for (String tempId : tempIds) {
            id = NumberUtils.toInt(tempId);
            if (!result.contains(id)) {
                result.add(id);
            }
        }
        return result;
    }
}
