package com.jeasy.common.qrcode;

import java.awt.*;

/**
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
public class LogoConfig {

    /**
     * logo默认边框颜色
     */
    public static final Color DEFAULT_BORDERCOLOR = Color.WHITE;
    /**
     * logo默认边框宽度
     */
    public static final int DEFAULT_BORDER = 2;
    /**
     * logo大小默认为照片的1/5
     */
    public static final int DEFAULT_LOGOPART = 5;

    private final Color borderColor;
    private final int logoPart;

    public LogoConfig() {
        this(DEFAULT_BORDERCOLOR, DEFAULT_LOGOPART);
    }

    public LogoConfig(Color borderColor, int logoPart) {
        this.borderColor = borderColor;
        this.logoPart = logoPart;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public int getBorder() {
        return DEFAULT_BORDER;
    }

    public int getLogoPart() {
        return logoPart;
    }
}
