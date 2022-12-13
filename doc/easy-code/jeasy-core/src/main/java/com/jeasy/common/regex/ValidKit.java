package com.jeasy.common.regex;

import com.jeasy.common.str.StrKit;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 日常检验工具类
 *
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
public class ValidKit {

    public static final int NUM_32 = 32;
    public static final int NUM_6 = 6;

    /**
     * 验证输入手机号码
     *
     * @param str
     * @return 如果是符合格式的字符串, 返回 <b>true </b>,否则为 <b>false </b>
     * @author nibili 2016年9月1日
     */
    public static boolean isMobileNumber(String str) {
        if (StrKit.isBlank(str)) {
            return false;
        } else {
            String regex = "^[1]+[3,4,5,7,8]+\\d{9}$";
            return match(regex, str);
        }
    }

    /**
     * 是否匹配正则
     *
     * @param regex 正则表达式字符串
     * @param str   要匹配的字符串
     * @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
     * @author nibili 2016年9月1日
     */
    private static boolean match(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * 邮箱验证
     *
     * @param email
     * @return
     * @author nibili 2016年9月1日
     */
    public static boolean isEmail(String email) {
        if (StrKit.isBlank(email)) {
            return false;
        } else {
            String str = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
            Pattern p = Pattern.compile(str);
            Matcher m = p.matcher(email);
            return m.matches();
        }
    }

    /**
     * 检验密码是否符合要求，密码最少6个，最多32位
     *
     * @param password
     * @return
     * @author nibili 2016年9月1日
     */
    public static boolean isPassword(String password) {
        if (StrKit.isBlank(password)) {
            return false;
        } else {
            if (password.length() > NUM_32 || password.length() < NUM_6) {
                return false;
            } else {
                return true;
            }
        }
    }
}
