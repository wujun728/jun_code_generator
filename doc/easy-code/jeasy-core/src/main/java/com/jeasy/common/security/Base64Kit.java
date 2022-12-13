package com.jeasy.common.security;


import org.apache.commons.codec.binary.Base64;

/**
 * @author taomk
 * @version 1.0
 * @since 2014/9/22 14:33
 */
public final class Base64Kit {

    private Base64Kit() {
    }

    public static byte[] encode(byte[] content) {
        return Base64.encodeBase64(content);
    }

    public static byte[] decode(byte[] content) {
        return Base64.decodeBase64(content);
    }
}
