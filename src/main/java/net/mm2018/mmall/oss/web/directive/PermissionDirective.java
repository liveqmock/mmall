/**
 * PermissionDirective.java
 *
 * Copyright 2012 lemon sea, Inc. All Rights Reserved.
 */
package net.mm2018.mmall.oss.web.directive;

import cn.kxai.common.web.freemarker.DirectiveUtil;
import freemarker.core.Environment;
import freemarker.template.*;
import net.mm2018.mmall.oss.web.interceptor.AdminContextInterceptor;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Map;

/**
 * 后台管理员权限许可.
 * User: Cosmo<cosmo.wang@kaixinai.com>
 * Date: 12-3-25
 * Time: 上午12:12
 */
public class PermissionDirective
        implements TemplateDirectiveModel {

    /** 此url必须和perm中url一致 */
    public static final String PARAM_URL = "url";

    @SuppressWarnings({"unchecked"})
    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
            throws TemplateException, IOException {
        String url = DirectiveUtil.getString(PARAM_URL, params);
        boolean pass = false;
        if (StringUtils.isBlank(url)) {
            // url为空,则认为有权限.
            pass = true;
        } else {
            TemplateSequenceModel perms = getPerms(env);
            if (null == perms) {
                // perms为null,则代表不需要判断权限.
                pass = true;
            } else {
                String perm;
                for (int i = 0, n = perms.size(); i < n; i++) {
                    perm = ((TemplateScalarModel) perms.get(i)).getAsString();
                    if (url.startsWith(perm)) {
                        pass = true;
                        break;
                    }
                }
            }
        }
        if (pass) {
            body.render(env.getOut());
        }
    }

    private TemplateSequenceModel getPerms(Environment environment) throws TemplateModelException {
        TemplateModel model = environment.getDataModel().get(AdminContextInterceptor.PERMISSION_MODEL);
        if (null == model) {
            return null;
        }
        if (model instanceof TemplateSequenceModel) {
            return (TemplateSequenceModel) model;
        } else {
            throw new TemplateModelException("'perms' in data model not a TemplateSequenceModel");
        }
    }
}
