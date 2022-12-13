package com.jeasy.http;

import com.jeasy.common.http.common.HttpConfig;
import com.jeasy.common.http.exception.HttpProcessException;
import com.jeasy.common.http.httpclient.HttpClientKit;

import java.io.*;

/**
 * 下载demo
 *
 * @author taomk
 * @version 1.0
 * @since 2017/08/21 18:29
 */
public class TestDownLoadImg {

    public static void main(String[] args) throws FileNotFoundException, HttpProcessException {
        String imgUrl = "https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/logo_white_fe6da1ec.png"; //百度logo
        File file = new File("c:/baidu.png");

        try (FileOutputStream fileOutputStream = new FileOutputStream(file);
             OutputStream outputStream = HttpClientKit.down(HttpConfig.custom().url(imgUrl).out(fileOutputStream))) {
            if (file.exists()) {
                System.out.println("图片下载成功了！存放在：" + file.getPath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String mp3Url = "http://win.web.rh01.sycdn.kuwo.cn/resource/n1/24/6/707126989.mp3"; //四叶草-好想你
        file = new File("c:/好想你.mp3");
        try (FileOutputStream fileOutputStream = new FileOutputStream(file);
             OutputStream outputStream = HttpClientKit.down(HttpConfig.custom().url(mp3Url).out(new FileOutputStream(file)))) {
            if (file.exists()) {
                System.out.println("mp3下载成功了！存放在：" + file.getPath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
