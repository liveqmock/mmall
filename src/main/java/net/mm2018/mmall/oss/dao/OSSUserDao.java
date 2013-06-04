/**
 * OSSUserDao.java
 *
 * Copyright 2012 lemon sea, Inc. All Rights Reserved.
 */
package net.mm2018.mmall.oss.dao;

import net.mm2018.mmall.common.mybatis.MyBatisDao;
import net.mm2018.mmall.oss.domain.OSSUser;
import net.mm2018.mmall.oss.domain.OSSUserExt;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 后台用户数据操作接口.
 * User: Cosmo<cosmo.wang@kaixinai.com>
 * Date: 12-3-23
 * Time: 下午5:15
 */
@MyBatisDao
@Repository
public interface OSSUserDao {

    /**
     * @param userId 用户ID
     * @return 根据用户ID查找后台用户
     */
    OSSUser findById(Integer userId);

    /**
     * @param username 用户名
     * @return 根据用户名查找后台用户
     */
    OSSUser findByUsername(String username);

    /**
     * 更新用户登录信息
     *
     * @param user 更新参数
     */
    void updateLoginInfo(OSSUser user);

    /**
     * 更新用户登录成功信息
     *
     * @param user 更新参数
     */
    void updateLoginSuccess(OSSUser user);

    /**
     * 更新用户登录错误信息
     *
     * @param user 更新参数
     */
    void updateLoginError(OSSUser user);

    /**
     * 更新密码邮箱
     *
     * @param user 用户信息
     */
    void updatePwdEmail(OSSUser user);

    /**
     * //     * @param queryUsername 用户名
     * //     * @param queryEmail    电子邮箱
     * //     * @param queryDisabled 是否禁用
     * //     * @param admin         是否管理员
     * //     * @param rank          等级
     * //     * @param pageNo        页码
     * //     * @param pageSize      页大小
     *
     * @return 查找列表
     */
    List<OSSUser> findListByQuery(Map<String, Object> params);

    int countListByQuery(Map<String, Object> params);

    /**
     * @param userId 用户ID
     * @return 根据ID查找用户扩展信息
     */
    OSSUserExt findUserExtByUserId(Integer userId);

    /**
     * 更新用户扩展信息
     *
     * @param ext 用户扩展
     */
    void updateUserExt(OSSUserExt ext);

    /**
     * 保存用户扩展
     *
     * @param ext 用户扩展
     */
    void saveUserExt(OSSUserExt ext);

    /**
     * 保存用户信息
     *
     * @param user 用户信息
     * @return 用户ID
     */
    void saveUser(OSSUser user);

    /**
     * 保存用户角色
     *
     * //     * @param userId 用户ID
     * //     * @param roleId 角色ID
     */
    void saveUserRole(Map<String, Object> params);

    /**
     * 根据用户ID删除用户
     *
     * @param userId 用户ID
     * @return 删除结果
     */
    Integer deleteById(Integer userId);

    /**
     * 根据用户ID删除用户扩展
     *
     * @param userId 用户ID
     */
    void deleteUserExtById(Integer userId);

    /**
     * 更新用户信息
     *
     * @param user 用户
     * @return user
     */
    Integer update(OSSUser user);

    /**
     * 根据用户ID删除用户角色
     *
     * @param userId 用户ID
     */
    void deleteUserRoleByUserId(Integer userId);
}
