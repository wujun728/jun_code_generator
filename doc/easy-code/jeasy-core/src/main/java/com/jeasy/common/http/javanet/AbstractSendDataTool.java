package com.jeasy.common.http.javanet;

import com.jeasy.common.charset.CharsetKit;
import com.jeasy.common.http.exception.HttpProcessException;
import com.jeasy.common.io.IoKit;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.*;

/**
 * 工具类【使用 HTTP 或 HTTPS 协议发送请求】
 *
 * @author taomk
 * @version 1.0
 * @since 2017/08/21 18:29
 */
@Slf4j
public abstract class AbstractSendDataTool {

    /**
     * 根据URL取得一个连接
     *
     * @param urlStr 请求URL
     * @return
     * @throws HttpProcessException
     */
    protected URLConnection getRequestByUrl(String urlStr) throws HttpProcessException {
        URL url;
        URLConnection conn;
        try {
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 8087));
            url = new URL(urlStr);
            conn = url.openConnection(proxy);
            conn.setDoInput(true);
            conn.setDoOutput(true);
        } catch (IOException e) {
            throw new HttpProcessException(e);
        }

        return conn;
    }

    /**
     * 与服务器建立连接（HTTP、或HTTPS）
     *
     * @param strUrl     请求URL
     * @param httpMethod 请求方式
     * @return
     * @throws HttpProcessException
     */
    protected abstract URLConnection createRequest(String strUrl, String httpMethod) throws HttpProcessException;

    /**
     * 发送请求
     *
     * @param data        请求数据
     * @param url         请求URL
     * @param contentType 请求contentType
     * @return
     * @throws HttpProcessException
     */
    public String send(String data, String url, String contentType) throws HttpProcessException {
        return send(data, url, contentType, CharsetKit.DEFAULT_CHARSET.name());
    }

    /**
     * 发送请求
     *
     * @param data        请求数据
     * @param url         请求URL
     * @param contentType 请求contentType
     * @param encoding    编码
     * @return
     * @throws HttpProcessException
     */
    public String send(String data, String url, String contentType, String encoding) throws HttpProcessException {

        // 1、重新对请求报文进行 GBK 编码
        byte[] postData;
        try {
            postData = data.getBytes(encoding);
        } catch (UnsupportedEncodingException e) {
            throw new HttpProcessException(e);
        }

        // 2、发送 HTTPS 请求
        OutputStream reqStream = null;
        InputStream resStream = null;
        URLConnection request = null;
        String respText = null;
        try {
            log.info("交易请求地址：" + url);
            log.info("参数：" + data);

            //A、与服务器建立 HTTPS 连接
            request = createRequest(url, "POST");

            //B、指定报文头【Content-type】
            if (contentType == null || contentType.length() == 0) {
                request.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
            } else {
                request.setRequestProperty("Content-type", contentType);
            }
            // 指定报文头【Content-length】与【Keep-alive】
            request.setRequestProperty("Content-length", String.valueOf(postData.length));
            request.setRequestProperty("Keep-alive", "false");

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
            respText = new String(ms.toByteArray(), encoding);
            log.info("交易响应结果：");
            log.info(respText);
        } catch (UnknownHostException e) {
            throw new HttpProcessException("服务器不可达【" + e.getMessage() + "】", e);
        } catch (IOException e) {
            throw new HttpProcessException(e.getMessage(), e);
        } finally {
            IoKit.close(reqStream, resStream);
        }

        return respText;
    }
}
