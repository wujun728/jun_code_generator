package com.jeasy.common.captcha;

import com.jeasy.common.io.IoKit;
import com.jeasy.common.security.HashKit;
import com.jeasy.common.str.StrKit;
import com.jeasy.common.web.CookieKit;
import com.jeasy.exception.KitException;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.geom.QuadCurve2D;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
public final class CaptchaMaker {

    /**
     * 默认的验证码大小
     */
    private static final int WIDTH = 108;

    private static final int HEIGHT = 40;

    /**
     * 验证码随机字符数组
     */
    private static final String[] STR_ARR = {"3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "J", "K", "M", "N", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y"};
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
    public static final int NUM_10 = 10;
    public static final int NUM_4 = 4;
    public static final int NUM_255 = 255;

    private static String captchaName = "_tframework_captcha";

    private HttpServletResponse response;

    private CaptchaMaker() {
    }

    private CaptchaMaker(final HttpServletResponse response) {
        this.response = response;
    }

    public static CaptchaMaker init(final HttpServletResponse response) {
        return new CaptchaMaker(response);
    }

    /**
     * 设置 captchaName
     */
    public static void setCaptchaName(final String captchaName) {
        if (StrKit.isBlank(captchaName)) {
            throw new IllegalArgumentException("captchaName can not be blank.");
        }
        CaptchaMaker.captchaName = captchaName;
    }

    /**
     * 生成验证码
     */
    public void start() {
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        String vCode = drawGraphic(image);
        vCode = vCode.toUpperCase();
        vCode = HashKit.md5(vCode);
        Cookie cookie = new Cookie(captchaName, vCode);
        cookie.setMaxAge(-1);
        cookie.setPath("/");

        response.addCookie(cookie);
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");

        ServletOutputStream sos = null;
        try {
            sos = response.getOutputStream();
            ImageIO.write(image, "jpeg", sos);
        } catch (Exception e) {
            throw new KitException(e);
        } finally {
            IoKit.close(sos);
        }
    }

    private String drawGraphic(final BufferedImage image) {
        // 获取图形上下文
        Graphics2D g = image.createGraphics();

        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        // 图形抗锯齿
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // 字体抗锯齿
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // 设定背景色
        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, WIDTH, HEIGHT);

        //生成随机类
        Random random = new Random();
        //设定字体
        g.setFont(RANDOM_FONT[random.nextInt(RANDOM_FONT.length)]);

        // 画蛋蛋，有蛋的生活才精彩
        Color color;
        for (int i = 0; i < NUM_10; i++) {
            color = getRandColor(120, 200);
            g.setColor(color);
            g.drawOval(random.nextInt(WIDTH), random.nextInt(HEIGHT), 5 + random.nextInt(NUM_10), 5 + random.nextInt(NUM_10));
            color = null;
        }

        // 取随机产生的认证码(4位数字)
        StringBuilder sRand = new StringBuilder();
        for (int i = 0; i < NUM_4; i++) {
            String rand = String.valueOf(STR_ARR[random.nextInt(STR_ARR.length)]);
            sRand.append(rand);
            //旋转度数 最好小于45度
            int degree = random.nextInt(28);
            if (i % 2 == 0) {
                degree = degree * (-1);
            }
            //定义坐标
            int x = 22 * i;
            int y = 21;
            //旋转区域
            g.rotate(Math.toRadians(degree), x, y);
            //设定字体颜色
            color = getRandColor(20, 130);
            g.setColor(color);
            //将认证码显示到图象中
            g.drawString(rand, x + 8, y + NUM_10);
            //旋转之后，必须旋转回来
            g.rotate(-Math.toRadians(degree), x, y);
            color = null;
        }
        //图片中间线
        g.setColor(getRandColor(0, 60));
        //width是线宽,float型
        BasicStroke bs = new BasicStroke(3);
        g.setStroke(bs);
        //画出曲线
        QuadCurve2D.Double curve = new QuadCurve2D.Double(0d, random.nextInt(HEIGHT - 8) + NUM_4, WIDTH / 2, HEIGHT / 2, WIDTH, random.nextInt(HEIGHT - 8) + NUM_4);
        g.draw(curve);
        // 销毁图像
        g.dispose();
        return sRand.toString();
    }

    /**
     * 给定范围获得随机颜色
     */
    private Color getRandColor(final int fc, final int bc) {
        int fcv = fc;
        int bcv = bc;
        if (fcv > NUM_255) {
            fcv = NUM_255;
        }
        if (bcv > NUM_255) {
            bcv = NUM_255;
        }
        Random random = new Random();
        int r = fcv + random.nextInt(bcv - fcv);
        int g = fcv + random.nextInt(bcv - fcv);
        int b = fcv + random.nextInt(bcv - fcv);
        return new Color(r, g, b);
    }

    /**
     * 仅能验证一次，验证后立即销毁 cookie
     *
     * @param request          请求
     * @param response         响应
     * @param userInputCaptcha 用户输入的验证码
     * @return 验证通过返回 true, 否则返回 false
     */
    public static boolean validate(final HttpServletRequest request, final HttpServletResponse response, final String userInputCaptcha) {
        if (StrKit.isBlank(userInputCaptcha)) {
            return false;
        }
        String userInputUpperCaptcha = userInputCaptcha.toUpperCase();
        userInputUpperCaptcha = HashKit.md5(userInputUpperCaptcha);
        String cookieValue = CookieKit.getCookie(captchaName, request);
        boolean result = userInputUpperCaptcha.equals(cookieValue);
        if (result) {
            CookieKit.removeCookie(captchaName, response);
        }
        return result;
    }
}




