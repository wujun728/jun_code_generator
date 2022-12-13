package com.jeasy.common.ueditor.manager;

import com.jeasy.common.str.StrKit;

import java.util.ArrayList;
import java.util.List;

/**
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
public class ManagerUtils {
    @SuppressWarnings("unchecked")
    protected static List<String> getAllowFiles(Object fileExt) {
        List<String> list = new ArrayList<String>();
        if (fileExt == null) {
            return list;
        }
        List<String> exts = (List<String>) fileExt;
        for (String ext : exts) {
            list.add(ext.replace(".", StrKit.S_EMPTY));
        }
        return list;
    }
}
