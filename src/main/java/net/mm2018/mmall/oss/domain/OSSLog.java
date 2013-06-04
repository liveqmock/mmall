/**
 * OSSLog.java
 *
 * Copyright 2013 lemon sea, Inc. All Rights Reserved.
 */
package net.mm2018.mmall.oss.domain;

import cn.kxai.common.lang.enums.IEnum;
import net.mm2018.mmall.oss.domain.base.BaseOSSLog;

/**
 * 后台日志.
 * User: Cosmo<cosmo.wang@ningmenghai.com>
 * Date: 13-3-8
 * Time: 上午11:30
 */
@SuppressWarnings("serial")
public class OSSLog
        extends BaseOSSLog {

    /** 日志类型枚举 */
    public enum TypeEnum
            implements IEnum<Integer> {
        LOGIN_SUCCESS(1, "登录成功"), //
        LOGIN_FAILURE(2, "登录失败"), //
        OPERATING(3, "操作"), //
        ;

        private final Integer code;
        private final String remark;

        TypeEnum(Integer code, String remark) {
            this.code = code;
            this.remark = remark;
        }

        @Override
        public Integer getCode() {
            return code;
        }

        @Override
        public String getRemark() {
            return remark;
        }
    }
}
