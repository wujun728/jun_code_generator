package com.jeasy.common.ocr;

import com.google.common.collect.Maps;
import com.jeasy.common.Func;
import com.jeasy.common.http.common.HttpConfig;
import com.jeasy.common.http.common.HttpHeader;
import com.jeasy.common.http.common.Utils;
import com.jeasy.common.http.exception.HttpProcessException;
import com.jeasy.common.http.httpclient.HttpClientKit;
import com.jeasy.common.http.httpclient.builder.HttpClientExtBuilder;
import com.jeasy.common.prop.PropKit;
import com.jeasy.common.regex.ReKit;
import org.apache.http.Header;
import org.apache.http.client.HttpClient;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

/**
 * 识别验证码
 *
 * @author taomk
 * @version 1.0
 * @since 2017/08/21 18:29
 */
public class OcrKit {

    /**
     * 接口说明：
     * https://github.com/AvensLab/OcrKing/blob/master/线上识别http接口说明.txt
     */
    private static final String API_URL = "http://lab.ocrking.com/ok.html";
    private static final String API_KEY = PropKit.use("config.properties").get("OCR.key");
    private static HttpClient client = null;

    public static void enableCatch() {
        client = HttpClientExtBuilder.custom().proxy("127.0.0.1", 8888).build();
    }

    public static void unEnableCatch() {
        client = null;
    }

    /**
     * 获取固定参数
     *
     * @return
     */
    private static Map<String, Object> getParaMap() {
        //加载所有参数
        Map<String, Object> map = Maps.newHashMap();
        map.put("service", "OcrKingForCaptcha");
        map.put("language", "eng");
        // 7-数字大写小写，5-数字大写字母
        map.put("charset", "7");
        map.put("type", "http://www.unknown.com");
        map.put("apiKey", API_KEY);
        return map;
    }

    /**
     * 识别本地校验码（英文：字母+大小写）
     *
     * @param filePath 验证码地址
     * @return
     */
    public static String ocrCode(String filePath) {
        return ocrCode(filePath, 0);
    }

    /**
     * 识别本地校验码（英文：字母+大小写）
     *
     * @param imgFilePath  验证码地址
     * @param limitCodeLen 验证码长度（如果结果与设定长度不一致，则返回获取失败的提示）
     * @return
     */
    public static String ocrCode(String imgFilePath, int limitCodeLen) {
        //读取文件
        File f = new File(imgFilePath);
        if (!f.exists()) {
            return "Error:文件不存在!";
        }

        String html;
        try {
            html = HttpClientKit.upload(HttpConfig.custom().client(client).url(API_URL).files(new String[]{imgFilePath}, "ocrfile", true).map(getParaMap()));
            String[] results = ReKit.findAll(html, "<Result>([^<]*)</Result>\\s*<Status>([^<]*)</Status>");
            if (results.length > 0) {
                if (Boolean.parseBoolean(results[1])) {
                    if (limitCodeLen <= 0 || limitCodeLen == results[0].length()) {
                        // 不判断长度或者长度一致时，直接返回
                        return results[0];
                    } else {
                        return "Error:获取失败! 原因：识别结果长度为:" + results[0].length() + "（期望长度:" + limitCodeLen + "）";
                    }
                } else {
                    return "Error:获取失败! 原因：" + results[0];
                }
            }
        } catch (HttpProcessException e) {
            Utils.exception(e);
        }

        return "Error:获取失败!";
    }


    /**
     * 直接获取网络验证码（验证码不刷新）
     *
     * @param imgUrl 验证码地址
     * @return
     */
    public static String ocrCode4Net(String imgUrl) {
        return ocrCode4Net(imgUrl, 0);
    }

    /**
     * 直接获取网络验证码（验证码不刷新）
     *
     * @param imgUrl       验证码地址
     * @param limitCodeLen 验证码长度
     * @return
     */
    public static String ocrCode4Net(String imgUrl, int limitCodeLen) {
        Map<String, Object> map = getParaMap();
        map.put("url", imgUrl);

        Header[] headers = HttpHeader.custom().userAgent("Mozilla/5.0 (Windows NT 5.1; zh-CN; rv:1.9.1.3) Gecko/20100101 Firefox/8.0").build();

        try {
            String html = HttpClientKit.post(HttpConfig.custom().client(client).url(API_URL).headers(headers).map(map));
            String[] results = ReKit.findAll(html, "<Result>([^<]*)</Result>\\s*<Status>([^<]*)</Status>");
            if (results.length > 0) {
                if (Boolean.parseBoolean(results[1])) {
                    if (limitCodeLen <= 0 || limitCodeLen == results[0].length()) {
                        // 不判断长度或者长度一致时，直接返回
                        return results[0];
                    } else {
                        return "Error:获取失败! 原因：识别结果长度为:" + results[0].length() + "（期望长度:" + limitCodeLen + "）";
                    }
                } else {
                    return "Error:获取失败! 原因：" + results[0];
                }
            }
        } catch (HttpProcessException e) {
            Utils.exception(e);
        }

        return "Error:获取失败!";
    }


    /**
     * 直接获取网络验证码（通过获取图片流，然后识别验证码）
     *
     * @param config   HttpConfig对象（设置cookie）
     * @param savePath 图片保存的完整路径（值为null时，不保存），如：c:/1.png
     * @return
     */
    public static String ocrCode4Net(HttpConfig config, String savePath) {
        return ocrCode4Net(config, savePath, 0);
    }

    /**
     * 直接获取网络验证码（通过获取图片流，然后识别验证码）
     *
     * @param config       HttpConfig对象（设置cookie）
     * @param savePath     图片保存的完整路径（值为null时，不保存），如：c:/1.png
     * @param limitCodeLen 验证码长度
     * @return
     */
    @SuppressWarnings("resource")
    public static String ocrCode4Net(HttpConfig config, String savePath, int limitCodeLen) {
        if (Func.isEmpty(savePath)) {
            // 如果不保存图片，则直接使用图片地址的方式获取验证码
            return ocrCode4Net(config.url(), limitCodeLen);
        }

        try (
            // 下载图片
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            //本地测试，可以保存一下图片，方便核验
            FileOutputStream fos = new FileOutputStream(savePath);
            ByteArrayOutputStream downStream = (ByteArrayOutputStream) HttpClientKit.down(config.client(client).out(out))
        ) {
            fos.write(downStream.toByteArray());
            return ocrCode(savePath, limitCodeLen);
        } catch (HttpProcessException | IOException e) {
            Utils.exception(e);
        }

        return "Error:获取失败!";
    }

    public static void main(String[] args) throws HttpProcessException, IOException {
        String filePath = "C:/Users/160049/Desktop/中国.png";
        String url = "http://file.ocrking.net:6080/small/20161104/w4fCjnzCl8KTwphpwqnCv2bCn8Kp/66fcff8d-61b1-49d6-bbfe-7428cf7accdf_debug.png?e9gFvJmkLbmgsZNTUCCNkjfi8J0Wbpn1CZHeP98eT1kxZ0ISBDt8Ql6h6zQ79pJg";
        String url2 = "http://59.41.9.91/GZCX/WebUI/Content/Handler/ValidateCode.ashx?0.3271647585525703";
        String code1 = ocrCode(filePath, 5);
        String code2 = ocrCode4Net(url, 5);
        String code3 = ocrCode4Net(HttpConfig.custom().url(url2), System.getProperty("user.dir") + System.getProperty("file.separator") + "123.png", 5);
        System.out.println(code1);
        System.out.println(code2);
        System.out.println(code3);
        System.out.println("----");
    }
}
