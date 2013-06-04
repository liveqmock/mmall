/**
 * OSSRoleSvcImpl.java
 *
 * Copyright 2012 lemon sea, Inc. All Rights Reserved.
 */
package net.mm2018.mmall.oss.service;

import net.mm2018.mmall.common.cache.ehcache.EhcacheService;
import net.mm2018.mmall.oss.dao.OSSRoleDao;
import net.mm2018.mmall.oss.domain.OSSRole;
import net.mm2018.mmall.oss.domain.OSSRolePermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * 后台角色业务接口实现.
 * User: Cosmo<cosmo.wang@kaixinai.com>
 * Date: 12-3-24
 * Time: 下午10:46
 */
@Transactional
@Service
public class OSSRoleSvc {

    @Autowired
    private OSSRoleDao ossRoleDao;
    @Autowired
    private EhcacheService ehcacheService;

    @Transactional(readOnly = true)
    public List<OSSRole> getAll() {
        return ossRoleDao.findAll();
    }

    /**
     * @param roleId 角色ID
     * @return 根据角色ID获取角色
     */
    @Transactional(readOnly = true)
    public OSSRole getById(Integer roleId) {
        String cacheKey = buildCacheKey(roleId);
        Object obj = ehcacheService.get(cacheKey);
        if (null != obj) {
            return (OSSRole) obj;
        }
        OSSRole role = ossRoleDao.findById(roleId);
        if (null != role) {
            ehcacheService.put(cacheKey, role);
        }
        return role;
    }

    /**
     * 批量删除角色
     *
     * @param ids 角色ID数组
     */
    public Integer[] removeByIds(Integer[] ids) {
        Integer[] result = new Integer[ids.length];
        for (int i = 0, len = ids.length; i < len; i++) {
            result[i] = removeById(ids[i]);
        }
        return result;
    }

    public OSSRole create(OSSRole bean, Set<String> perms) {
        ossRoleDao.save(bean);
        OSSRolePermission rolePermission;
        for (String perm : perms) {
            rolePermission = new OSSRolePermission(bean.getRoleId(), perm);
            ossRoleDao.saveRolePermission(rolePermission);
        }
        ehcacheService.remove(buildCacheKey(bean.getRoleId()));
        return bean;
    }

    public OSSRole modify(OSSRole bean, Set<String> perms) {
        ossRoleDao.update(bean);
        ossRoleDao.deletePermissionByRoleId(bean.getRoleId());
        OSSRolePermission rolePermission;
        for (String perm : perms) {
            rolePermission = new OSSRolePermission(bean.getRoleId(), perm);
            ossRoleDao.saveRolePermission(rolePermission);
        }
        ehcacheService.remove(buildCacheKey(bean.getRoleId()));
        return bean;
    }

    /**
     * 根据ID删除角色
     *
     * @param roleId 角色ID
     * @return 删除结果
     */
    private Integer removeById(Integer roleId) {
        Integer result = ossRoleDao.deleteById(roleId);
        if (result > 0) {
            ehcacheService.remove(buildCacheKey(roleId));
        }
        return result;
    }

    /**
     * @param roleId 角色ID
     * @return 构建缓存key
     */
    private String buildCacheKey(Integer roleId) {
        return "OSSRole." + roleId;
    }
}
