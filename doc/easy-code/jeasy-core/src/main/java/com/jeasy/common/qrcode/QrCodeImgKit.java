package com.jeasy.common.qrcode;

import com.google.common.collect.Maps;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.jeasy.common.charset.CharsetKit;
import com.jeasy.common.str.StrKit;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.OutputStream;
import java.util.Map;

/**
 * 二维码工具类
 *
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
public class QrCodeImgKit {

    public static void main(String[] args) throws WriterException {
        String content = "http://www.summall.com/pros/555184699680389065.html";

        try {

            QrCodeImgKit.createImg(content, 300, 300, "D:/newPic.jpg");
            QrCodeImgKit.createImgWithLogo(content, 300, 300, "D:/newPic1.jpg", "E:/快盘1/projects/yishanjia/yishanjia-platform/src/main/webapp/assets/webapp/images/m_icon8.png");
            // 二维码解析
            Thread.sleep(1000);
            String tempString = QrCodeImgKit.parseQRCODEImage(new File("D:/newPic1.jpg"));
            System.out.println(tempString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建二维码图片
     *
     * @param toEncodeString
     * @param picWigth
     * @param picHeight
     * @param targetFilePath
     * @throws Exception
     */
    public static void createImg(String toEncodeString, int picWigth, int picHeight, String targetFilePath) throws Exception {
        BufferedImage bim = getQRCODEBufferedImage(toEncodeString, BarcodeFormat.QR_CODE, picWigth, picHeight, getDecodeHintType());
        File file = new File(targetFilePath);
        if (file.exists()) {
            file = new File(targetFilePath);
        }
        ImageIO.write(bim, "jpeg", file);
    }

    /**
     * 创建二维码图片
     *
     * @param toEncodeString
     * @param picWigth
     * @param picHeight
     * @param targetFilePath
     * @throws Exception
     */
    public static void createImgWithLogo(String toEncodeString, int picWigth, int picHeight, String targetFilePath, String logoPath) throws Exception {
        BufferedImage bim = QrCodeImgKit.getQRCODEBufferedImage(toEncodeString, BarcodeFormat.QR_CODE, picWigth, picHeight, getDecodeHintType());
        File file = new File(targetFilePath);
        if (file.exists()) {
            file = new File(targetFilePath);
        }
        ImageIO.write(bim, "jpeg", file);
        addLogoQRCode(file, new File(logoPath), new LogoConfig());
        // Thread.sleep(1000);
        // parseQRCODEImage(file);

    }

    /**
     * 二维码的解析
     *
     * @param file
     */
    public static String parseQRCODEImage(File file) {
        try {
            MultiFormatReader formatReader = new MultiFormatReader();

            // File file = new File(filePath);
            if (!file.exists()) {
                return StrKit.S_EMPTY;
            }

            BufferedImage image = ImageIO.read(file);

            LuminanceSource source = new BufferedImageLuminanceSource(image);
            Binarizer binarizer = new HybridBinarizer(source);
            BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);

            @SuppressWarnings("rawtypes")
            Map<DecodeHintType, Object> hints = Maps.newHashMap();
            hints.put(DecodeHintType.CHARACTER_SET, CharsetKit.DEFAULT_ENCODE);

            Result result = formatReader.decode(binaryBitmap, hints);

            return result.getText();
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    /**
     * 生成二维码bufferedImage图片
     *
     * @param content       编码内容
     * @param barcodeFormat 编码类型
     * @param width         图片宽度
     * @param height        图片高度
     * @param hints         设置参数
     * @return
     */
    private static BufferedImage getQRCODEBufferedImage(String content, BarcodeFormat barcodeFormat, int width, int height, Map<EncodeHintType, ?> hints) {
        MultiFormatWriter multiFormatWriter;
        BitMatrix bm;
        BufferedImage image = null;
        try {
            multiFormatWriter = new MultiFormatWriter();

            // 参数顺序分别为：编码内容，编码类型，生成图片宽度，生成图片高度，设置参数
            bm = multiFormatWriter.encode(content, barcodeFormat, width, height, hints);

            int w = bm.getWidth();
            int h = bm.getHeight();
            image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

            // 开始利用二维码数据创建Bitmap图片，分别设为黑（0xFFFFFFFF）白（0xFF000000）两色
            for (int x = 0; x < w; x++) {
                for (int y = 0; y < h; y++) {
                    image.setRGB(x, y, bm.get(x, y) ? 0xFF000000 : 0xFFCCDDEE);
                }
            }
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return image;
    }

    /**
     * 给二维码图片添加Logo
     *
     * @param qrPic
     * @param logoPic
     */
    private static void addLogoQRCode(File qrPic, File logoPic, LogoConfig logoConfig) {
        try {
            if (!qrPic.isFile() || !logoPic.isFile()) {
                System.out.print("file not find !");
                System.exit(0);
            }

            /**
             * 读取二维码图片，并构建绘图对象
             */
            BufferedImage image = ImageIO.read(qrPic);
            Graphics2D g = image.createGraphics();

            /**
             * 读取Logo图片
             */
            BufferedImage logo = ImageIO.read(logoPic);

            int widthLogo = logo.getWidth(), heightLogo = logo.getHeight();

            // 计算图片放置位置
            int x = (image.getWidth() - widthLogo) / 2;
            int y = (image.getHeight() - logo.getHeight()) / 2;

            // 开始绘制图片
            g.drawImage(logo, x, y, widthLogo, heightLogo, null);
            g.drawRoundRect(x, y, widthLogo, heightLogo, 15, 15);
            g.setStroke(new BasicStroke(logoConfig.getBorder()));
            g.setColor(logoConfig.getBorderColor());
            g.drawRect(x, y, widthLogo, heightLogo);

            g.dispose();

            ImageIO.write(image, "jpeg", new File("D:/newPic.jpg"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将二维码生成为文件
     *
     * @param bm
     * @param imageFormat
     * @param file
     */
    public void decodeQRCODE2ImageFile(BitMatrix bm, String imageFormat, File file) {
        try {
            if (null == file || file.getName().trim().isEmpty()) {
                throw new IllegalArgumentException("文件异常，或扩展名有问题！");
            }

            BufferedImage bi = fileToBufferedImage(bm);
            ImageIO.write(bi, "jpeg", file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将二维码生成为输出流
     *
     * @param bm
     * @param imageFormat
     * @param os
     */
    public void decodeQRCODE2OutputStream(BitMatrix bm, String imageFormat, OutputStream os) {
        try {
            BufferedImage image = fileToBufferedImage(bm);
            ImageIO.write(image, imageFormat, os);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 构建初始化二维码
     *
     * @param bm
     * @return
     */
    public BufferedImage fileToBufferedImage(BitMatrix bm) {
        BufferedImage image = null;
        try {
            int w = bm.getWidth(), h = bm.getHeight();
            image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

            for (int x = 0; x < w; x++) {
                for (int y = 0; y < h; y++) {
                    image.setRGB(x, y, bm.get(x, y) ? 0xFF000000 : 0xFFCCDDEE);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }

    /**
     * 设置二维码的格式参数
     *
     * @return
     */
    private static Map<EncodeHintType, Object> getDecodeHintType() {
        // 用于设置QR二维码参数
        Map<EncodeHintType, Object> hints = Maps.newHashMap();
        // 设置QR二维码的纠错级别（H为最高级别）具体级别信息
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        // 设置编码方式
        hints.put(EncodeHintType.CHARACTER_SET, CharsetKit.DEFAULT_ENCODE);
        return hints;
    }
}
