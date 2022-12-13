package com.jeasy.common.captcha;

import com.jeasy.common.io.IoKit;
import com.jeasy.common.str.StrKit;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.geom.QuadCurve2D;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * 验证码工具类
 *
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
public final class CaptchaKit {

    /**
     * 默认的验证码大小
     */
    private static final int WIDTH = 108;

    private static final int HEIGHT = 40;

    private static final int CODE_SIZE = 4;

    /**
     * 验证码随机字符数组
     */
    private static final char[] CHAR_ARRAY = "3456789ABCDEFGHJKMNPQRSTUVWXY".toCharArray();

    /**
     * 验证码字体
     */
    private static final Font[] RANDOM_FONT = new Font[]{
        new Font("nyala", Font.BOLD, 38),
        new Font("Arial", Font.BOLD, 32),
        new Font("Bell MT", Font.BOLD, 32),
        new Font("Credit valley", Font.BOLD, 34),
        new Font("Impact", Font.BOLD, 32),
        new Font(Font.MONOSPACED, Font.BOLD, 40)
    };
    public static final int NUM_20 = 20;
    public static final int NUM_255 = 255;

    private CaptchaKit() {
    }

    /**
     * 生成验证码
     */
    public static void generate(final HttpServletResponse response, final String vCode) {
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");

        ServletOutputStream sos = null;
        try {
            drawGraphic(image, vCode);
            sos = response.getOutputStream();
            ImageIO.write(image, "JPEG", sos);
            sos.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            IoKit.close(sos);
        }
    }

    /**
     * 生成随机类
     */
    private static final Random RANDOM = new Random();

    /**
     * 生成验证码字符串
     *
     * @return 验证码字符串
     */
    public static String generateCode() {
        int count = CODE_SIZE;
        char[] buffer = new char[count];
        for (int i = 0; i < count; i++) {
            buffer[i] = CHAR_ARRAY[RANDOM.nextInt(CHAR_ARRAY.length)];
        }
        return new String(buffer);
    }

    private static void drawGraphic(final BufferedImage image, final String code) {
        // 获取图形上下文
        Graphics2D g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        // 图形抗锯齿
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // 字体抗锯齿
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // 设定背景色，淡色
        g.setColor(getRandColor(210, 250));
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // 画小字符背景
        Color color = null;
        for (int i = 0; i < NUM_20; i++) {
            color = getRandColor(120, 200);
            g.setColor(color);
            String rand = String.valueOf(CHAR_ARRAY[RANDOM.nextInt(CHAR_ARRAY.length)]);
            g.drawString(rand, RANDOM.nextInt(WIDTH), RANDOM.nextInt(HEIGHT));
            color = null;
        }
        // 取随机产生的认证码(4位数字)
        char[] buffer = code.toCharArray();
        for (int i = 0; i < buffer.length; i++) {
            char codeChar = buffer[i];
            //旋转度数 最好小于45度
            int degree = RANDOM.nextInt(28);
            if (i % 2 == 0) {
                degree = degree * (-1);
            }
            //定义坐标
            int x = 22 * i;
            int y = 21;
            //旋转区域
            g.rotate(Math.toRadians(degree), x, y);
            //设定字体颜色
            color = getRandColor(NUM_20, 130);
            g.setColor(color);
            //设定字体，每次随机
            g.setFont(RANDOM_FONT[RANDOM.nextInt(RANDOM_FONT.length)]);
            //将认证码显示到图象中
            g.drawString(StrKit.S_EMPTY + codeChar, x + 8, y + 10);
            //旋转之后，必须旋转回来
            g.rotate(-Math.toRadians(degree), x, y);
        }
        //图片中间曲线，使用上面缓存的color
        g.setColor(color);
        //width是线宽,float型
        BasicStroke bs = new BasicStroke(3);
        g.setStroke(bs);
        //画出曲线
        QuadCurve2D.Double curve = new QuadCurve2D.Double(0d, RANDOM.nextInt(HEIGHT - 8) + 4, WIDTH / 2, HEIGHT / 2, WIDTH, RANDOM.nextInt(HEIGHT - 8) + 4);
        g.draw(curve);
        // 销毁图像
        g.dispose();
    }

    /**
     * 给定范围获得随机颜色
     */
    private static Color getRandColor(final int fc, final int bc) {
        int fcv = fc;
        int bcv = bc;
        if (fcv > NUM_255) {
            fcv = NUM_255;
        }
        if (bcv > NUM_255) {
            bcv = NUM_255;
        }
        int r = fcv + RANDOM.nextInt(bcv - fcv);
        int g = fcv + RANDOM.nextInt(bcv - fcv);
        int b = fcv + RANDOM.nextInt(bcv - fcv);
        return new Color(r, g, b);
    }
}
