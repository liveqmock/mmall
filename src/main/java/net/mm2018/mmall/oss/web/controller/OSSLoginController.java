/**
 * OSSLoginController.java
 *
 * Copyright 2012 lemon sea, Inc. All Rights Reserved.
 */
package net.mm2018.mmall.oss.web.controller;

import cn.kxai.common.web.util.CookieUtil;
import cn.kxai.common.web.util.RequestUtil;
import cn.kxai.common.web.util.ResponseUtil;
import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.image.ImageCaptchaService;
import net.mm2018.mmall.common.config.Constants;
import net.mm2018.mmall.common.email.SimpleMailService;
import net.mm2018.mmall.oss.domain.OSSAuthentication;
import net.mm2018.mmall.oss.domain.OSSConfig;
import net.mm2018.mmall.oss.domain.OSSUser;
import net.mm2018.mmall.oss.exception.BadCredentialException;
import net.mm2018.mmall.oss.exception.UserDisabledException;
import net.mm2018.mmall.oss.exception.UsernameNotFoundException;
import net.mm2018.mmall.oss.service.OSSAuthenticationSvc;
import net.mm2018.mmall.oss.service.OSSConfigSvc;
import net.mm2018.mmall.oss.service.OSSLogSvc;
import net.mm2018.mmall.oss.service.OSSUserSvc;
import net.mm2018.mmall.oss.util.OSSUtil;
import net.mm2018.mmall.oss.util.WebErrors;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 后台登陆控制器.
 * User: Cosmo<cosmo.wang@kaixinai.com>
 * Date: 12-3-23
 * Time: 下午6:38
 */
@Controller
public class OSSLoginController {

    public static final String RETURN_URL = "returnUrl";
    /** 剩余错误次数: cookie key name */
    private static final String COOKIE_ERROR_REMAINING = "_error_remaining";
    private static final String DEFAULT_RETURN_URL = Constants.getDomain() + "/mmall/oss/index.jhtml";

    @Autowired
    private OSSUserSvc ossUserSvc;
    @Autowired
    private OSSConfigSvc ossConfigSvc;
    @Autowired
    private OSSAuthenticationSvc ossAuthenticationSvc;
    @Autowired
    private OSSLogSvc ossLogSvc;
    @Autowired
    private ImageCaptchaService imageCaptchaService;
    @Autowired
    private SimpleMailService simpleMailService;

    @RequestMapping(value = "login.jhtml", method = RequestMethod.GET)
    public String login(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        String returnUrl = RequestUtil.getParameterForString(request, RETURN_URL, null);
        String message = RequestUtil.getParameterForString(request, "message", null);
        String authId = (String) RequestUtil.getSessionAttribute(request, OSSAuthenticationSvc.AUTH_KEY);
        if (null != authId) {
            // 存在认证ID
            OSSAuthentication authentication = ossAuthenticationSvc.retrieve(authId);
            // 存在认证信息,且未过期
            if (null != authentication) {
                model.addAttribute("auth", authentication);
                String view = getReturnView(returnUrl);
                if (null != view) {
                    return view;
                }
                return "redirect:" + DEFAULT_RETURN_URL;
            }
        }
        writeCookieErrorRemaining(null, request, response, model);
        if (!StringUtils.isBlank(returnUrl)) {
            model.addAttribute(RETURN_URL, returnUrl);
        }
        if (!StringUtils.isBlank(message)) {
            model.addAttribute("message", message);
        }
        //检测IP来源,设置变量,是否显示“获取外网登录验证码”
//        if (!OSSUtil.isWhiteIp4Login(request)) {
//            model.put("isOutsideNetwork", "Y");
//        }
        return "login";
    }

