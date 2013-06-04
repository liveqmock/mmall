/**
 * ImageUtil.java
 *
 * Copyright 2012 lemon sea, Inc. All Rights Reserved.
 */
package net.mm2018.mmall.common.image;

import java.io.InputStream;
import java.util.Locale;

/**
 * 图片工具类.
 * User: Cosmo<cosmo.wang@kaixinai.com>
 * Date: 12-4-12
 * Time: 下午12:56
 */
public final class ImageUtil {

    /** 图片的后缀 */
    private static final String[] IMAGE_EXT = new String[]{"jpg", "jpeg", "gif", "png", "bmp"};

    /**
     * 是否是图片
     *
     * @param ext 文件后缀
     * @return "jpg", "jpeg", "gif", "png", "bmp" 为文件后缀名者为图片
     */
    public static boolean isValidImageExt(String ext) {
        ext = ext.toLowerCase(Locale.ENGLISH);
        for (String s : IMAGE_EXT) {
            if (s.equalsIgnoreCase(ext)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the underlying input stream contains an image.
     *
     * @param in input stream of an image
     * @return <code>true</code> if the underlying input stream contains an image, else <code>false</code>
     */
    public static boolean isImage(InputStream in) {
        ImageInfo ii = new ImageInfo();
        ii.setInput(in);
        return ii.check();
    }

    private ImageUtil() {
    }
}
