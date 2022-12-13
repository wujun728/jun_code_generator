package com.jeasy.common.security;

import com.jeasy.common.charset.CharsetKit;
import com.jeasy.common.io.IoKit;
import com.jeasy.common.number.HexKit;
import com.jeasy.exception.KitException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author taomk
 * @version 1.0
 * @since 2014/9/22 14:33
 */
@Slf4j
public final class Md5Kit extends org.springframework.util.DigestUtils {

    private static final String SEC_TYPE = "md5";
    private static final int NUM_16 = 16;

    private Md5Kit() {
    }

    private static final char[] DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String md5(String inStr, String charset) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance(SEC_TYPE);
        } catch (Exception e) {
            throw new KitException(e);
        }
        byte[] byteArray;
        try {
            byteArray = inStr.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new KitException(e);
        }
        return HexKit.byteArr2HexStr(md5.digest(byteArray));
    }

    private static MessageDigest getDigest() {
        try {
            MessageDigest md5MessageDigest = MessageDigest.getInstance(SEC_TYPE);
            md5MessageDigest.reset();
            return md5MessageDigest;
        } catch (NoSuchAlgorithmException nsaex) {
            throw new KitException("Could not access md5 algorithm, fatal error");
        }
    }

    /**
     * 计算content的md5摘要.
     *
     * @param content
     * @return md5结果
     */
    public static String md5(String content) {
        byte[] data = getDigest().digest(content.getBytes(CharsetKit.DEFAULT_CHARSET));
        return HexKit.byteArr2HexStr(data);
    }

    public static String md5Sum(String inputStr) throws NoSuchAlgorithmException {
        MessageDigest digest = getDigest();
        byte[] inputStrByte = inputStr.getBytes(CharsetKit.DEFAULT_CHARSET);
        digest.update(inputStrByte, 0, inputStrByte.length);

        byte[] md5sum = digest.digest();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < NUM_16; i++) {
            char[] ob = new char[2];
            ob[0] = DIGITS[md5sum[i] >> 4 & 0x0F];
            ob[1] = DIGITS[md5sum[i] & 0x0F];
            String s = new String(ob);
            sb.append(s);
        }

        return sb.toString();
    }

    /**
     * 签名字符串
     *
     * @param text 需要签名的字符串
     * @param sign 签名结果
     * @return 签名结果
     */
    public static boolean verify(String text, String sign) {
        String mysign = md5(text);
        if (mysign.equals(sign)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 对文件进行 md5 加密
     *
     * @param file 待加密的文件
     * @return 文件加密后的 md5 值
     * @throws IOException
     */
    public static String md5(File file) throws IOException {
        FileInputStream is = new FileInputStream(file);
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance(SEC_TYPE);
            int n = 0;
            byte[] buffer = new byte[1024];
            do {
                n = is.read(buffer);
                if (n > 0) {
                    md5.update(buffer, 0, n);
                }
            } while (n != -1);
            long skipLength = is.skip(0);
            log.debug("skipLength: " + skipLength);

            byte[] encodedValue = md5.digest();

            int j = encodedValue.length;
            char[] finalValue = new char[j * 2];
            int k = 0;
            for (byte encoded : encodedValue) {
                finalValue[k++] = DIGITS[encoded >> 4 & 0xf];
                finalValue[k++] = DIGITS[encoded & 0xf];
            }

            return new String(finalValue);
        } catch (NoSuchAlgorithmException e) {
            throw new KitException(e);
        } finally {
            IoKit.close(is);
        }
    }

    /**
     * Calculates the md5 digest and returns the value as a 32 character hex string.
     *
     * @param data Data to digest
     * @return md5 digest as a hex string
     */
    public static String md5Hex(final String data) {
        try {
            return DigestUtils.md5DigestAsHex(data.getBytes(CharsetKit.DEFAULT_ENCODE));
        } catch (UnsupportedEncodingException e) {
            throw new KitException(e);
        }
    }

    /**
     * Return a hexadecimal string representation of the md5 digest of the given bytes.
     *
     * @param bytes the bytes to calculate the digest over
     * @return a hexadecimal digest string
     */
    public static String md5Hex(final byte[] bytes) {
        return DigestUtils.md5DigestAsHex(bytes);
    }
}
