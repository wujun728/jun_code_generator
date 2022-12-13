package com.jeasy.common.security;

import com.jeasy.common.charset.CharsetKit;
import com.jeasy.common.number.HexKit;
import com.jeasy.exception.KitException;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @author taomk
 * @version 1.0
 * @since 2014/9/22 14:33
 */
public final class AesKit {

    private static final String KEY = "springblade";

    private static final String AES = "AES";

    private static final String SHA1PRNG = "SHA1PRNG";

    private static final Integer KEY_GEN_INIT = 128;

    private AesKit() {
    }

    /**
     * 加密
     *
     * @param content  需要加密的内容
     * @param password 加密密码
     * @return 结果
     */
    private static byte[] encryptByte(final String content, final String password) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance(AES);
            SecureRandom secureRandom = SecureRandom.getInstance(SHA1PRNG);
            secureRandom.setSeed(password.getBytes(CharsetKit.DEFAULT_CHARSET));
            kgen.init(KEY_GEN_INIT, secureRandom);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, AES);
            // 创建密码器
            Cipher cipher = Cipher.getInstance(AES);
            byte[] byteContent = content.getBytes(CharsetKit.DEFAULT_ENCODE);
            // 初始化
            cipher.init(Cipher.ENCRYPT_MODE, key);
            // 加密
            return cipher.doFinal(byteContent);
        } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | BadPaddingException
            | UnsupportedEncodingException | IllegalBlockSizeException e) {
            throw new KitException(e);
        }
    }

    /**
     * 解密
     *
     * @param content  待解密内容
     * @param password 解密密钥
     * @return 结果
     */
    private static byte[] decryptByte(final byte[] content, final String password) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance(AES);
            SecureRandom secureRandom = SecureRandom.getInstance(SHA1PRNG);
            secureRandom.setSeed(password.getBytes(CharsetKit.DEFAULT_CHARSET));
            kgen.init(KEY_GEN_INIT, secureRandom);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, AES);
            // 创建密码器
            Cipher cipher = Cipher.getInstance(AES);
            // 初始化
            cipher.init(Cipher.DECRYPT_MODE, key);
            // 加密
            return cipher.doFinal(content);
        } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException
            | BadPaddingException | IllegalBlockSizeException e) {
            throw new KitException(e);
        }
    }

    /**
     * AES加密
     *
     * @param str 内容
     * @return 结果
     */
    public static String encrypt(final String str) {
        return HexKit.binary2Hex(encryptByte(str, KEY));
    }

    /**
     * AES解密
     *
     * @param str 内容
     * @return 结果
     */
    public static String decrypt(final String str) {
        // 16进制转2进制
        byte[] decryptFrom = HexKit.hex2Byte(str);
        // 根据byte进行解码
        byte[] decryptResult = decryptByte(decryptFrom, KEY);
        return new String(decryptResult, CharsetKit.DEFAULT_CHARSET);
    }

    public static void main(String[] args) {
        String encrypt = AesKit.encrypt("我爱你");
        System.out.println(encrypt);
        System.out.println(AesKit.decrypt(encrypt));
    }
}
