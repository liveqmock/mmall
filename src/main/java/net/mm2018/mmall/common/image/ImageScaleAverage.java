package net.mm2018.mmall.common.image;

import cn.kxai.common.lang.FileUtil;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 图片压缩类,使用java方型区域颜色平均算法.
 * User: Cosmo<cosmo.wang@kaixinai.com>
 * Date: 12-4-12
 * Time: 下午12:56
 */
@Component
public class ImageScaleAverage {
    /**
     * 缩小图片
     *
     * @param srcFile   原图片
     * @param destFile  目标图片
     * @param boxWidth  缩略图最大宽度
     * @param boxHeight 缩略图最大高度
     * @throws java.io.IOException
     */
    public void resizeFix(File srcFile, File destFile, int boxWidth, int boxHeight) throws IOException {
        BufferedImage srcImgBuff = ImageIO.read(srcFile);
        int width = srcImgBuff.getWidth();
        int height = srcImgBuff.getHeight();
        if (width <= boxWidth && height <= boxHeight) {
            FileUtil.copyFile(srcFile, destFile);
            return;
        }
        int zoomWidth;
        int zoomHeight;
        if ((float) width / height > (float) boxWidth / boxHeight) {
            zoomWidth = boxWidth;
            zoomHeight = Math.round((float) boxWidth * height / width);
        } else {
            zoomWidth = Math.round((float) boxHeight * width / height);
            zoomHeight = boxHeight;
        }
        BufferedImage imgBuff = scaleImage(srcImgBuff, width, height, zoomWidth, zoomHeight);
        writeFile(imgBuff, destFile);
    }

    /**
     * 裁剪并压缩
     *
     * @param srcFile   原文件
     * @param destFile  目标文件
     * @param boxWidth  缩略图最大宽度
     * @param boxHeight 缩略图最大高度
     * @param cutTop    裁剪TOP
     * @param cutLeft   裁剪LEFT
     * @param cutWidth  裁剪宽度
     * @param catHeight 裁剪高度
     * @throws java.io.IOException
     */
    public void resizeFix(File srcFile, File destFile, int boxWidth, int boxHeight, int cutTop, int cutLeft,
            int cutWidth, int catHeight) throws IOException {
        BufferedImage srcImgBuff = ImageIO.read(srcFile);
        srcImgBuff = srcImgBuff.getSubimage(cutTop, cutLeft, cutWidth, catHeight);
        int width = srcImgBuff.getWidth();
        int height = srcImgBuff.getHeight();
        if (width <= boxWidth && height <= boxHeight) {
            writeFile(srcImgBuff, destFile);
            return;
        }
        int zoomWidth;
        int zoomHeight;
        if ((float) width / height > (float) boxWidth / boxHeight) {
            zoomWidth = boxWidth;
            zoomHeight = Math.round((float) boxWidth * height / width);
        } else {
            zoomWidth = Math.round((float) boxHeight * width / height);
            zoomHeight = boxHeight;
        }
        BufferedImage imgBuff = scaleImage(srcImgBuff, width, height, zoomWidth, zoomHeight);
        writeFile(imgBuff, destFile);
    }

    public static void writeFile(BufferedImage imgBuf, File destFile) throws IOException {
        File parent = destFile.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }
        ImageIO.write(imgBuf, "jpeg", destFile);
    }

    private static BufferedImage scaleImage(BufferedImage srcImgBuff, int width, int height, int zoomWidth,
            int zoomHeight) {
        int[] colorArray = srcImgBuff.getRGB(0, 0, width, height, null, 0, width);
        BufferedImage outBuff = new BufferedImage(zoomWidth, zoomHeight, BufferedImage.TYPE_INT_RGB);
        // 宽缩小的倍数
        float wScale = (float) width / zoomWidth;
        int wScaleInt = (int) (wScale + 0.5);
        // 高缩小的倍数
        float hScale = (float) height / zoomHeight;
        int hScaleInt = (int) (hScale + 0.5);
        int area = wScaleInt * hScaleInt;
        int x0, x1, y0, y1;
        int color;
        long red, green, blue;
        int x, y, i, j;
        for (y = 0; y < zoomHeight; y++) {
            // 得到原图高的Y坐标
            y0 = (int) (y * hScale);
            y1 = y0 + hScaleInt;
            for (x = 0; x < zoomWidth; x++) {
                x0 = (int) (x * wScale);
                x1 = x0 + wScaleInt;
                red = green = blue = 0;
                for (i = x0; i < x1; i++) {
                    for (j = y0; j < y1; j++) {
                        color = colorArray[width * j + i];
                        red += getRedValue(color);
                        green += getGreenValue(color);
                        blue += getBlueValue(color);
                    }
                }
                outBuff.setRGB(x, y, comRGB((int) (red / area), (int) (green / area), (int) (blue / area)));
            }
        }
        return outBuff;
    }

    private static int getRedValue(int rgbValue) {
        return (rgbValue & 0x00ff0000) >> 16;
    }

    private static int getGreenValue(int rgbValue) {
        return (rgbValue & 0x0000ff00) >> 8;
    }

    private static int getBlueValue(int rgbValue) {
        return rgbValue & 0x000000ff;
    }

    private static int comRGB(int redValue, int greenValue, int blueValue) {
        return (redValue << 16) + (greenValue << 8) + blueValue;
    }

    //    public static void main(String[] args) throws Exception {
    //        long time = System.currentTimeMillis();
    //        ImageScaleAverageImpl.resizeFix(new File("D:/1.bmp"),
    //                new File("D:/1-n-2.bmp"), 310, 310, 50, 50, 320, 320);
    //        time = System.currentTimeMillis() - time;
    //        System.out.println("resize2 img in " + time + "ms");
    //    }
}