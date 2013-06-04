/**
 * OSSCommonController.java
 *
 * Copyright 2013 lemon sea, Inc. All Rights Reserved.
 */
package net.mm2018.mmall.oss.web.controller;

import cn.kxai.common.lang.FileUtil;
import cn.kxai.common.log.Log;
import cn.kxai.common.log.Logs;
import cn.kxai.common.upload.FileRepository;
import net.mm2018.mmall.common.config.Constants;
import net.mm2018.mmall.common.image.ImageScaleAverage;
import net.mm2018.mmall.common.image.ImageUtil;
import net.mm2018.mmall.common.util.CommonUtil;
import net.mm2018.mmall.oss.util.WebErrors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Locale;

/**
 * 通用controller, 如:图片处理.
 * User: Cosmo<cosmo.wang@ningmenghai.com>
 * Date: 13-3-12
 * Time: 上午11:28
 */
@Controller
public class OSSCommonController {
    private static final Log log = Logs.get();
    /** 结果页 */
    private static final String IMAGE_UPLOAD_RESULT_PAGE = "common/image_iframe";
    /** 图片选择页面 */
    private static final String IMAGE_SELECT_RESULT = "common/image_area_select";
    /** 图片裁剪完成页面 */
    private static final String IMAGE_CUT_RESULT = "common/image_cut";

    private static final String MEDIA_UPLOAD_RESULT_PAGE = "common/media_iframe";

    private static final String FILE_UPLOAD_RESULT_PAGE = "common/file_iframe";
    private static final String ERROR = "error";

    /** 后缀 */
    private static final String[] FILE_EXT = {"lrc", "nrc", "krc", "txt"};

    /** 后缀 */
    private static final String[] MEDIA_EXT = {"mp3"};

    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private ImageScaleAverage imageScaleAverage;

    @RequestMapping("/common/o_upload_file.jhtml")
    public String fileUpload(String filename, Integer uploadNum,
            @RequestParam(value = "uploadFile", required = false) MultipartFile file, ModelMap model) {
        WebErrors errors = validateUploadFile(filename, file);
        if (errors.hasErrors()) {
            model.addAttribute(ERROR, errors.getErrors().get(0));
            return FILE_UPLOAD_RESULT_PAGE;
        }
        String origName = file.getOriginalFilename();
        String ext = FileUtil.getExtension(origName).toLowerCase(Locale.ENGLISH);
        String fileUri;
        try {
            fileUri = fileRepository.storeByExt(Constants.getQmsSourceUriPrefix(), ext, file.getBytes());
            model.addAttribute("uploadPath", CommonUtil.getResourceUrl(fileUri));
            model.addAttribute("uploadNum", uploadNum);
        } catch (IOException e) {
            model.addAttribute(ERROR, e.getMessage());
            log.error("upload file error!", e);
        }
        return FILE_UPLOAD_RESULT_PAGE;
    }

    @RequestMapping("/common/v_image_area_select.jhtml")
    public String imageAreaSelect(String uploadBase, String imgSrcPath, Integer zoomWidth, Integer zoomHeight,
            Integer uploadNum, ModelMap model) {
        model.addAttribute("uploadBase", uploadBase);
        model.addAttribute("imgSrcPath", imgSrcPath);
        model.addAttribute("zoomWidth", zoomWidth);
        model.addAttribute("zoomHeight", zoomHeight);
        model.addAttribute("uploadNum", uploadNum);
        return IMAGE_SELECT_RESULT;
    }

    @RequestMapping("/common/o_image_cut.jhtml")
    public String imageCut(String imgSrcPath, Integer imgTop, Integer imgLeft, Integer imgWidth, Integer imgHeight,
            Integer reMinWidth, Integer reMinHeight, Float imgScale, Integer uploadNum, HttpServletRequest request,
            ModelMap model) {
        try {
            imgSrcPath = imgSrcPath.substring(Constants.getResourceUrlPrefix().length());
            File file = new File(Constants.getResourceUriPrefix() + imgSrcPath);
            if (imgWidth > 0) {
                imageScaleAverage.resizeFix(file, file, reMinWidth, reMinHeight, getLen(imgTop, imgScale),
                        getLen(imgLeft, imgScale), getLen(imgWidth, imgScale), getLen(imgHeight, imgScale));
            } else {
                imageScaleAverage.resizeFix(file, file, reMinWidth, reMinHeight);
            }
            model.addAttribute("uploadNum", uploadNum);
        } catch (Exception e) {
            log.error("cut image error", e);
            model.addAttribute(ERROR, e.getMessage());
        }
        return IMAGE_CUT_RESULT;
    }

