/**
 * OSSRoleController.java
 *
 * Copyright 2012 lemon sea, Inc. All Rights Reserved.
 */
package net.mm2018.mmall.oss.web.controller;

import cn.kxai.common.log.Log;
import cn.kxai.common.log.Logs;
import net.mm2018.mmall.oss.domain.OSSRole;
import net.mm2018.mmall.oss.service.OSSLogSvc;
import net.mm2018.mmall.oss.service.OSSRoleSvc;
import net.mm2018.mmall.oss.util.WebErrors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 角色控制.
 * User: Cosmo<cosmo.wang@kaixinai.com>
 * Date: 12-3-29
 * Time: 下午10:45
 */
@Controller
public class OSSRoleController {

    private static Log log = Logs.get();

    @Autowired
    private OSSRoleSvc ossRoleSvc;
    @Autowired
    private OSSLogSvc ossLogSvc;

    @RequestMapping("/role/v_list.jhtml")
    public String list(ModelMap model) {
        List<OSSRole> list = ossRoleSvc.getAll();
        model.addAttribute("list", list);
        return "role/list";
    }

    @RequestMapping("/role/v_add.jhtml")
    public String add() {
        return "role/add";
    }

    @RequestMapping("/role/v_edit.jhtml")
    public String edit(Integer id, ModelMap model) {
        WebErrors errors = validateEdit(id);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        model.addAttribute("ossRole", ossRoleSvc.getById(id));
        return "role/edit";
    }

    @RequestMapping("/role/o_create.jhtml")
    public String create(HttpServletRequest request, OSSRole bean, String[] perms) {
        bean = ossRoleSvc.create(bean, splitPerms(perms));
        log.infof("create OSSRole roleId = %d", bean.getRoleId());
        ossLogSvc.operating(request, "添加角色", "roleId=" + bean.getRoleId());
        return "redirect:v_list.jhtml";
    }

    @RequestMapping("/role/o_modify.jhtml")
    public String modify(HttpServletRequest request, OSSRole bean, String[] perms, ModelMap model) {
        WebErrors errors = validateEdit(bean.getRoleId());
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        bean = ossRoleSvc.modify(bean, splitPerms(perms));
        log.infof("modify OSSRole roleId = %d", bean.getRoleId());
        ossLogSvc.operating(request, "修改角色", "roleId=" + bean.getRoleId() + ";roleName=" + bean.getRoleName());
        return list(model);
    }

    @RequestMapping("/role/o_remove.jhtml")
    public String remove(HttpServletRequest request, Integer[] ids, ModelMap model) {
        WebErrors errors = validateRemove(ids);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        Integer[] results = ossRoleSvc.removeByIds(ids);
        for (int i = 0, n = results.length; i < n; i++) {
            if (results[i] > 0) {
                log.infof("remove role success!id = %d", ids[i]);
                ossLogSvc.operating(request, "删除角色", "id=" + ids[i]);
            } else {
                log.infof("remove role failed!id = %d", ids[i]);
            }
        }
        return list(model);
    }

    /**
     * 校验删除Ids
     *
     * @param ids ID数组
     * @return boolean
     */
    private WebErrors validateRemove(Integer[] ids) {
        WebErrors errors = WebErrors.create();
        errors.ifEmpty(ids, "ids");
        for (Integer id : ids) {
            vldExist(id, errors);
        }
        return errors;
    }

    /**
     * @param perms 权限字符串
     * @return 拆分权限
     */
    private Set<String> splitPerms(String[] perms) {
        Set<String> set = new HashSet<String>();
        if (perms != null) {
            for (String perm : perms) {
                for (String p : StringUtils.split(perm, ',')) {
                    if (!StringUtils.isBlank(p)) {
                        set.add(p);
                    }
                }
            }
        }
        return set;
    }

    /**
     * 校验编辑
     *
     * @param id ID
     * @return boolean
     */
    private WebErrors validateEdit(Integer id) {
        WebErrors errors = WebErrors.create();
        vldExist(id, errors);
        return errors;
    }

    /**
     * 校验是否存在
     *
     * @param id     ID
     * @param errors WebErrors
     * @return boolean
     */
    private boolean vldExist(Integer id, WebErrors errors) {
        if (errors.ifNull(id, "id")) {
            return true;
        }
        OSSRole entity = ossRoleSvc.getById(id);
        return errors.ifNotExist(entity, OSSRole.class, id);
    }
}
