/**
 * WebErrors.java
 *
 * Copyright 2012 lemon sea, Inc. All Rights Reserved.
 */
package net.mm2018.mmall.oss.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * WEB错误信息.
 * User: Cosmo<cosmo.wang@kaixinai.com>
 * Date: 12-3-25
 * Time: 上午10:08
 */
public final class WebErrors {

    /** 默认错误页面 */
    public static final String ERROR_PAGE = "common/error_message";
    /** 默认错误信息属性名称 */
    public static final String ERROR_ATTR_NAME = "errors";

    /** email正则表达式 */
    public static final Pattern EMAIL_PATTERN = Pattern.compile("^\\w+(\\.\\w+)*@\\w+(\\.\\w+)+$");
    /** username正则表达式 */
    public static final Pattern USERNAME_PATTERN = Pattern.compile("^[0-9a-zA-Z\\u4e00-\\u9fa5\\.\\-@_]+$");

    private List<String> errors;

    /** @return 创建WebErrors */
    public static WebErrors create() {
        return new WebErrors();
    }

    /**
     * 添加错误字符串
     *
     * @param error 错误字符串
     */
    public void addError(String error) {
        getErrors().add(error);
    }

    /** @return 是否存在错误 */
    public boolean hasErrors() {
        return null != errors && errors.size() > 0;
    }

    /** @return 错误数量 */
    public int getCount() {
        return null == errors ? 0 : errors.size();
    }

    /** @return 错误列表 */
    public List<String> getErrors() {
        if (null == errors) {
            errors = new ArrayList<String>();
        }
        return errors;
    }

    /**
     * 将错误信息保存至ModelMap,并返回错误页面.
     *
     * @param model ModelMap
     * @return 错误页面地址
     * @see org.springframework.ui.ModelMap
     */
    public String showErrorPage(ModelMap model) {
        putToModelMap(model);
        return getErrorPage();
    }

    /**
     * 将错误信息保存至ModelMap
     *
     * @param model ModelMap
     */
    public void putToModelMap(Map<String, Object> model) {
        Assert.notNull(model);
        if (!hasErrors()) {
            throw new IllegalStateException("no errors found!");
        }
        model.put(getErrorAttrName(), getErrors());
    }

    public boolean ifNull(Object o, String field) {
        if (null == o) {
            StringBuilder sb = new StringBuilder(field);
            sb.append("为必填项");
            addError(sb.toString());
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param o     object,可以为单个对象或数组,集合
     * @param field 属性名
     * @return 判断对象是否为空
     */
    public boolean ifEmpty(Object[] o, String field) {
        if (null == o || o.length <= 0) {
            StringBuilder sb = new StringBuilder(field);
            sb.append("为必填项");
            addError(sb.toString());
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param s         字符串
     * @param field     属性
     * @param maxLength 最大长度
     * @return 判断字符串是否为空白字符, 并是否超出最大长度
     */
    public boolean ifBlank(String s, String field, int maxLength) {
        if (StringUtils.isBlank(s)) {
            StringBuilder sb = new StringBuilder(field);
            sb.append("为必填项");
            addError(sb.toString());
            return true;
        }
        return ifMaxLength(s, field, maxLength);
    }

    /**
     * @param s         字符串
     * @param field     属性
     * @param maxLength 最大长度
     * @return 判断字符串是否超出最大长度
     */
    public boolean ifMaxLength(String s, String field, int maxLength) {
        if (null != s && s.length() > maxLength) {
            StringBuilder sb = new StringBuilder(field);
            sb.append("的长度不能大于").append(maxLength);
            addError(sb.toString());
            return true;
        }
        return false;
    }

    /**
     * @param s         字符串
     * @param field     属性
     * @param minLength 最小长度
     * @param maxLength 最大长度
     * @return 判断字符串是否在minLength和maxLength之间
     */
    public boolean ifOutOfLength(String s, String field, int minLength, int maxLength) {
        if (ifNull(s, field)) {
            return true;
        }
        int len = s.length();
        if (len < minLength || len > maxLength) {
            StringBuilder sb = new StringBuilder(field);
            sb.append("的长度必须在").append(minLength).append("和").append(maxLength).append("之间");
            addError(sb.toString());
            return true;
        }
        return false;
    }

    /**
     * @param email     邮件地址
     * @param field     属性
     * @param maxLength 长度
     * @return 判断是否为合法email地址
     */
    public boolean ifNotEmail(String email, String field, int maxLength) {
        if (ifBlank(email, field, maxLength)) {
            return true;
        }
        Matcher m = EMAIL_PATTERN.matcher(email);
        if (!m.matches()) {
            StringBuilder sb = new StringBuilder(field);
            sb.append("必须为合法的电子邮箱格式");
            addError(sb.toString());
            return true;
        }
        return false;
    }

    /**
     * @param username  用户名
     * @param field     属性
     * @param minLength 最小长度
     * @param maxLength 最大长度
     * @return 判断是否合法用户名
     */
    public boolean ifNotUsername(String username, String field, int minLength, int maxLength) {
        if (ifOutOfLength(username, field, minLength, maxLength)) {
            return true;
        }
        Matcher m = USERNAME_PATTERN.matcher(username);
        if (!m.matches()) {
            StringBuilder sb = new StringBuilder(field);
            sb.append("用户名只允许字母、数字、中文字和'_','-','@'");
            addError(sb.toString());
            return true;
        }
        return false;
    }

    public boolean ifNotExist(Object o, Class<?> clazz, Serializable id) {
        if (null == o) {
            String error = String.format("实体类 %s, ID=%s, 不存在", clazz.getSimpleName(), id.toString());
            addError(error);
            return true;
        } else {
            return false;
        }
    }


    protected String getErrorAttrName() {
        return ERROR_ATTR_NAME;
    }

    protected String getErrorPage() {
        return ERROR_PAGE;
    }

    private WebErrors() {
    }
}
