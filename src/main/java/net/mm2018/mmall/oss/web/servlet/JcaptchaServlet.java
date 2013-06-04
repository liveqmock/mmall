/**
 * JcaptchaServlet.java
 *
 * Copyright 2012 lemon sea, Inc. All Rights Reserved.
 */
package net.mm2018.mmall.oss.web.servlet;

import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.image.ImageCaptchaService;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 提供验证码图片的Servlet.
 * User: Cosmo<cosmo.wang@kaixinai.com>
 * Date: 12-3-24
 * Time: 下午9:43
 */
@SuppressWarnings("serial")
public class JcaptchaServlet
        extends HttpServlet {

    private static final String CAPTCHA_IMAGE_FORMAT = "jpeg";
    private ImageCaptchaService captchaService;

    @Override
    public void init() throws ServletException {
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        captchaService = BeanFactoryUtils.beanOfTypeIncludingAncestors(ctx, ImageCaptchaService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        byte[] captchaChallengeAsJpeg;
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        try {
            String captchaId = req.getSession().getId();
            BufferedImage challenge = captchaService.getImageChallengeForID(captchaId, req.getLocale());
            ImageIO.write(challenge, CAPTCHA_IMAGE_FORMAT, jpegOutputStream);
        } catch (CaptchaServiceException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }

        captchaChallengeAsJpeg = jpegOutputStream.toByteArray();

        // flush it in the response
        resp.setHeader("Cache-Control", "no-store");
        resp.setHeader("Pragma", "no-cache");
        resp.setDateHeader("Expires", 0);
        resp.setContentType("image/" + CAPTCHA_IMAGE_FORMAT);

        ServletOutputStream responseOutputStream = resp.getOutputStream();
        responseOutputStream.write(captchaChallengeAsJpeg);
        responseOutputStream.flush();
        responseOutputStream.close();
    }
}
