package com.jeasy.common.number;

import com.jeasy.common.str.StrKit;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 金额工具类
 *
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
public class AmountKit {

    public static final int NUM_2 = 2;
    public static final String STR_FEN = "分";

    public static void main(String[] args) {
        System.out.println(AmountKit.amountToBig(Double.valueOf("6789.08")));
    }

    /**
     * 单位
     */
    private static final String UNIT = "万千佰拾亿千佰拾万千佰拾元角分";
    /**
     * 数字大写
     */
    private static final String DIGIT = "零壹贰叁肆伍陆柒捌玖";
    /** */
    private static final double MAX_VALUE = 9999999999999.99D;

    /**
     * 金额小写转大写
     *
     * @param amount
     * @return
     * @author nibili 2016年10月11日
     */
    public static String amountToBig(String amount) {
        return AmountKit.amountToBig(Double.valueOf(amount));
    }

    /**
     * 金额小写转大写
     *
     * @param v 金额
     * @return
     * @author nibili 2016年10月11日
     */
    public static String amountToBig(double v) {
        if (v < 0 || v > MAX_VALUE) {
            return "参数非法!";
        }
        long l = Math.round(v * 100);
        if (l == 0) {
            return "零元整";
        }
        String strValue = l + StrKit.S_EMPTY;
        // i用来控制数
        int i = 0;
        // j用来控制单位
        int j = UNIT.length() - strValue.length();
        StringBuilder rs = new StringBuilder();
        boolean isZero = false;
        for (; i < strValue.length(); i++, j++) {
            char ch = strValue.charAt(i);
            if (ch == '0') {
                isZero = true;
                if (UNIT.charAt(j) == '亿' || UNIT.charAt(j) == '万' || UNIT.charAt(j) == '元') {
                    rs.append(UNIT.charAt(j));
                    isZero = false;
                }
            } else {
                if (isZero) {
                    rs.append("零");
                    isZero = false;
                }
                // 拼接两个char，而不将两个char相加，会导致字符串发生变化
                rs.append(DIGIT.charAt(ch - '0')).append(UNIT.charAt(j));
            }
        }
        if (!rs.toString().endsWith(STR_FEN)) {
            rs.append("整");
        }
        rs = new StringBuilder(rs.toString().replaceAll("亿万", "亿"));
        return rs.toString();
    }

    /**
     * 将金额（1.00）转为100
     * 即单位从元转为分
     *
     * @param price
     * @return
     */
    public static String priceTofen(String price) {
        BigDecimal bd = new BigDecimal(price);
        BigDecimal bd2 = new BigDecimal(100);
        BigInteger re = bd.multiply(bd2).toBigInteger();
        return re.toString();
    }

    /**
     * 将100转为金额（1.00）
     * 即单位从分转为元
     *
     * @param fen
     * @return
     */
    public static String fenToPrice(Integer fen) {
        BigDecimal bd = new BigDecimal(fen);
        BigDecimal bd2 = new BigDecimal(100);
        BigDecimal re = bd.divide(bd2);
        return re.toString();
    }

    /**
     * 将金额（1）转为1.00
     *
     * @param price
     * @return
     */
    public static String priceToString(String price) {
        int idx = price.indexOf(".");
        if (idx == -1) {
            return price + ".00";
        }
        int pos = price.substring(idx + 1).length();
        if (pos == 1) {
            return price + "0";
        }
        if (pos > NUM_2) {
            return price.substring(0, idx + 3);
        }
        return price;
    }
}
