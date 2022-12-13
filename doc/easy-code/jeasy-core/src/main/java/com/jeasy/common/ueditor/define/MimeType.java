package com.jeasy.common.ueditor.define;

import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

/**
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
public class MimeType {

    private static final ImmutableMap<String, String> TYPES = ImmutableMap.<String, String>builder()
        .put("image/gif", ".gif")
        .put("image/jpeg", ".jpg")
        .put("image/jpg", ".jpg")
        .put("image/png", ".png")
        .put("image/bmp", ".bmp")
        .put("image/webp", ".webp")
        .build();

    public static String getSuffix(String mime) {
        return MimeType.TYPES.get(mime);
    }

}
