package com.jeasy.util;

import com.jeasy.common.str.StrKit;

/**
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
public class CommentKit {

    /**
     * 返回注释信息
     *
     * @param all
     * @return
     */
    public static String parse(String all) {
        String comment;
        int index = all.indexOf("COMMENT='");
        if (index < 0) {
            return StrKit.S_EMPTY;
        }
        comment = all.substring(index + 9);
        comment = comment.substring(0, comment.length() - 1);
        return comment;
    }
}
