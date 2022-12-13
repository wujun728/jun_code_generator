package com.jeasy.common.security;

import com.jeasy.common.charset.CharsetKit;
import com.jeasy.common.str.StrKit;
import com.jeasy.exception.KitException;
import lombok.extern.slf4j.Slf4j;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Random;

/**
 * 用于生成密码的工具类
 *
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
@Slf4j
public final class SecurityKit {

    /**
     * 盐值
     */
    private static String salt = "wx-platform";

    private SecurityKit() {

    }

    /**
     * 默认生成六位的密码
     *
     * @return
     */
    public static String createPasswd() {
        return SecurityKit.createPasswd(6);
    }

    /**
     * 创建一个密码
     *
     * @param pwdLen 密码长度
     * @return
     */
    public static String createPasswd(int pwdLen) {
        // 35是因为数组是从0开始的，26个字母+10个数字
        final int maxNum = 36;
        // 生成的随机数
        int i;
        // 生成的密码的长度
        int count = 0;
        char[] str = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'i', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8',
            '9'};
        StringBuilder pwd = new StringBuilder(StrKit.S_EMPTY);
        Random r = new Random(System.currentTimeMillis());
        while (count < pwdLen) {
            // 生成随机数，取绝对值，防止生成负数，生成的数最大为36-1
            i = Math.abs(r.nextInt(maxNum));
            if (i >= 0 && i < str.length) {
                pwd.append(str[i]);
                count++;
            }
        }
        return pwd.toString();
    }

    /**
     * 进行sha-256加密
     *
     * @param passwd
     * @return
     */
    public static String sha256(String passwd) {
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA-256");
            mdTemp.update(passwd.getBytes(CharsetKit.DEFAULT_CHARSET));
            byte[] md = mdTemp.digest(md5(passwd).getBytes(CharsetKit.DEFAULT_CHARSET));
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            String temp = new String(str);
            return temp;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * MD5加密
     *
     * @param s
     * @return
     */
    public static String md5(String s) {
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] strTemp = s.getBytes(CharsetKit.DEFAULT_CHARSET);
            MessageDigest mdTemp = MessageDigest.getInstance("md5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;
            for (byte byte0 : md) {
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            log.error("md5 error: ", e);
            return null;
        }
    }

    /**
     * 进行md5加密,带有盐值
     *
     * @param passwd
     * @return
     */
    public static String md5WithSalt(String passwd) {
        passwd = salt + passwd + salt;
        char[] hexDigits = {'A', '6', '7', 'B', '0', '1', '2', '3', '4', '5', 'C', '8', '9', 'D', 'E', 'F'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("md5");
            mdTemp.update((salt + passwd + salt).getBytes(CharsetKit.DEFAULT_CHARSET));
            byte[] md = mdTemp.digest((passwd).getBytes(CharsetKit.DEFAULT_CHARSET));
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;
            for (byte byte0 : md) {
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            throw new KitException(e);
        }
    }

    /**
     * 根据键值进行加密
     *
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    public static String encrypt(String data, String key) throws Exception {
        byte[] bt = encrypt(data.getBytes(CharsetKit.DEFAULT_CHARSET), key.getBytes(CharsetKit.DEFAULT_CHARSET));
        return new BASE64Encoder().encode(bt);
    }

    /**
     * 根据键值进行解密
     *
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws IOException
     * @throws Exception
     */
    public static String decrypt(String data, String key) throws Exception {
        if (data == null) {
            return null;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] buf = decoder.decodeBuffer(data);
        byte[] bt = decrypt(buf, key.getBytes(CharsetKit.DEFAULT_CHARSET));
        return new String(bt, CharsetKit.DEFAULT_CHARSET);
    }

    /**
     * 根据键值进行加密
     *
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();

        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);

        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey securekey = keyFactory.generateSecret(dks);

        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance("DES");

        // 用密钥初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);

        return cipher.doFinal(data);
    }

    /**
     * 根据键值进行解密
     *
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();

        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);

        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey securekey = keyFactory.generateSecret(dks);

        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance("DES");

        // 用密钥初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);

        return cipher.doFinal(data);
    }
}
