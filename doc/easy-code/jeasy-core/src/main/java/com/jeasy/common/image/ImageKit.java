package com.jeasy.common.image;

import com.jeasy.common.Func;
import com.jeasy.common.charset.CharsetKit;
import com.jeasy.common.io.IoKit;
import com.jeasy.common.str.StrKit;
import com.jeasy.common.support.IMultiOutputStream;
import com.jeasy.exception.KitException;
import magick.CompositeOperator;
import magick.ImageInfo;
import magick.MagickException;
import magick.MagickImage;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.MemoryCacheImageInputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.io.*;
import java.net.URL;
import java.util.Iterator;

/**
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
public final class ImageKit {

    /**
     * 默认输出图片类型
     */
    public static final String DEFAULT_IMG_TYPE = "JPEG";
    public static final double DOUBLE_1_5 = 1.5;
    public static final int NUM_TEN = 10;
    public static final int NUM_2 = 2;

    private ImageKit() {

    }

    /**
     * 转换输入流到byte
     *
     * @param src
     * @param type
     * @return
     * @throws IOException
     */
    public static byte[] toByteArray(BufferedImage src, String type) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ImageIO.write(src, defaultString(type, DEFAULT_IMG_TYPE), os);
            return os.toByteArray();
        } catch (IOException e) {
            throw new KitException(e);
        } finally {
            IoKit.close(os);
        }
    }

    /**
     * 获取图像内容
     *
     * @param srcImageFile 文件路径
     * @return
     */
    public static BufferedImage readImage(String srcImageFile) {
        try {
            return ImageIO.read(new File(srcImageFile));
        } catch (IOException e) {
            throw new KitException(e);
        }
    }

    /**
     * 获取图像内容
     *
     * @param srcImageFile 文件
     * @return
     */
    public static BufferedImage readImage(File srcImageFile) {
        try {
            return ImageIO.read(srcImageFile);
        } catch (IOException e) {
            throw new KitException(e);
        }
    }

    /**
     * 获取图像内容
     *
     * @param srcInputStream 输入流
     * @return
     */
    public static BufferedImage readImage(InputStream srcInputStream) {
        try {
            return ImageIO.read(srcInputStream);
        } catch (IOException e) {
            throw new KitException(e);
        }
    }

    /**
     * 获取图像内容
     *
     * @param url URL地址
     * @return
     */
    public static BufferedImage readImage(URL url) {
        try {
            return ImageIO.read(url);
        } catch (IOException e) {
            throw new KitException(e);
        }
    }


    /**
     * 缩放图像（按比例缩放）
     *
     * @param src    源图像
     * @param output 输出流
     * @param scale  缩放比例
     * @param flag   缩放选择:true 放大; false 缩小;
     */
    public static final void zoomScale(BufferedImage src, OutputStream output, String type, double scale, boolean flag) {
        try {
            // 得到源图宽
            int width = src.getWidth();
            // 得到源图长
            int height = src.getHeight();
            if (flag) {
                // 放大
                width = Long.valueOf(Math.round(width * scale)).intValue();
                height = Long.valueOf(Math.round(height * scale)).intValue();
            } else {
                // 缩小
                width = Long.valueOf(Math.round(width / scale)).intValue();
                height = Long.valueOf(Math.round(height / scale)).intValue();
            }
            Image image = src.getScaledInstance(width, height, Image.SCALE_DEFAULT);
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            // 绘制缩小后的图
            g.drawImage(image, 0, 0, null);
            g.dispose();
            // 输出为文件
            ImageIO.write(tag, defaultString(type, DEFAULT_IMG_TYPE), output);
        } catch (IOException e) {
            throw new KitException(e);
        }
    }

    /**
     * 缩放图像（按高度和宽度缩放）
     *
     * @param src       源图像
     * @param output    输出流
     * @param height    缩放后的高度
     * @param width     缩放后的宽度
     * @param bb        比例不对时是否需要补白：true为补白; false为不补白;
     * @param fillColor 填充色，null时为Color.WHITE
     */
    public static final void zoomFixed(BufferedImage src, OutputStream output, String type, int height, int width, boolean bb, Color fillColor) {
        try {
            // 缩放比例
            double ratio = 0.0;
            Image itemp = src.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);
            // 计算比例
            if (src.getHeight() > src.getWidth()) {
                ratio = Integer.valueOf(height).doubleValue() / src.getHeight();
            } else {
                ratio = Integer.valueOf(width).doubleValue() / src.getWidth();
            }
            AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);
            itemp = op.filter(src, null);

            if (bb) {
                //补白
                BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                Graphics2D g = image.createGraphics();
                Color fill = fillColor == null ? Color.white : fillColor;
                g.setColor(fill);
                g.fillRect(0, 0, width, height);
                if (width == itemp.getWidth(null)) {
                    g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / NUM_2, itemp.getWidth(null), itemp.getHeight(null), fill, null);
                } else {
                    g.drawImage(itemp, (width - itemp.getWidth(null)) / NUM_2, 0, itemp.getWidth(null), itemp.getHeight(null), fill, null);
                }
                g.dispose();
                itemp = image;
            }
            // 输出为文件
            ImageIO.write((BufferedImage) itemp, defaultString(type, DEFAULT_IMG_TYPE), output);
        } catch (IOException e) {
            throw new KitException(e);
        }
    }

    /**
     * 图像裁剪(按指定起点坐标和宽高切割)
     *
     * @param src    源图像
     * @param output 切片后的图像地址
     * @param x      目标切片起点坐标X
     * @param y      目标切片起点坐标Y
     * @param width  目标切片宽度
     * @param height 目标切片高度
     */
    public static final void crop(BufferedImage src, OutputStream output, String type, int x, int y, int width, int height) {
        try {
            // 源图宽度
            int srcWidth = src.getHeight();
            // 源图高度
            int srcHeight = src.getWidth();
            if (srcWidth > 0 && srcHeight > 0) {
                Image image = src.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);
                // 四个参数分别为图像起点坐标和宽高
                ImageFilter cropFilter = new CropImageFilter(x, y, width, height);
                Image img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(), cropFilter));
                BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                Graphics g = tag.getGraphics();
                // 绘制切割后的图
                g.drawImage(img, 0, 0, width, height, null);
                g.dispose();
                // 输出为文件
                ImageIO.write(tag, defaultString(type, DEFAULT_IMG_TYPE), output);
            }
        } catch (Exception e) {
            throw new KitException(e);
        }
    }

    /**
     * 图像切割（指定切片的行数和列数）
     *
     * @param src   源图像地址
     * @param mos   切片目标文件夹
     * @param prows 目标切片行数。默认2，必须是范围 [1, 20] 之内
     * @param pcols 目标切片列数。默认2，必须是范围 [1, 20] 之内
     */
    public static final void sliceWithNumber(BufferedImage src, IMultiOutputStream mos, String type, int prows, int pcols) {
        try {
            int rows = prows <= 0 || prows > 20 ? NUM_2 : prows;
            int cols = pcols <= 0 || pcols > 20 ? NUM_2 : pcols;
            // 源图宽度
            int srcWidth = src.getHeight();
            // 源图高度
            int srcHeight = src.getWidth();
            if (srcWidth > 0 && srcHeight > 0) {
                Image img;
                ImageFilter cropFilter;
                Image image = src.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);
                // 每张切片的宽度
                int destWidth = (srcWidth % cols == 0) ? (srcWidth / cols) : (srcWidth / cols + 1);
                // 每张切片的高度
                int destHeight = (srcHeight % rows == 0) ? (srcHeight / rows) : (srcHeight / rows + 1);
                // 循环建立切片
                // 改进的想法:是否可用多线程加快切割速度
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        // 四个参数分别为图像起点坐标和宽高
                        cropFilter = new CropImageFilter(j * destWidth, i * destHeight, destWidth, destHeight);
                        img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(), cropFilter));
                        BufferedImage tag = new BufferedImage(destWidth, destHeight, BufferedImage.TYPE_INT_RGB);
                        Graphics g = tag.getGraphics();
                        // 绘制缩小后的图
                        g.drawImage(img, 0, 0, null);
                        g.dispose();
                        // 输出为文件
                        ImageIO.write(tag, defaultString(type, DEFAULT_IMG_TYPE), mos.buildOutputStream(i, j));
                    }
                }
            }
        } catch (Exception e) {
            throw new KitException(e);
        }
    }

    /**
     * 图像切割（指定切片的宽度和高度）
     *
     * @param src         源图像地址
     * @param mos         切片目标文件夹
     * @param pdestWidth  目标切片宽度。默认200
     * @param pdestHeight 目标切片高度。默认150
     */
    public static final void sliceWithSize(BufferedImage src, IMultiOutputStream mos, String type, int pdestWidth, int pdestHeight) {
        try {
            int destWidth = pdestWidth <= 0 ? 200 : pdestWidth;
            int destHeight = pdestHeight <= 0 ? 150 : pdestHeight;
            // 源图宽度
            int srcWidth = src.getHeight();
            // 源图高度
            int srcHeight = src.getWidth();
            if (srcWidth > destWidth && srcHeight > destHeight) {
                Image img;
                ImageFilter cropFilter;
                Image image = src.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);
                // 切片横向数量
                int cols = (srcWidth % destWidth == 0) ? (srcWidth / destWidth) : (srcWidth / destWidth + 1);
                // 切片纵向数量
                int rows = (srcHeight % destHeight == 0) ? (srcHeight / destHeight) : (srcHeight / destHeight + 1);
                // 循环建立切片
                // 改进的想法:是否可用多线程加快切割速度
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        // 四个参数分别为图像起点坐标和宽高
                        cropFilter = new CropImageFilter(j * destWidth, i * destHeight, destWidth, destHeight);
                        img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(), cropFilter));
                        BufferedImage tag = new BufferedImage(destWidth, destHeight, BufferedImage.TYPE_INT_RGB);
                        Graphics g = tag.getGraphics();
                        // 绘制缩小后的图
                        g.drawImage(img, 0, 0, null);
                        g.dispose();
                        // 输出为文件
                        ImageIO.write(tag, defaultString(type, DEFAULT_IMG_TYPE), mos.buildOutputStream(i, j));
                    }
                }
            }
        } catch (Exception e) {
            throw new KitException(e);
        }
    }

    /**
     * 图像类型转换：GIF->JPG、GIF->PNG、PNG->JPG、PNG->GIF(X)、BMP->PNG
     *
     * @param src        源图像地址
     * @param formatName 包含格式非正式名称的 String：如JPG、JPEG、GIF等
     * @param output     目标图像地址
     */
    public static final void convert(BufferedImage src, OutputStream output, String formatName) {
        try {
            // 输出为文件
            ImageIO.write(src, formatName, output);
        } catch (Exception e) {
            throw new KitException(e);
        }
    }

    /**
     * 彩色转为黑白
     *
     * @param src    源图像地址
     * @param output 目标图像地址
     */
    public static final void gray(BufferedImage src, OutputStream output, String type) {
        try {
            ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
            ColorConvertOp op = new ColorConvertOp(cs, null);
            src = op.filter(src, null);
            // 输出为文件
            ImageIO.write(src, defaultString(type, DEFAULT_IMG_TYPE), output);
        } catch (IOException e) {
            throw new KitException(e);
        }
    }

    /**
     * 给图片添加文字水印
     *
     * @param src      源图像
     * @param output   输出流
     * @param text     水印文字
     * @param font     水印的字体
     * @param color    水印的字体颜色
     * @param position 水印位置 {@link ImagePosition}
     * @param x        修正值
     * @param y        修正值
     * @param alpha    透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     */
    public static final void textStamp(BufferedImage src, OutputStream output, String type, String text, Font font, Color color, int position, int x, int y, float alpha) {
        try {
            int width = src.getWidth(null);
            int height = src.getHeight(null);
            BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();
            g.drawImage(src, 0, 0, width, height, null);
            g.setColor(color);
            g.setFont(font);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            // 在指定坐标绘制水印文字
            ImagePosition boxPos = new ImagePosition(width, height, calcTextWidth(text) * font.getSize(), font.getSize(), position);
            g.drawString(text, boxPos.getX(x), boxPos.getY(y));
            g.dispose();
            // 输出为文件
            ImageIO.write((BufferedImage) image, defaultString(type, DEFAULT_IMG_TYPE), output);
        } catch (Exception e) {
            throw new KitException(e);
        }
    }

    /**
     * 给图片添加图片水印
     *
     * @param src      源图像
     * @param output   输出流
     * @param stamp    水印图片
     * @param position 水印位置 {@link ImagePosition}
     * @param x        修正值
     * @param y        修正值
     * @param alpha    透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     */
    public static final void imageStamp(BufferedImage src, OutputStream output, String type, BufferedImage stamp, int position, int x, int y, float alpha) {
        try {
            int width = src.getWidth();
            int height = src.getHeight();
            BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();
            g.drawImage(src, 0, 0, width, height, null);
            // 水印文件
            int stampWidth = stamp.getWidth();
            int stampHeight = stamp.getHeight();
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            ImagePosition boxPos = new ImagePosition(width, height, stampWidth, stampHeight, position);
            g.drawImage(stamp, boxPos.getX(x), boxPos.getY(y), stampWidth, stampHeight, null);
            // 水印文件结束
            g.dispose();
            // 输出为文件
            ImageIO.write((BufferedImage) image, defaultString(type, DEFAULT_IMG_TYPE), output);
        } catch (Exception e) {
            throw new KitException(e);
        }
    }

    /**
     * 计算text的长度（一个中文算两个字符）
     *
     * @param text
     * @return
     */
    private static int calcTextWidth(String text) {
        int length = 0;
        for (int i = 0; i < text.length(); i++) {
            try {
                byte[] bytes = String.valueOf(text.charAt(i)).getBytes(CharsetKit.DEFAULT_ENCODE);
                if (bytes.length > 1) {
                    length += NUM_2;
                } else {
                    length += 1;
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return length / NUM_2;
    }

    private static String defaultString(String str, String defaultStr) {
        return ((str == null) ? defaultStr : str);
    }

    public static MagickImage regulate(MagickImage source, String destPathName) throws MagickException {
        int width = (int) source.getDimension().getWidth();
        int height = (int) source.getDimension().getHeight();
        if ((height + 0.0) / width > DOUBLE_1_5) {
            MagickImage image = null;
            MagickImage scaled;
            try {
                int newHeight = (int) Math.round(width * DOUBLE_1_5);
                Rectangle rect = new Rectangle(0, (height - newHeight) / NUM_2, width, newHeight);

                scaled = source.cropImage(rect);
                scaled.setFileName(destPathName);
                return scaled;
            } finally {
                if (Func.isNotEmpty(source)) {
                    source.destroyImages();
                }

            }
        } else {
            // System.out.println("--------------regulate.source:width:"+width+"|height:"+height)
            // ;
            return source;
        }
    }

    /**
     * 按照1：1.5宽高比裁切，如果进行了裁切，则原图对象被销毁
     *
     * @param source
     * @return
     * @throws MagickException
     */
    public static MagickImage regulate(MagickImage source) throws MagickException {
        return regulate(source, true);
    }

    /**
     * 按照1:1.5宽高比裁切，可以指定是否销毁原始对象
     *
     * @param source
     * @param destroySource
     * @return
     * @throws MagickException
     */
    public static MagickImage regulate(MagickImage source, boolean destroySource)
        throws MagickException {
        int width = (int) source.getDimension().getWidth();
        int height = (int) source.getDimension().getHeight();
        if ((height + 0.0) / width > DOUBLE_1_5) {
            MagickImage image = null;
            MagickImage scaled = null;
            try {
                int newHeight = (int) Math.round(width * DOUBLE_1_5);
                Rectangle rect = new Rectangle(0, (height - newHeight) / NUM_2, width, newHeight);

                scaled = source.cropImage(rect);
                return scaled;
            } finally {
                if (destroySource) {
                    source.destroyImages();
                }
            }
        } else {
            // System.out.println("--------------regulate.source:width:"+width+"|height:"+height)
            // ;
            return source;
        }
    }

    /**
     * 给尺寸不足的边补白，source会被destroy
     *
     * @param source
     * @param minWidth
     * @param minHeight
     * @return
     * @throws MagickException
     */
    public static MagickImage regulateWithMinLimit(MagickImage source, int minWidth, int minHeight)
        throws MagickException {
        return regulateWithMinLimit(source, minWidth, minHeight, true);
    }

    /**
     * 给尺寸不足的边补白
     *
     * @param source
     * @param minWidth
     * @param minHeight
     * @param destroyImage
     * @return
     * @throws MagickException
     */
    public static MagickImage regulateWithMinLimit(MagickImage source, int minWidth, int minHeight,
                                                   boolean destroyImage) throws MagickException {
        int width = (int) source.getDimension().getWidth();
        int height = (int) source.getDimension().getHeight();

        if (width < minWidth && height < minHeight) {
            MagickImage img = null;
            byte[] pixels = new byte[minWidth * minHeight * 4];
            for (int i = 0; i < minWidth * minHeight; i++) {
                pixels[4 * i] = (byte) 255;
                pixels[4 * i + 1] = (byte) 255;
                pixels[4 * i + NUM_2] = (byte) 255;
                pixels[4 * i + 3] = (byte) 0;
            }
            img = new MagickImage();
            img.constituteImage(minWidth, minHeight, "RGBA", pixels);
            img.compositeImage(CompositeOperator.ModulusAddCompositeOp, source, (minWidth - width) / NUM_2,
                (minHeight - height) / NUM_2);
            if (destroyImage) {
                source.destroyImages();
            }
            return img;
        } else if (width < minWidth) {
            return regulateWithRatio(source, minWidth, height, destroyImage);
        } else if (height < minHeight) {
            return regulateWithRatio(source, width, minHeight, destroyImage);
        }

        return source;
    }

    /**
     * 按照限定宽度缩放，超过1.5高的部分会被切掉，如果宽度不足，不会按照该宽度的1.5倍高度裁切 而是限定宽度的1.5倍，这是和
     * {@link #regulate(MagickImage)}不同的地方
     *
     * @param source
     * @param maxWidth
     * @param destroySource 是否销毁source对象
     * @return
     * @throws MagickException
     */
    public static MagickImage regulateWithMaxWidth(MagickImage source, int maxWidth,
                                                   boolean destroySource) throws MagickException {
        int width = (int) source.getDimension().getWidth();
        int height = (int) source.getDimension().getHeight();

        if (Float.parseFloat(String.valueOf(height)) / Float.parseFloat(String.valueOf(width)) > DOUBLE_1_5) {
            if (width < maxWidth) {
                // 宽度比限定宽度小
                int y = (int) ((height - maxWidth * DOUBLE_1_5) / NUM_2);
                int h = (int) (maxWidth * DOUBLE_1_5);
                if (h < height) {
                    // 如果高度超过限定宽度的1.5倍
                    Rectangle rect = new Rectangle(0, y, width, h);
                    if (destroySource) {
                        source.destroyImages();
                    }
                    return source.cropImage(rect);
                } else {
                    return source;
                }
            }
        }
        MagickImage img = null;
        try {
            // 按比例处理
            img = regulate(source, false);
            return resizePhotoStep(img, img.getFileName(), maxWidth, (int) (maxWidth * DOUBLE_1_5));
        } finally {
            if (destroySource) {
                source.destroyImages();
            }
        }
    }

    /**
     * 按照比例对尺寸不足的边补白，销毁source
     *
     * @param source
     * @param ratioWidth
     * @param ratioHeight
     * @return
     * @throws MagickException
     */
    public static MagickImage regulateWithRatio(MagickImage source, int ratioWidth, int ratioHeight)
        throws MagickException {
        return regulateWithRatio(source, ratioWidth, ratioHeight, true);
    }

    /**
     * 按照比例对尺寸不足的边补白
     *
     * @param source
     * @param ratioWidth
     * @param ratioHeight
     * @param destroySource
     * @return
     * @throws MagickException
     */
    public static MagickImage regulateWithRatio(MagickImage source, int ratioWidth,
                                                int ratioHeight, boolean destroySource) throws MagickException {
        int width = (int) source.getDimension().getWidth();
        int height = (int) source.getDimension().getHeight();

        boolean okay = true;
        int newh = height, neww = width;
        int x = 0, y = 0;
        System.out.println(String.format("%d, %d", width, height));
        if ((width / (float) height) > (ratioWidth / (float) ratioHeight)) {
            // 比例不协调，宽
            newh = width * ratioHeight / ratioWidth;
            y = (newh - height) / NUM_2;
            okay = false;
            System.out.println("w>h");
        } else if ((width / (float) height < ratioWidth / (float) ratioHeight)) {
            // 比例不协调，高
            neww = height * ratioWidth / ratioHeight;
            x = (neww - width) / NUM_2;
            okay = false;
            System.out.println("h>w");
        }
        MagickImage img = null;
        if (!okay) {
            System.out.println(String.format("%d, %d", neww, newh));
            byte[] pixels = new byte[neww * newh * 4];
            for (int i = 0; i < neww * newh; i++) {
                pixels[4 * i] = (byte) 255;
                pixels[4 * i + 1] = (byte) 255;
                pixels[4 * i + NUM_2] = (byte) 255;
                pixels[4 * i + 3] = (byte) 0;
            }
            img = new MagickImage();
            img.constituteImage(neww, newh, "RGBA", pixels);
            img.compositeImage(CompositeOperator.ModulusAddCompositeOp, source, x, y);
            if (destroySource) {
                source.destroyImages();
            }
            return img;

        }

        return source;
    }

    public static MagickImage resizePhotoStep(MagickImage source, String destPathName,
                                              int maxWidth, int maxHeight) throws MagickException {
        int width = 0;
        int height = 0;
        boolean change = true;
        width = (int) source.getDimension().getWidth();
        height = (int) source.getDimension().getHeight();
        if (maxWidth > width && maxHeight > height) {
            change = false;
        } else {
            if (width > 0 && height > 0) {
                if (height / width > maxHeight / maxWidth) {
                    width = width * maxHeight / height;
                    height = maxHeight;
                } else {
                    height = height * maxWidth / width;
                    width = maxWidth;
                }
            }
        }

        MagickImage scaled = null;

        scaled = source.zoomImage(width, height);
        scaled.setFileName(destPathName);
        return scaled;

    }

    public static MagickImage setcomment(MagickImage image, String comment) throws MagickException {
        image.profileImage("*", null);
        image.setImageAttribute("comment", comment);
        return image;
    }

    public static void convertBmpToJpg(String srcPathName, String destPathName)
        throws MagickException {
        MagickImage image = null;
        try {
            ImageInfo info = null;
            info = new ImageInfo();
            image = new MagickImage(new ImageInfo(srcPathName + "[0]"));
            image.setFileName(destPathName);
            image.writeImage(info);
        } finally {
            if (image != null) {
                image.destroyImages();
            }
        }
    }

    public static void resize(String srcPathName, String destPathName, int maxWidth, int maxHeight)
        throws MagickException {
        int width = 0;
        int height = 0;
        boolean change = true;
        width = getWidth(srcPathName);
        height = getHeight(srcPathName);
        // if (maxWidth > width && maxHeight > height) {
        // change = false;
        // } else {
        if (width > 0 && height > 0) {
            if (height / width > maxHeight / maxWidth) {
                width = width * maxHeight / height;
                height = maxHeight;
            } else {
                height = height * maxWidth / width;
                width = maxWidth;
            }
        }
        // }

        MagickImage image = null;
        MagickImage scaled = null;
        try {
            ImageInfo info = null;
            info = new ImageInfo();
            image = new MagickImage(new ImageInfo(srcPathName + "[0]"));
            if (change) {

                scaled = image.scaleImage(width, height);
            } else {
                scaled = image;
            }
            scaled.setFileName(destPathName);
            scaled.writeImage(info);

        } finally {
            if (image != null) {
                image.destroyImages();
            }
            if (scaled != null) {
                scaled.destroyImages();
            }
        }
    }

    public static void resizePhoto(String srcPathName, String destPathName, int maxWidth,
                                   int maxHeight) throws MagickException {
        int width = 0;
        int height = 0;
        boolean change = true;
        width = getWidth(srcPathName);
        height = getHeight(srcPathName);
        if (maxWidth > width && maxHeight > height) {
            change = false;
        } else {
            if (width > 0 && height > 0) {
                if (height / width > maxHeight / maxWidth) {
                    width = width * maxHeight / height;
                    height = maxHeight;
                } else {
                    height = height * maxWidth / width;
                    width = maxWidth;
                }
            }
        }

        MagickImage image = null;
        MagickImage scaled = null;
        try {
            ImageInfo info = null;
            info = new ImageInfo();
            image = new MagickImage(new ImageInfo(srcPathName + "[0]"));
            if (change) {
                scaled = image.scaleImage(width, height);
            } else {
                scaled = image;
            }
            scaled.setFileName(destPathName);
            scaled.writeImage(info);
        } finally {
            if (image != null) {
                image.destroyImages();
            }
            if (scaled != null) {
                scaled.destroyImages();
            }
        }
    }

    public static void setcomment(String srcPathName, String comment) throws MagickException {
        ImageInfo info = null;
        info = new ImageInfo();
        MagickImage image = null;
        try {
            image = new MagickImage(new ImageInfo(srcPathName + "[0]"));
            image.profileImage("*", null);
            image.setImageAttribute("comment", comment);
            image.writeImage(info);
        } finally {
            if (image != null) {
                image.destroyImages();
            }
        }
    }

    public static boolean checkcomment(MagickImage source, String comment) {

        try {
            String cmt = StrKit.S_EMPTY;
            cmt = source.getImageAttribute("comment");
            if (cmt != null) {
                if (cmt.startsWith(getCommentProfile()) && !comment.equals(cmt)) {
                    return false;
                }
            }
        } catch (Exception e) {

        }
        return true;

    }

    public static boolean checkcomment(String srcPathName, String comment) throws MagickException {
        MagickImage image = null;
        try {
            image = new MagickImage(new ImageInfo(srcPathName + "[0]"));
            String cmt = StrKit.S_EMPTY;
            cmt = image.getImageAttribute("comment");
            if (cmt != null) {
                if (cmt.startsWith(getCommentProfile()) && !comment.equals(cmt)) {
                    return false;
                }
            }
            return true;
        } finally {
            if (image != null) {
                image.destroyImages();
            }
        }
    }

    public static void mask(String logoPath, String srcPathName, String destPathName, int location,
                            int scale) throws MagickException {
        int width = getWidth(srcPathName);
        int height = getHeight(srcPathName);
        int x = 0, y = 0;
        int w, h;
        w = scale * 70 / 100;
        h = scale * 65 / 100;
        boolean lc = true;
        if (width < height) {
            switch (location) {
                case 0:
                    lc = false;
                    break;
                case 1:
                    x = width / 4 - w;
                    y = height / 8 - h / NUM_2;
                    break;
                case NUM_2:
                    x = width / NUM_2 - w;
                    y = height / 8 - h / NUM_2;
                    break;
                case 3:
                    x = width * 3 / 4 - w;
                    y = height / 8 - h / NUM_2;
                    break;
                case 4:
                    x = width / 4 - w;
                    y = height / NUM_2 - h / NUM_2;
                    break;
                case 5:
                    x = width / NUM_2 - w;
                    y = height / NUM_2 - h / NUM_2;
                    break;
                case 6:
                    x = width * 3 / 4 - w;
                    y = height / NUM_2 - h / NUM_2;
                    break;
                case 7:
                    x = width / 4 - w;
                    y = height * 7 / 8 - h / NUM_2;
                    break;
                case 8:
                    x = width / NUM_2 - w;
                    y = height * 7 / 8 - h / NUM_2;
                    break;
                case 9:
                    x = width * 3 / 4 - w;
                    y = height * 7 / 8 - h / NUM_2;
                    break;
                default:
                    break;
            }
        } else {
            switch (location) {
                case 0:
                    lc = false;
                    break;
                case 1:
                    x = width / 7 - w;
                    y = height / 6 - h / NUM_2;
                    break;
                case NUM_2:
                    x = width / NUM_2 - w;
                    y = height / 6 - h / NUM_2;
                    break;
                case 3:
                    x = width * 6 / 7 - w;
                    y = height / 6 - h / NUM_2;
                    break;
                case 4:
                    x = width / 7 - w;
                    y = height / NUM_2 - h / NUM_2;
                    break;
                case 5:
                    x = width / NUM_2 - w;
                    y = height / NUM_2 - h / NUM_2;
                    break;
                case 6:
                    x = width * 6 / 7 - w;
                    y = height / NUM_2 - h / NUM_2;
                    break;
                case 7:
                    x = width / 7 - w;
                    y = height * 5 / 6 - h / NUM_2;
                    break;
                case 8:
                    x = width / NUM_2 - w;
                    y = height * 5 / 6 - h / NUM_2;
                    break;
                case 9:
                    x = width * 6 / 7 - w;
                    y = height * 5 / 6 - h / NUM_2;
                    break;
                default:
                    break;
            }
        }
        if (x < NUM_TEN) {
            x = NUM_TEN;
        }
        if (x + w * NUM_2 + NUM_TEN > width) {
            x = width - w * NUM_2 - NUM_TEN;
        }
        if (y < NUM_TEN) {
            y = NUM_TEN;
        }
        if (y + h + NUM_TEN > height) {
            y = height - h - NUM_TEN;
        }
        if (lc) {
            ImageInfo info = new ImageInfo();
            MagickImage image = null;
            MagickImage mask = null;
            MagickImage dest = null;
            try {
                image = new MagickImage(new ImageInfo(srcPathName + "[0]"));
                mask = new MagickImage(new ImageInfo(logoPath));
                image.setFileName(destPathName);
                image.writeImage(info);
                dest = new MagickImage(new ImageInfo(destPathName));
                dest.compositeImage(CompositeOperator.AtopCompositeOp, mask, x, y);
                dest.setFileName(destPathName);
                dest.writeImage(info);
            } finally {
                if (image != null) {
                    image.destroyImages();
                }
                if (mask != null) {
                    mask.destroyImages();
                }
                if (dest != null) {
                    dest.destroyImages();
                }
            }
        }
    }

    public static MagickImage getMagickImage(String src) throws MagickException {

        ImageInfo info = new ImageInfo(src + "[0]");
        return new MagickImage(info);

    }

    public static MagickImage getMagickImage(byte[] bt) throws MagickException {

        ImageInfo info = new ImageInfo("[0]");
        return new MagickImage(info, bt);

    }

    public static int getWidth(String src) throws MagickException {
        MagickImage magImage = null;
        try {
            ImageInfo info = new ImageInfo(src + "[0]");
            magImage = new MagickImage(info);
            Dimension imageDim = magImage.getDimension();
            return imageDim.width;
        } finally {
            if (magImage != null) {
                magImage.destroyImages();
            }
        }
    }

    public static int getHeight(String src) throws MagickException {
        MagickImage magImage = null;
        try {
            ImageInfo info = new ImageInfo(src + "[0]");
            magImage = new MagickImage(info);
            Dimension imageDim = magImage.getDimension();
            return imageDim.height;
        } finally {
            if (magImage != null) {
                magImage.destroyImages();
            }
        }
    }

    public static boolean shrinkWidth(String srcPathName, String destPathName, int maxWidth)
        throws MagickException {
        int width = getWidth(srcPathName);
        int height = getHeight(srcPathName);
        if (width <= maxWidth) {
            return false;
        }
        int y = height * maxWidth / width;
        MagickImage image = null;
        MagickImage scaled = null;
        try {
            ImageInfo info = new ImageInfo();
            image = new MagickImage(new ImageInfo(srcPathName));
            scaled = image.scaleImage(maxWidth, y);
            scaled.setFileName(destPathName);
            scaled.writeImage(info);
            return true;
        } finally {
            if (image != null) {
                image.destroyImages();
            }
            if (scaled != null) {
                scaled.destroyImages();
            }
        }
    }

    private static String getCommentProfile() {
        return "nuomi";
    }

    public static MagickImage resizePhoto(MagickImage source, int maxWidth, int maxHeight)
        throws MagickException {
        int width = (int) source.getDimension().getWidth();
        int height = (int) source.getDimension().getHeight();
        // no need to resize
        if (maxWidth > width && maxHeight > height) {
            return source;
        }
        if (height / width > maxHeight / maxWidth) {
            width = width * maxHeight / height;
            height = maxHeight;
        } else {
            height = height * maxWidth / width;
            width = maxWidth;
        }
        MagickImage scaled = null;
        scaled = source.zoomImage(width, height);
        return scaled;
    }

    public static MagickImage getFirstPhoto(byte[] blob, int maxWidth, int maxHeight)
        throws MagickException {
        ImageInfo info = new ImageInfo();
        info.setPing(true);
        MagickImage image = new MagickImage(info, blob);
        int columns = 0;
        int rows = 0;
        int width = (int) image.getDimension().getWidth();
        int height = (int) image.getDimension().getHeight();
        if (height / width > maxHeight / maxWidth) {
            columns = width * maxHeight / height;
            rows = maxHeight;
        } else {
            rows = height * maxWidth / width;
            columns = maxWidth;
        }
        image.destroyImages();
        MagickImage source = null;
        info.setPing(false);
        info.setMagick("JPG");
        // no need to resize
        if (width / NUM_2 < columns) {
            source = new MagickImage(info, blob);
            if (columns >= width) {
                return source;
            } else {
                MagickImage scaled = source.zoomImage(columns, rows);
                source.destroyImages();
                return scaled;
            }
        } else {
            info.setSize(columns + "x" + rows + "!");
            source = new MagickImage(info, blob);
            MagickImage scaled = source.zoomImage(columns, rows);
            source.destroyImages();
            return scaled;
        }

    }

    public static boolean isJPEGImage(byte[] mapObj) {
        ByteArrayInputStream bais = null;
        MemoryCacheImageInputStream mcis = null;
        try {
            bais = new ByteArrayInputStream(mapObj);
            mcis = new MemoryCacheImageInputStream(new BufferedInputStream(bais));
            Iterator<ImageReader> itr = ImageIO.getImageReaders(mcis);
            while (itr.hasNext()) {
                ImageReader reader = itr.next();
                String imageName = reader.getClass().getSimpleName();
                if ("JPEGImageReader".equals(imageName)) {
                    return true;
                }
            }
        } finally {
            IoKit.close(mcis, bais);
        }
        return false;
    }

    public static boolean isLargerThan(byte[] image, int width, int height) throws IOException {
        InputStream inputStream = new BufferedInputStream(new ByteArrayInputStream(image));
        BufferedImage bufferedImage = ImageIO.read(inputStream);
        int realWidth = bufferedImage.getWidth();
        int realHeight = bufferedImage.getHeight();
        return realHeight >= height && realWidth >= width;
    }

    public static BufferedImage cropImage(BufferedImage image, int x, int y, int w, int h) {

        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();
        x = x < 0 ? 0 : x;
        y = y < 0 ? 0 : y;
        x = x > imageWidth ? imageWidth : x;
        y = y > imageHeight ? imageHeight : y;
        if (x + w > imageWidth) {
            w = imageWidth - x;
        }
        if (y + h > imageHeight) {
            h = imageHeight - y;
        }
        ImageFilter cropFilter = new CropImageFilter(x, y, w, h);
        Image img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(), cropFilter));
        BufferedImage tag = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

        Graphics g = tag.getGraphics();
        // 绘制小图
        g.drawImage(img, 0, 0, null);
        g.dispose();
        return tag;
    }

    public static BufferedImage scaleImage(BufferedImage bufferedImage, int targetWidth, int targetHeight) {
        BufferedImage tag = new BufferedImage(targetWidth, targetHeight,
            BufferedImage.TYPE_INT_RGB);
        Graphics g = tag.getGraphics();
        g.drawImage(bufferedImage.getScaledInstance(targetWidth, targetHeight, BufferedImage.SCALE_SMOOTH), 0, 0, null);
        g.dispose();
        return tag;
    }

    /**
     * 给图片添加水印
     *
     * @param iconPath   水印图片路径
     * @param srcImgPath 源图片路径
     * @param targetPath 目标图片路径
     */
    public static void markImageByIcon(String iconPath, String srcImgPath, String targetPath, int x, int y, int width, int height) {
        markImageByIcon(iconPath, srcImgPath, targetPath, null, x, y, width, height);
    }

    /**
     * 给图片添加水印、可设置水印图片旋转角度
     *
     * @param iconPath   水印图片路径
     * @param srcImgPath 源图片路径
     * @param targetPath 目标图片路径
     * @param degree     水印图片旋转角度
     */
    public static void markImageByIcon(String iconPath, String srcImgPath, String targetPath, Integer degree, int x, int y, int width, int height) {
        OutputStream os = null;
        try {
            Image srcImg = ImageIO.read(new File(srcImgPath));

            BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null), srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);

            // 得到画笔对象
            Graphics2D g = buffImg.createGraphics();

            // 设置对线段的锯齿状边缘处理
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

            g.drawImage(srcImg.getScaledInstance(srcImg.getWidth(null), srcImg.getHeight(null), Image.SCALE_SMOOTH), 0, 0, null);

            if (null != degree) {
                // 设置水印旋转
                g.rotate(Math.toRadians(degree), (double) buffImg.getWidth() / NUM_2, (double) buffImg.getHeight() / NUM_2);
            }

            // 水印图象的路径 水印一般为gif或者png的，这样可设置透明度
            ImageIcon imgIcon = new ImageIcon(iconPath);

            // 得到Image对象。
            Image img = imgIcon.getImage();

            // 透明度
            float alpha = 1f;
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));

            // 表示水印图片的位置：位置、位置、大小、大小
            // 215, 450, 290, 270
            g.drawImage(img, x, y, width, height, null);

            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));

            g.dispose();

            os = new FileOutputStream(targetPath);

            // 生成图片
            ImageIO.write(buffImg, "JPG", os);

        } catch (Exception e) {
            throw new KitException(e);
        } finally {
            IoKit.close(os);
        }
    }

    /**
     * 添加文字水印
     *
     * @param srcImgPath       原图片
     * @param outImgPath       目标位置
     * @param waterMarkContent
     * @param fontHeight
     * @param fromX
     */
    public static void markText(String srcImgPath, String outImgPath, String waterMarkContent, int fontHeight, int fromX, int fromY) {
        FileOutputStream outImgStream = null;
        try {
            // 读取原图片信息
            File srcImgFile = new File(srcImgPath);
            Image srcImg = ImageIO.read(srcImgFile);
            int srcImgWidth = srcImg.getWidth(null);
            int srcImgHeight = srcImg.getHeight(null);
            // 加水印
            BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufImg.createGraphics();
            g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);
            // Font font = new Font("Courier New", Font.PLAIN, 12);
            Font font = new Font("宋体", Font.PLAIN, fontHeight);
            // 根据图片的背景设置水印颜色
            g.setColor(Color.BLACK);

            g.setFont(font);
            int x = fromX;
            if (x == 0) {
                x = srcImgWidth / NUM_2 - getWatermarkLength(waterMarkContent, g) / NUM_2;
            }
            int y = fontHeight + fromY;
            // int x = (srcImgWidth - getWatermarkLength(watermarkStr, g)) / 2;
            // int y = srcImgHeight / 2;
            g.drawString(waterMarkContent, x, y);
            g.dispose();
            // 输出图片
            outImgStream = new FileOutputStream(outImgPath);
            ImageIO.write(bufImg, "jpg", outImgStream);
            outImgStream.flush();
        } catch (Exception e) {
            throw new KitException(e);
        } finally {
            IoKit.close(outImgStream);
        }
    }

    /**
     * 获取水印文字总长度
     *
     * @param waterMarkContent 水印的文字
     * @param g
     * @return 水印文字总长度
     */
    public static int getWatermarkLength(String waterMarkContent, Graphics2D g) {
        return g.getFontMetrics(g.getFont()).charsWidth(waterMarkContent.toCharArray(), 0, waterMarkContent.length());
    }
}
