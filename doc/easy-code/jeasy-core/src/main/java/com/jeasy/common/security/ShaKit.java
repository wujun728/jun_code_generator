package com.jeasy.common.security;

import com.jeasy.common.charset.CharsetKit;
import com.jeasy.common.number.HexKit;
import com.jeasy.exception.KitException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author taomk
 * @version 1.0
 * @since 2014/10/5 21:45
 */
public final class ShaKit {

    private ShaKit() {
    }

    public static String sha1(String decript) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(decript.getBytes(CharsetKit.DEFAULT_CHARSET));
            return HexKit.byteArr2HexStr(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new KitException(e);
        }
    }

    public static String sha(String decript) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA");
            digest.update(decript.getBytes(CharsetKit.DEFAULT_CHARSET));
            return HexKit.byteArr2HexStr(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new KitException(e);
        }
    }
}
