package com.jeasy.common.str;

import com.jeasy.exception.KitException;

/**
 * @author taomk
 * @version 1.0
 * @since 2014/9/22 14:33
 */
public final class Native2AsciiKit {

    public static final int NUM_255 = 255;
    public static final int NUM_6 = 6;
    public static final int NUM_2 = 2;

    private Native2AsciiKit() {
    }

    /**
     * prefix of ascii string of native character
     */
    private static String PREFIX = "\\u";

    /**
     * Native to ascii string. It's same as execut native2ascii.exe.
     *
     * @param str native string
     * @return ascii string
     */
    public static String native2Ascii(String str) {

        char[] chars = str.toCharArray();

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < chars.length; i++) {

            sb.append(char2Ascii(chars[i]));

        }

        return sb.toString();

    }

    /**
     * Native character to ascii string.
     *
     * @param c native character
     * @return ascii string
     */

    private static String char2Ascii(char c) {

        if (c > NUM_255) {

            StringBuilder sb = new StringBuilder();

            sb.append(PREFIX);

            int code = (c >> 8);

            String tmp = Integer.toHexString(code);

            if (tmp.length() == 1) {

                sb.append("0");

            }

            sb.append(tmp);

            code = (c & 0xFF);

            tmp = Integer.toHexString(code);

            if (tmp.length() == 1) {

                sb.append("0");

            }

            sb.append(tmp);

            return sb.toString();

        } else {

            return Character.toString(c);

        }

    }

    /**
     * Ascii to native string. It's same as execut native2ascii.exe -reverse.
     *
     * @param str ascii string
     * @return native string
     */
    public static String ascii2Native(String str) {

        StringBuilder sb = new StringBuilder();

        int begin = 0;

        int index = str.indexOf(PREFIX);

        while (index != -1) {

            sb.append(str.substring(begin, index));

            sb.append(ascii2Char(str.substring(index, index + NUM_6)));

            begin = index + NUM_6;

            index = str.indexOf(PREFIX, begin);

        }

        sb.append(str.substring(begin));

        return sb.toString();

    }

    /**
     * Ascii to native character.
     *
     * @param str ascii string
     * @return native character
     */
    private static char ascii2Char(String str) {

        if (str.length() != NUM_6) {
            throw new KitException("Ascii string of a native character must be 6 character.");
        }

        if (!PREFIX.equals(str.substring(0, NUM_2))) {
            throw new KitException("Ascii string of a native character must start with \"\\u\".");
        }

        String tmp = str.substring(NUM_2, 4);

        int code = Integer.parseInt(tmp, 16) << 8;

        tmp = str.substring(4, NUM_6);

        code += Integer.parseInt(tmp, 16);

        return (char) code;
    }
}
