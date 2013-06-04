/**
 * SimpleMailServiceImpl.java
 *
 * Copyright 2012 lemon sea, Inc. All Rights Reserved.
 */
package net.mm2018.mmall.common.email;

import cn.kxai.common.log.Log;
import cn.kxai.common.log.Logs;
import org.apache.commons.lang3.StringUtils;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Date;

/**
 * 纯文本邮件业务实现.
 * User: Cosmo<cosmo.wang@kaixinai.com>
 * Date: 12-3-27
 * Time: 下午1:37
 */
public class SimpleMailService {

    private static Log log = Logs.get();

    /** Spring的MailSender. */
    private JavaMailSender mailSender;
    /** 发送邮箱 */
    private String from;

    /**
     * 后台登录时为用户发送邮箱验证码
     *
     * @param to         用户邮箱
     * @param verifyCode 验证码
     * @return true:发送成功
     */
    public boolean sendVerifyCode4OSSLogin(String to, String verifyCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom(from);
        message.setSubject("MMall后台登录验证码");
        //将用户名与当期日期格式化到邮件内容的字符串模板
        String content = String.format("您的验证码是%1$s.", verifyCode, new Date());
        message.setText(content);

        try {
            mailSender.send(message);
            if (log.isInfoEnabled()) {
                log.infof("纯文本邮件已发送至%s", StringUtils.join(message.getTo(), ","));
            }
            return true;
        } catch (Exception e) {
            log.error("发送邮件失败", e);
            return false;
        }
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
}