    @RequestMapping("/common/o_upload_image.jhtml")
    public String imageUpload(String filename, Integer uploadNum,
            @RequestParam(value = "uploadFile", required = false) MultipartFile file, ModelMap model) {
        WebErrors errors = validateUploadImage(filename, file);
        if (errors.hasErrors()) {
            model.addAttribute(ERROR, errors.getErrors().get(0));
            return IMAGE_UPLOAD_RESULT_PAGE;
        }
        String origName = file.getOriginalFilename();
        String ext = FileUtil.getExtension(origName).toLowerCase(Locale.ENGLISH);
        String fileUri;
        try {
            fileUri = fileRepository.storeByExt(Constants.getQmsSourceUriPrefix(), ext, file.getBytes());
            model.addAttribute("uploadPath", CommonUtil.getResourceUrl(fileUri));
            model.addAttribute("uploadNum", uploadNum);
        } catch (IOException e) {
            model.addAttribute(ERROR, e.getMessage());
            log.error("upload file error!", e);
        }
        return IMAGE_UPLOAD_RESULT_PAGE;
    }

    @RequestMapping("/common/o_upload_media.jhtml")
    public String mediaUpload(String filename, Integer uploadNum,
            @RequestParam(value = "uploadFile", required = false) MultipartFile file, ModelMap model) {
        WebErrors errors = validateUploadMedia(filename, file);
        if (errors.hasErrors()) {
            model.addAttribute(ERROR, errors.getErrors().get(0));
            return MEDIA_UPLOAD_RESULT_PAGE;
        }
        String origName = file.getOriginalFilename();
        String ext = FileUtil.getExtension(origName).toLowerCase(Locale.ENGLISH);
        String fileUri;
        try {
            fileUri = fileRepository.storeByExt(Constants.getQmsSourceUriPrefix(), ext, file.getBytes());
            model.addAttribute("uploadPath", CommonUtil.getResourceUrl(fileUri));
            model.addAttribute("uploadNum", uploadNum);
        } catch (IOException e) {
            model.addAttribute(ERROR, e.getMessage());
            log.error("upload file error!", e);
        }
        return MEDIA_UPLOAD_RESULT_PAGE;
    }

    private int getLen(int len, float imgScale) {
        return Math.round(len / imgScale);
    }

    /**
     * @param ext 文件后缀
     * @return 是否是允许的文件
     */
    private boolean isValidFileExt(String ext) {
        ext = ext.toLowerCase(Locale.ENGLISH);
        for (String s : FILE_EXT) {
            if (s.equalsIgnoreCase(ext)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param ext 文件后缀
     * @return 是否是允许的媒体文件
     */
    private boolean isValidMediaExt(String ext) {
        ext = ext.toLowerCase(Locale.ENGLISH);
        for (String s : MEDIA_EXT) {
            if (s.equalsIgnoreCase(ext)) {
                return true;
            }
        }
        return false;
    }

    private WebErrors validateUploadFile(String filename, MultipartFile file) {
        WebErrors errors = WebErrors.create();
        if (null == file) {
            errors.addError("请先选择文件");
            return errors;
        }
        if (StringUtils.isBlank(filename)) {
            filename = file.getOriginalFilename();
        }
        String ext = FileUtil.getExtension(filename);
        if (!isValidFileExt(ext)) {
            errors.addError(String.format("不支持的文件后缀: %s", ext));
            return errors;
        }
        return errors;
    }

    private WebErrors validateUploadImage(String filename, MultipartFile file) {
        WebErrors errors = WebErrors.create();
        if (null == file) {
            errors.addError("请先选择图片");
            return errors;
        }
        if (StringUtils.isBlank(filename)) {
            filename = file.getOriginalFilename();
        }
        String ext = FileUtil.getExtension(filename);
        if (!ImageUtil.isValidImageExt(ext)) {
            errors.addError(String.format("不支持的图片后缀: %s", ext));
            return errors;
        }
        try {
            if (!ImageUtil.isImage(file.getInputStream())) {
                errors.addError("图片格式不支持");
                return errors;
            }
        } catch (IOException e) {
            log.error("image upload error", e);
            errors.addError("IO错误");
            return errors;
        }
        return errors;
    }

    private WebErrors validateUploadMedia(String filename, MultipartFile file) {
        WebErrors errors = WebErrors.create();
        if (null == file) {
            errors.addError("请先选择文件");
            return errors;
        }
        if (StringUtils.isBlank(filename)) {
            filename = file.getOriginalFilename();
        }
        String ext = FileUtil.getExtension(filename);
        if (!isValidMediaExt(ext)) {
            errors.addError(String.format("不支持的文件后缀: %s", ext));
            return errors;
        }
        return errors;
    }
}
