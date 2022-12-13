package com.jeasy.common.security;

import com.jeasy.common.charset.CharsetKit;
import com.jeasy.common.number.HexKit;
import com.jeasy.exception.KitException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;

/**
 * @author taomk
 * @version 1.0
 * @since 2014/9/22 14:33
 */
public final class DesKit {

    private static final String DES = "TripleDES";

    /**
     * 定义加密算法，有DES、DESEDE(即3DES)、Blowfish
     */
    private static final String DESEDE = "DESEDE";

    private static final String PASSWORD_CRYPT_KEY = "2012PinganVitality075522628888ForShenZhenBelter075561869839";

    private DesKit() {

    }

    /**
     * 加密
     *
     * @param src
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(byte[] src, String key) throws Exception {
        Cipher cipher = Cipher.getInstance(DES);
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(CharsetKit.DEFAULT_CHARSET), DES);
        //设置密钥和加密形式
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(src);
    }

    /**
     * 解密
     *
     * @param src
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decrypt(byte[] src, String key) throws Exception {
        Cipher cipher = Cipher.getInstance(DES);
        //设置加密Key
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(CharsetKit.DEFAULT_CHARSET), DES);
        //设置密钥和解密形式
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return cipher.doFinal(src);
    }

    /**
     * 解密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static final String decrypt(String data, String password) {
        try {
            return new String(decrypt(HexKit.hexStr2ByteArr(data), password), CharsetKit.DEFAULT_CHARSET);
        } catch (Exception e) {
            throw new KitException(e);
        }
    }

    /**
     * 加密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static final String encrypt(String data, String password) {
        try {
            return HexKit.byteArr2HexStr(encrypt(data.getBytes(CharsetKit.DEFAULT_CHARSET), password));
        } catch (Exception e) {
            throw new KitException(e);
        }
    }

    /**
     * 加密方法
     *
     * @param src 源数据的字节数组
     * @return
     */
    public static byte[] encrypt3DES(byte[] src) {
        try {
            //生成密钥
            SecretKey secretKey = new SecretKeySpec(build3DesKey(PASSWORD_CRYPT_KEY), DESEDE);
            //实例化负责加密/解密的Cipher工具类
            Cipher c1 = Cipher.getInstance(DESEDE);
            //初始化为加密模式
            c1.init(Cipher.ENCRYPT_MODE, secretKey);
            return c1.doFinal(src);
        } catch (Exception e) {
            throw new KitException(e);
        }
    }


    /**
     * 解密函数
     *
     * @param src 密文的字节数组
     * @return
     */
    public static byte[] decrypt3DES(byte[] src) {
        try {
            SecretKey secretKey = new SecretKeySpec(build3DesKey(PASSWORD_CRYPT_KEY), DESEDE);
            Cipher c1 = Cipher.getInstance(DESEDE);
            //初始化为解密模式
            c1.init(Cipher.DECRYPT_MODE, secretKey);
            return c1.doFinal(src);
        } catch (Exception e) {
            throw new KitException(e);
        }
    }


    /**
     * 根据字符串生成密钥字节数组
     *
     * @param keyStr 密钥字符串
     * @return
     * @throws UnsupportedEncodingException
     */
    public static byte[] build3DesKey(String keyStr) throws UnsupportedEncodingException {
        //声明一个24位的字节数组，默认里面都是0
        byte[] key = new byte[24];
        //将字符串转成字节数组
        byte[] temp = keyStr.getBytes(CharsetKit.DEFAULT_CHARSET);

        // 执行数组拷贝 System.arraycopy(源数组，从源数组哪里开始拷贝，目标数组，拷贝多少位)
        if (key.length > temp.length) {
            //如果temp不够24位，则拷贝temp数组整个长度的内容到key数组中
            System.arraycopy(temp, 0, key, 0, temp.length);
        } else {
            //如果temp大于24位，则拷贝temp数组24个长度的内容到key数组中
            System.arraycopy(temp, 0, key, 0, key.length);
        }
        return key;
    }


    public static void main(String[] args) {
        String id = "61111111111116111111111111";
        String keystr = "-7-*d@#5EdxBvrTRe-#5CtUs";

        String idEncrypt = encrypt(id, keystr);
        System.out.println(idEncrypt);
        String idDecrypt = decrypt(idEncrypt, keystr);
        System.out.println(idDecrypt);

        String msg = "3DES加密解密案例";
        System.out.println("【加密前】：" + msg);
        //加密
        byte[] secretArr = DesKit.encrypt3DES(msg.getBytes(CharsetKit.DEFAULT_CHARSET));
        System.out.println("【加密后】：" + new String(secretArr, CharsetKit.DEFAULT_CHARSET));
        //解密
        byte[] myMsgArr = DesKit.decrypt3DES(secretArr);
        System.out.println("【解密后】：" + new String(myMsgArr, CharsetKit.DEFAULT_CHARSET));
    }
}
