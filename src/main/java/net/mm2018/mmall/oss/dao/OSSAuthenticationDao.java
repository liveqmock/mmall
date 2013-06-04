/**
 * OSSAuthenticationDao.java
 *
 * Copyright 2012 lemon sea, Inc. All Rights Reserved.
 */
package net.mm2018.mmall.oss.dao;

import net.mm2018.mmall.common.mybatis.MyBatisDao;
import net.mm2018.mmall.oss.domain.OSSAuthentication;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * 后台授权数据操作接口.
 * User: Cosmo<cosmo.wang@kaixinai.com>
 * Date: 12-3-23
 * Time: 下午5:19
 */
@MyBatisDao
@Repository
public interface OSSAuthenticationDao {

    /**
     * 删除过期认证
     *
     * @param date 过期时间
     * @return 删除数量
     */
    int deleteExpire(Date date);

    /**
     * 根据ID删除认证信息
     *
     * @param authenticationId 认证ID
     * @return 影响行数
     */
    Integer deleteById(String authenticationId);

    /**
     * 更新认证时间
     *
     * @param authentication 认证信息
     */
    void updateAuthenticationTime(OSSAuthentication authentication);

    /**
     * @param authenticationId 认证ID
     * @return 通过认证ID查找认证信息
     */
    OSSAuthentication findById(String authenticationId);

    /**
     * @param authentication 认证信息
     * @return 保存的认证信息
     */
    void save(OSSAuthentication authentication);

}
