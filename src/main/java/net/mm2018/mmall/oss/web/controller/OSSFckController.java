/**
 * OSSFckController.java
 *
 * Copyright 2013 lemon sea, Inc. All Rights Reserved.
 */
package net.mm2018.mmall.oss.web.controller;

import cn.kxai.common.lang.Encoding;
import cn.kxai.common.lang.FileUtil;
import cn.kxai.common.log.Log;
import cn.kxai.common.log.Logs;
import cn.kxai.common.upload.FileRepository;
import cn.kxai.common.upload.UploadUtil;
import net.mm2018.mmall.common.config.Constants;
import net.mm2018.mmall.common.fck.Command;
import net.mm2018.mmall.common.fck.ResourceType;
import net.mm2018.mmall.common.fck.UploadResponse;
import net.mm2018.mmall.common.fck.Utils;
import net.mm2018.mmall.common.image.ImageUtil;
import net.mm2018.mmall.common.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

/**
 * FCK服务器控制.
 * User: Cosmo<cosmo.wang@ningmenghai.com>
 * Date: 13-4-22
 * Time: 下午5:15
 */
@Controller
public class OSSFckController {

    private static Log log = Logs.get();

    @Autowired
    private FileRepository fileRepository;

    @RequestMapping(value = "/fck/upload.jhtml", method = RequestMethod.POST)
    public void upload(@RequestParam(value = "Command", required = false) String commandStr,
            @RequestParam(value = "Type", required = false) String typeStr,
            @RequestParam(value = "CurrentFolder", required = false) String currentFolderStr,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.debug("Entering Dispatcher#doPost");
        responseInit(response);
        if (Utils.isEmpty(commandStr) && Utils.isEmpty(currentFolderStr)) {
            commandStr = "QuickUpload";
            currentFolderStr = "/";
            if (Utils.isEmpty(typeStr)) {
                typeStr = "File";
            }
        }
        if (currentFolderStr != null && !currentFolderStr.startsWith("/")) {
            currentFolderStr = "/".concat(currentFolderStr);
        }
        log.debugf("Parameter Command: %s", commandStr);
        log.debugf("Parameter Type: %s", typeStr);
        log.debugf("Parameter CurrentFolder: %s", currentFolderStr);
        UploadResponse ur = validateUpload(commandStr, typeStr, currentFolderStr);
        if (null == ur) {
            ur = doUpload(request, typeStr, currentFolderStr);
        }
        PrintWriter out = response.getWriter();
        out.print(ur);
        out.flush();
        out.close();
    }

    private UploadResponse doUpload(HttpServletRequest request, String typeStr, String currentFolderStr) {
        ResourceType type = ResourceType.getDefaultResourceType(typeStr);
        try {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            // We upload just one file at the same time
            MultipartFile uplFile = multipartRequest.getFileMap().entrySet().iterator().next().getValue();
            // Some browsers transfer the entire source path not just the

            String origName = uplFile.getOriginalFilename();
            // filename
            String filename = FileUtil.getName(origName);
            log.debugf("Parameter NewFile: %s", filename);
            String ext = FileUtil.getExtension(origName).toLowerCase(Locale.ENGLISH);
            if (type.isDeniedExtension(ext)) {
                return UploadResponse.getInvalidFileTypeError();
            }
            if (type.equals(ResourceType.IMAGE) && !ImageUtil.isImage(uplFile.getInputStream())) {
                return UploadResponse.getInvalidFileTypeError();
            }
            boolean isImg = type.equals(ResourceType.IMAGE);
            log.debugf("Parameter isImg: %s", isImg);
            String fileUri = fileRepository.storeByExt(Constants.getQmsSourceUriPrefix(), ext, uplFile.getBytes());
            String fileUrl = CommonUtil.getResourceUrl(fileUri);
            return UploadResponse.getOK(fileUrl);
        } catch (IOException e) {
            return UploadResponse.getFileUploadWriteError();
        }
    }

    private void responseInit(HttpServletResponse response) {
        response.setCharacterEncoding(Encoding.UTF8);
        response.setContentType("text/html");
        response.setHeader("Cache-Control", "no-cache");
    }

    private UploadResponse validateUpload(String commandStr, String typeStr, String currentFolderStr) {
        if (!Command.isValidForPost(commandStr)) {
            return UploadResponse.getInvalidCommandError();
        }
        if (!ResourceType.isValidType(typeStr)) {
            return UploadResponse.getInvalidResourceTypeError();
        }
        if (!UploadUtil.isValidPath(currentFolderStr)) {
            return UploadResponse.getInvalidCurrentFolderError();
        }
        return null;
    }
}
