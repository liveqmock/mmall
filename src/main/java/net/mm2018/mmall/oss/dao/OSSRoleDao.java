/**
 * OSSRoleDao.java
 *
 * Copyright 2012 lemon sea, Inc. All Rights Reserved.
 */
package net.mm2018.mmall.oss.dao;

import net.mm2018.mmall.common.mybatis.MyBatisDao;
import net.mm2018.mmall.oss.domain.OSSRole;
import net.mm2018.mmall.oss.domain.OSSRolePermission;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 后台角色数据操作接口.
 * User: Cosmo<cosmo.wang@kaixinai.com>
 * Date: 12-3-24
 * Time: 下午10:47
 */
@MyBatisDao
@Repository
public interface OSSRoleDao {

    /** @return 获取所有后台角色 */
    List<OSSRole> findAll();

    /**
     * @param roleId 角色ID
     * @return 根据角色ID查找角色
     */
    OSSRole findById(Integer roleId);

    /**
     * 根据角色ID删除
     *
     * @param roleId 角色ID
     * @return 删除结果
     */
    Integer deleteById(Integer roleId);

    /**
     * 保存角色
     *
     * @param bean 角色信息
     * @return 角色
     */
    void save(OSSRole bean);

    /** 保存角色权限 */
    void saveRolePermission(OSSRolePermission bean);

    /**
     * 更新用户角色
     *
     * @param bean 角色信息
     * @return 角色
     */
    Integer update(OSSRole bean);

    /**
     * 根据角色ID删除权限
     *
     * @param roleId 角色ID
     */
    void deletePermissionByRoleId(Integer roleId);
}
