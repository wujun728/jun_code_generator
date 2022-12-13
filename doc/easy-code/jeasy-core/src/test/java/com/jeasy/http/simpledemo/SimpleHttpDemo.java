package com.jeasy.http.simpledemo;

import com.jeasy.common.io.IoKit;
import com.jeasy.common.str.StrKit;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author taomk
 * @version 1.0
 * @since 2017/08/21 18:29
 */
public class SimpleHttpDemo {

    public static String send(String urlStr, Map<String, String> map, String encoding) {
        String body = StrKit.S_EMPTY;
        StringBuilder sbuf = new StringBuilder();
        if (map != null) {
            for (Entry<String, String> entry : map.entrySet()) {
                sbuf.append(entry.getKey()).append(StrKit.S_EQUAL).append(entry.getValue()).append(StrKit.S_SINGLE_AND);
            }
            if (sbuf.length() > 0) {
                sbuf.deleteCharAt(sbuf.length() - 1);
            }
        }

        byte[] postData = null;
        OutputStream reqStream = null;
        InputStream resStream = null;
        URLConnection request = null;

        try {
            // 1、重新对请求报文进行 GBK 编码
            postData = sbuf.toString().getBytes(encoding);

            // 2、发送 HTTP(S) 请求
            System.out.println("交易请求地址：" + urlStr);
            System.out.println("参数：" + sbuf.toString());

            //A、与服务器建立 HTTP(S) 连接
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 8087));
            URL url = new URL(urlStr);
            request = url.openConnection(proxy);
            request.setDoInput(true);
            request.setDoOutput(true);

            //B、指定报文头【Content-type】、【Content-length】 与 【Keep-alive】
            request.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
            request.setRequestProperty("Content-length", String.valueOf(postData.length));
            request.setRequestProperty("Keep-alive", "false");
            request.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

            //C、发送报文至服务器
            reqStream = request.getOutputStream();
            reqStream.write(postData);
            reqStream.close();

            //D、接收服务器返回结果
            ByteArrayOutputStream ms = null;
            resStream = request.getInputStream();
            ms = new ByteArrayOutputStream();
            byte[] buf = new byte[4096];
            int count;
            while ((count = resStream.read(buf, 0, buf.length)) > 0) {
                ms.write(buf, 0, count);
            }
            resStream.close();
            body = new String(ms.toByteArray(), encoding);
        } catch (UnknownHostException e) {
            System.err.println("服务器不可达【" + e.getMessage() + "】");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IoKit.close(reqStream, resStream);
        }

        return body;
    }

    public static void main(String[] args) {
        String url = "http://php.weather.sina.com.cn/iframe/index/w_cl.php";
        //url = "https://sso.tgb.com:8443/cas/login";
        Map<String, String> map = new HashMap<String, String>();
        map.put("code", "js");
        map.put("day", "0");
        map.put("city", "上海");
        map.put("dfc", "1");
        map.put("charset", "utf-8");
        String body = send(url, map, "utf-8");
        System.out.println("交易响应结果：");
        System.out.println(body);
    }
}
