package com.jeasy.common.security;

import com.jeasy.common.charset.CharsetKit;
import com.jeasy.common.number.HexKit;

import java.security.MessageDigest;

/**
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
public final class HashKit {

    private static java.security.SecureRandom random = new java.security.SecureRandom();

    private HashKit() {

    }

    public static String md5(String srcStr) {
        return hash("md5", srcStr);
    }

    public static String sha1(String srcStr) {
        return hash("SHA-1", srcStr);
    }

    public static String sha256(String srcStr) {
        return hash("SHA-256", srcStr);
    }

    public static String sha384(String srcStr) {
        return hash("SHA-384", srcStr);
    }

    public static String sha512(String srcStr) {
        return hash("SHA-512", srcStr);
    }

    public static String hash(String algorithm, String srcStr) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            byte[] bytes = md.digest(srcStr.getBytes(CharsetKit.UTF_8));
            return HexKit.binary2Hex(bytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * md5 128bit 16bytes
     * sha1 160bit 20bytes
     * sha256 256bit 32bytes
     * sha384 384bit 48bites
     * sha512 512bit 64bites
     */
    public static String generateSalt(int numberOfBytes) {
        byte[] salt = new byte[numberOfBytes];
        random.nextBytes(salt);
        return HexKit.binary2Hex(salt);
    }
}