    @RequestMapping(value = "login.jhtml", method = RequestMethod.POST)
    public String loginSubmit(String username, String password, String captcha, String returnUrl, String message,
            HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        Integer errorRemaining = ossUserSvc.errorRemaining(username);
        WebErrors errors = validateLoginSubmit(username, password, captcha, errorRemaining, request);
        if (!errors.hasErrors()) {
            try {
                String ip = RequestUtil.getIpAddress(request);
                OSSAuthentication authentication = ossAuthenticationSvc.login(username, password, ip, request);
                ossUserSvc.modifyLoginInfo(authentication.getUserId(), ip);
                OSSUser user = ossUserSvc.getByUserId(authentication.getUserId());
                if (user.getDisabled()) {
                    // 如果已经禁用,则退出登录
                    ossAuthenticationSvc.removeByAuthenticationId(authentication.getAuthenticationId());
                    HttpSession session = request.getSession(false);
                    if (null != session) {
                        session.invalidate();
                    }
                    throw new UserDisabledException("user disabled");
                }
                removeCookieErrorRemaining(request, response);
                ossLogSvc.loginSuccess(request, user, "登录成功");
                String view = getReturnView(returnUrl);
                if (null != view) {
                    return view;
                }
                return "redirect:" + DEFAULT_RETURN_URL;
            } catch (UsernameNotFoundException e) {
                errors.addError(e.getMessage());
            } catch (BadCredentialException e) {
                errors.addError(e.getMessage());
            } catch (UserDisabledException e) {
                errors.addError(e.getMessage());
            }
        }
        ossLogSvc.loginFailure(request, "登录失败", "username=" + username + ";password=" + password);
        // 登录失败
        writeCookieErrorRemaining(errorRemaining, request, response, model);
        errors.putToModelMap(model);
        if (!StringUtils.isBlank(returnUrl)) {
            model.addAttribute(RETURN_URL, returnUrl);
        }
        if (!StringUtils.isBlank(message)) {
            model.addAttribute("message", message);
        }
        //检测IP来源,设置变量,是否显示“获取外网登录验证码”
//        if (!OSSUtil.isWhiteIp4Login(request)) {
//            model.put("isOutsideNetwork", "Y");
//        }
        return "login";
    }

    @RequestMapping("logout.jhtml")
    public String logout(HttpServletRequest request) {
        String authId = (String) RequestUtil.getSessionAttribute(request, OSSAuthenticationSvc.AUTH_KEY);
        if (null != authId) {
            ossAuthenticationSvc.removeByAuthenticationId(authId);
            HttpSession session = request.getSession(false);
            if (null != session) {
                session.invalidate();
            }
        }
        String returnUrl = RequestUtil.getParameterForString(request, RETURN_URL, null);
        String view = getReturnView(returnUrl);
        if (null != view) {
            return view;
        }
        return "redirect:login.jhtml";
    }

    @RequestMapping("verifyCode2Email.jhtml")
    public void verifyCode2Email(String email, HttpServletRequest request, HttpServletResponse response) {
        if (!validateEmail(email)) {
            ResponseUtil.renderJson(response, "{success:false,msg:'只能使用本公司邮箱申请'}");
            return;
        }
        StringBuilder code = new StringBuilder();
        final int codeLen = 6;
        for (int i = 0; i < codeLen; i++) {
            code.append((int) (Math.random() * 10));
        }
        if (simpleMailService.sendVerifyCode4OSSLogin(email, code.toString())) {
            RequestUtil.setSessionAttribute(request, email, code.toString());
            ResponseUtil.renderJson(response, "{success:true,msg:'邮件已发送，请登录邮箱查看验证码'}");
        } else {
            ResponseUtil.renderJson(response, "{success:false,msg:'发送邮件失败'}");
        }
    }

    /** @return 验证是否公司邮箱地址 */
    private boolean validateEmail(String email) {
        return !(StringUtils.isBlank(email) ||
                StringUtils.isNotBlank(email.replaceAll("^([a-zA-Z0-9_\\-\\.])+@ningmenghai\\.com$", "")));
    }

