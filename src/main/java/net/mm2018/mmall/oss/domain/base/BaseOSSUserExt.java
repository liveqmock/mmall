/**
 * BaseOSSUserExt.java
 *
 * Copyright 2012 lemon sea, Inc. All Rights Reserved.
 */
package net.mm2018.mmall.oss.domain.base;

import java.io.Serializable;
import java.util.Date;

/**
 * 后台用户扩展.
 * User: Cosmo<cosmo.wang@kaixinai.com>
 * Date: 12-3-24
 * Time: 下午11:10
 */
@SuppressWarnings("serial")
public abstract class BaseOSSUserExt
        implements Serializable {

    private Integer userId;
    private String realName;
    private Boolean gender;
    private Date birthday;
    private String intro;
    private String qq;
    private String msn;
    private String phone;
    private String mobile;
    private String userImg;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getMsn() {
        return msn;
    }

    public void setMsn(String msn) {
        this.msn = msn;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("BaseOSSUserExt");
        sb.append("{userId=").append(userId);
        sb.append(", realName='").append(realName).append('\'');
        sb.append(", gender=").append(gender);
        sb.append(", birthday=").append(birthday);
        sb.append(", intro='").append(intro).append('\'');
        sb.append(", qq='").append(qq).append('\'');
        sb.append(", msn='").append(msn).append('\'');
        sb.append(", phone='").append(phone).append('\'');
        sb.append(", mobile='").append(mobile).append('\'');
        sb.append(", userImg='").append(userImg).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
