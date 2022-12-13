package com.jeasy.http;

import com.jeasy.common.http.javanet.HttpsClientByJdkKit;
import com.jeasy.common.str.StrKit;

/**
 * @author taomk
 * @version 1.0
 * @since 2017/08/21 18:29
 */
public class SendDataToolTest {

    public static void main(String[] args) throws Exception {
        String url = "https://www.facebook.com/";
        System.out.println(new HttpsClientByJdkKit().send(StrKit.S_EMPTY, url, null));
    }
}