    /**
     * 校验登录提交参数
     *
     * @param username       用户名
     * @param password       密码
     * @param captcha        验证码
     * @param errorRemaining 剩余登录错误数
     * @param request        HttpServletRequest
     * @return WebErrors
     */
    private WebErrors validateLoginSubmit(String username, String password, String captcha, Integer errorRemaining,
            HttpServletRequest request) {
        WebErrors errors = WebErrors.create();
        if (errors.ifOutOfLength(username, "用户名", 1, 100)) {
            return errors;
        }
        if (errors.ifOutOfLength(password, "密码", 1, 32)) {
            return errors;
        }
        // 如果输入了验证码,那么必须验证;如果没有输入验证码,则根据当前用户判断是否需要验证码.
        if (!StringUtils.isBlank(captcha) || (null != errorRemaining && errorRemaining < 0)) {
            if (errors.ifBlank(captcha, "验证码", 100)) {
                return errors;
            }
            try {
                if (!imageCaptchaService.validateResponseForID(request.getSession().getId(), captcha)) {
                    errors.addError("验证码错误");
                    return errors;
                }
            } catch (CaptchaServiceException e) {
                errors.addError("验证码无效");
                return errors;
            }
        }
//        if (!OSSUtil.isWhiteIp4Login(request)) {
//            String verifyCodeFromParam = RequestUtil.getParameterForString(request, "outsideNetworkVerifyCode", null);
//            String email = RequestUtil.getParameterForString(request, "insideNetworkEmail", null);
//            if (StringUtils.isBlank(verifyCodeFromParam) || StringUtils.isBlank(email)) {
//                errors.addError("外网登录校验码或内网邮箱无效");
//                return errors;
//            }
//            String emailVerifyCode = (String) RequestUtil.getSessionAttribute(request, email);
//            if (StringUtils.isBlank(emailVerifyCode) || !StringUtils.equals(emailVerifyCode, verifyCodeFromParam)) {
//                errors.addError("外网登录校验码无效");
//            }
//        }
        return errors;
    }

    /**
     * 删除Cookie中的剩余错误次数
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     */
    private void removeCookieErrorRemaining(HttpServletRequest request, HttpServletResponse response) {
        CookieUtil.addCookie(request, response, COOKIE_ERROR_REMAINING, "", 0, null);
    }

    /**
     * 剩余错误次数写入Cookie
     *
     * @param userErrorRemaining 用户剩余错误次数
     * @param request            HttpServletRequest
     * @param response           HttpServletResponse
     * @param model              ModelMap
     */
    private void writeCookieErrorRemaining(Integer userErrorRemaining, HttpServletRequest request,
            HttpServletResponse response, ModelMap model) {
        // 所有访问的页面都需要写一个cookie,这样可以判断已经登录了几次.
        Integer errorRemaining = getCookieErrorRemaining(request);
        if (null != userErrorRemaining && (null == errorRemaining || userErrorRemaining < errorRemaining)) {
            errorRemaining = userErrorRemaining;
        }
        OSSConfig.ConfigLogin configLogin = ossConfigSvc.getConfigLogin();
        // 最大错误次数
        final int maxErrorTimes = configLogin.getErrorTimes();
        if (null == errorRemaining || errorRemaining > maxErrorTimes) {
            errorRemaining = maxErrorTimes;
        } else if (errorRemaining <= 0) {
            errorRemaining = 0;
        } else {
            errorRemaining--;
        }
        model.addAttribute("errorRemaining", errorRemaining);
        // 时间间隔
        final int errorInterval = configLogin.getErrorInterval();
        CookieUtil.addCookie(request, response, COOKIE_ERROR_REMAINING, String.valueOf(errorRemaining),
                errorInterval * 60, null);
    }

    /**
     * @param request HttpServletRequest
     * @return 从Cookie中获取剩余错误数
     */
    private Integer getCookieErrorRemaining(HttpServletRequest request) {
        String value = CookieUtil.getCookieForString(request, COOKIE_ERROR_REMAINING);
        Integer errorRemaining = null;
        if (NumberUtils.isDigits(value)) {
            errorRemaining = Integer.parseInt(value);
        }
        return errorRemaining;
    }

    /**
     * @param returnUrl 返回地址
     * @return 重定向到返回地址
     */
    private String getReturnView(String returnUrl) {
        if (!StringUtils.isBlank(returnUrl)) {
            StringBuilder sb = new StringBuilder("redirect:");
            sb.append(returnUrl);
            return sb.toString();
        }
        return null;
    }
}
