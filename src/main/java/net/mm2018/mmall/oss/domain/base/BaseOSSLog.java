/**
 * BaseOSSLog.java
 *
 * Copyright 2013 lemon sea, Inc. All Rights Reserved.
 */
package net.mm2018.mmall.oss.domain.base;

import net.mm2018.mmall.oss.domain.OSSUser;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.Date;

/**
 * 后台日志.
 * User: Cosmo<cosmo.wang@ningmenghai.com>
 * Date: 13-3-8
 * Time: 上午11:24
 */
public abstract class BaseOSSLog
        implements Serializable {

    private Integer logId;
    private Integer userId;
    private Integer type;
    private String ip;
    private String url;
    private String title;
    private String content;
    private Date createTime;

    private OSSUser user;

    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public OSSUser getUser() {
        return user;
    }

    public void setUser(OSSUser user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).
                append("logId", logId).
                append("userId", userId).
                append("type", type).
                append("ip", ip).
                append("url", url).
                append("title", title).
                append("content", content).
                append("createTime", createTime).
                append("user", user).
                toString();
    }
}
