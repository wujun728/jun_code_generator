package com.jeasy.common.ueditor;

/**
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
public class Encoder {

    public static String toUnicode(String input) {
        StringBuilder builder = new StringBuilder();
        char[] chars = input.toCharArray();
        for (char ch : chars) {
            if (ch < 256) {
                builder.append(ch);
            } else {
                builder.append("\\u" + Integer.toHexString(ch & 0xffff));
            }
        }
        return builder.toString();
    }
}
