/**
 * BaseOSSUser.java
 *
 * Copyright 2012 lemon sea, Inc. All Rights Reserved.
 */
package net.mm2018.mmall.oss.domain.base;

import net.mm2018.mmall.oss.domain.OSSRole;
import net.mm2018.mmall.oss.domain.OSSUserExt;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * 后台用户.
 * User: Cosmo<cosmo.wang@kaixinai.com>
 * Date: 12-3-24
 * Time: 下午11:03
 */
@SuppressWarnings("serial")
public abstract class BaseOSSUser
        implements Serializable {

    private Integer userId;
    private String username;
    private String password;
    private String email;
    private Date registerTime;
    private String registerIP;
    private Date lastLoginTime;
    private String lastLoginIP;
    private Integer loginCount;
    private Date errorTime;
    private Integer errorCount;
    private String errorIP;
    private Integer rank;
    private Boolean admin;
    private Boolean viewonlyAdmin;
    private Boolean selfAdmin;
    private Boolean disabled;

    private OSSUserExt ossUserExt;
    private Set<OSSRole> roles;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public String getRegisterIP() {
        return registerIP;
    }

    public void setRegisterIP(String registerIP) {
        this.registerIP = registerIP;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLastLoginIP() {
        return lastLoginIP;
    }

    public void setLastLoginIP(String lastLoginIP) {
        this.lastLoginIP = lastLoginIP;
    }

    public Integer getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }

    public Date getErrorTime() {
        return errorTime;
    }

    public void setErrorTime(Date errorTime) {
        this.errorTime = errorTime;
    }

    public Integer getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(Integer errorCount) {
        this.errorCount = errorCount;
    }

    public String getErrorIP() {
        return errorIP;
    }

    public void setErrorIP(String errorIP) {
        this.errorIP = errorIP;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public Boolean getViewonlyAdmin() {
        return viewonlyAdmin;
    }

    public void setViewonlyAdmin(Boolean viewonlyAdmin) {
        this.viewonlyAdmin = viewonlyAdmin;
    }

    public Boolean getSelfAdmin() {
        return selfAdmin;
    }

    public void setSelfAdmin(Boolean selfAdmin) {
        this.selfAdmin = selfAdmin;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public OSSUserExt getOssUserExt() {
        return ossUserExt;
    }

    public void setOssUserExt(OSSUserExt ossUserExt) {
        this.ossUserExt = ossUserExt;
    }

    public Set<OSSRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<OSSRole> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("BaseOSSUser");
        sb.append("{userId=").append(userId);
        sb.append(", username='").append(username).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", registerTime=").append(registerTime);
        sb.append(", registerIP='").append(registerIP).append('\'');
        sb.append(", lastLoginTime=").append(lastLoginTime);
        sb.append(", lastLoginIP='").append(lastLoginIP).append('\'');
        sb.append(", loginCount=").append(loginCount);
        sb.append(", errorTime=").append(errorTime);
        sb.append(", errorCount=").append(errorCount);
        sb.append(", errorIP='").append(errorIP).append('\'');
        sb.append(", rank=").append(rank);
        sb.append(", admin=").append(admin);
        sb.append(", viewonlyAdmin=").append(viewonlyAdmin);
        sb.append(", selfAdmin=").append(selfAdmin);
        sb.append(", disabled=").append(disabled);
        sb.append(", ossUserExt=").append(ossUserExt);
        sb.append(", roles=").append(roles);
        sb.append('}');
        return sb.toString();
    }
}
