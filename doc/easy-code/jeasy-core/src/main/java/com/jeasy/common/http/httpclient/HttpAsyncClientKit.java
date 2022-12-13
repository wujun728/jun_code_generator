package com.jeasy.common.http.httpclient;

import com.jeasy.common.http.common.HttpConfig;
import com.jeasy.common.http.common.HttpMethods;
import com.jeasy.common.http.common.Utils;
import com.jeasy.common.http.exception.HttpProcessException;
import com.jeasy.common.http.httpclient.builder.HttpAsyncClientExtBuilder;
import com.jeasy.common.io.IoKit;
import com.jeasy.common.str.StrKit;
import com.jeasy.exception.KitException;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.*;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 使用HttpClient模拟异步发送（http/https）请求
 *
 * @author taomk
 * @version 1.0
 * @since 2017/08/21 18:29
 */
public class HttpAsyncClientKit {

    /**
     * 判定是否开启连接池、及url是http还是https <br>
     * 如果已开启连接池，则自动调用build方法，从连接池中获取client对象<br>
     * 否则，直接创建client对象<br>
     *
     * @return
     * @throws HttpProcessException
     */
    public static void create(HttpConfig config) throws HttpProcessException {
        if (config.hacb() != null && config.hacb().isSetPool) {
            // 如果设置了hcb对象，且配置了连接池，则直接从连接池取
            if (config.url().toLowerCase().startsWith(StrKit.S_HTTPS_PREFIX)) {
                config.asynclient(config.hacb().ssl().build());
            } else {
                config.asynclient(config.hacb().build());
            }
        } else {
            if (config.asynclient() == null) {
                // 如果为空，设为默认client对象
                if (config.url().toLowerCase().startsWith(StrKit.S_HTTPS_PREFIX)) {
                    config.asynclient(HttpAsyncClientExtBuilder.custom().ssl().build());
                } else {
                    config.asynclient(HttpAsyncClientExtBuilder.custom().build());
                }
            }
        }
    }

    /**
     * 以Get方式，请求资源或服务
     *
     * @param client   client对象
     * @param url      资源地址
     * @param headers  请求头信息
     * @param context  http上下文，用于cookie操作
     * @param encoding 编码
     * @param handler  回调处理对象
     * @return 返回处理结果
     * @throws HttpProcessException
     */
    public static void get(CloseableHttpAsyncClient client, String url, Header[] headers, HttpContext context, String encoding, IHandler handler) throws HttpProcessException {
        get(HttpConfig.custom().method(HttpMethods.GET).asynclient(client).url(url).headers(headers).context(context).encoding(encoding).handler(handler));
    }

    /**
     * 以Get方式，请求资源或服务
     *
     * @param config 请求参数配置
     * @throws HttpProcessException
     */
    public static void get(HttpConfig config) throws HttpProcessException {
        send(config.method(HttpMethods.GET));
    }

    /**
     * 以Post方式，请求资源或服务
     *
     * @param client   client对象
     * @param url      资源地址
     * @param parasMap 请求参数
     * @param headers  请求头信息
     * @param context  http上下文，用于cookie操作
     * @param encoding 编码
     * @param handler  回调处理对象
     * @return 返回处理结果
     * @throws HttpProcessException
     */
    public static void post(CloseableHttpAsyncClient client, String url, Map<String, Object> parasMap, Header[] headers, HttpContext context, String encoding, IHandler handler) throws HttpProcessException {
        post(HttpConfig.custom().method(HttpMethods.POST).asynclient(client).url(url).map(parasMap).headers(headers).context(context).encoding(encoding).handler(handler));
    }

    /**
     * 以Post方式，请求资源或服务
     *
     * @param config 请求参数配置
     * @throws HttpProcessException
     */
    public static void post(HttpConfig config) throws HttpProcessException {
        send(config.method(HttpMethods.POST));
    }

    /**
     * 以Put方式，请求资源或服务
     *
     * @param client   client对象
     * @param url      资源地址
     * @param parasMap 请求参数
     * @param headers  请求头信息
     * @param context  http上下文，用于cookie操作
     * @param encoding 编码
     * @param handler  回调处理对象
     * @return 返回处理结果
     * @throws HttpProcessException
     */
    public static void put(CloseableHttpAsyncClient client, String url, Map<String, Object> parasMap, Header[] headers, HttpContext context, String encoding, IHandler handler) throws HttpProcessException {
        put(HttpConfig.custom().method(HttpMethods.PUT).asynclient(client).url(url).map(parasMap).headers(headers).context(context).encoding(encoding).handler(handler));
    }

    /**
     * 以Put方式，请求资源或服务
     *
     * @param config 请求参数配置
     * @throws HttpProcessException
     */
    public static void put(HttpConfig config) throws HttpProcessException {
        send(config.method(HttpMethods.PUT));
    }

    /**
     * 以Delete方式，请求资源或服务
     *
     * @param client   client对象
     * @param url      资源地址
     * @param headers  请求头信息
     * @param context  http上下文，用于cookie操作
     * @param encoding 编码
     * @param handler  回调处理对象
     * @return 返回处理结果
     * @throws HttpProcessException
     */
    public static void delete(CloseableHttpAsyncClient client, String url, Header[] headers, HttpContext context, String encoding, IHandler handler) throws HttpProcessException {
        delete(HttpConfig.custom().method(HttpMethods.DELETE).asynclient(client).url(url).headers(headers).context(context).encoding(encoding).handler(handler));
    }

    /**
     * 以Delete方式，请求资源或服务
     *
     * @param config 请求参数配置
     * @throws HttpProcessException
     */
    public static void delete(HttpConfig config) throws HttpProcessException {
        send(config.method(HttpMethods.DELETE));
    }

    /**
     * 以Patch方式，请求资源或服务
     *
     * @param client   client对象
     * @param url      资源地址
     * @param headers  请求头信息
     * @param context  http上下文，用于cookie操作
     * @param encoding 编码
     * @param handler  回调处理对象
     * @return 返回处理结果
     * @throws HttpProcessException
     */
    public static void patch(CloseableHttpAsyncClient client, String url, Map<String, Object> parasMap, Header[] headers, HttpContext context, String encoding, IHandler handler) throws HttpProcessException {
        patch(HttpConfig.custom().method(HttpMethods.PATCH).asynclient(client).url(url).map(parasMap).headers(headers).context(context).encoding(encoding).handler(handler));
    }

    /**
     * 以Patch方式，请求资源或服务
     *
     * @param config 请求参数配置
     * @throws HttpProcessException
     */
    public static void patch(HttpConfig config) throws HttpProcessException {
        send(config.method(HttpMethods.PATCH));
    }

    /**
     * 以Head方式，请求资源或服务
     *
     * @param client   client对象
     * @param url      资源地址
     * @param headers  请求头信息
     * @param context  http上下文，用于cookie操作
     * @param encoding 编码
     * @param handler  回调处理对象
     * @throws HttpProcessException
     */
    public static void head(CloseableHttpAsyncClient client, String url, Header[] headers, HttpContext context, String encoding, IHandler handler) throws HttpProcessException {
        head(HttpConfig.custom().method(HttpMethods.HEAD).asynclient(client).url(url).headers(headers).context(context).encoding(encoding).handler(handler));
    }

    /**
     * 以Head方式，请求资源或服务
     *
     * @param config 请求参数配置
     * @throws HttpProcessException
     */
    public static void head(HttpConfig config) throws HttpProcessException {
        send(config.method(HttpMethods.HEAD));
    }


    /**
     * 以Options方式，请求资源或服务
     *
     * @param client   client对象
     * @param url      资源地址
     * @param headers  请求头信息
     * @param context  http上下文，用于cookie操作
     * @param encoding 编码
     * @param handler  回调处理对象
     * @throws HttpProcessException
     */
    public static void options(CloseableHttpAsyncClient client, String url, Header[] headers, HttpContext context, String encoding, IHandler handler) throws HttpProcessException {
        options(HttpConfig.custom().method(HttpMethods.OPTIONS).asynclient(client).url(url).headers(headers).context(context).encoding(encoding).handler(handler));
    }

    /**
     * 以Options方式，请求资源或服务
     *
     * @param config 请求参数配置
     * @throws HttpProcessException
     */
    public static void options(HttpConfig config) throws HttpProcessException {
        send(config.method(HttpMethods.OPTIONS));
    }

    /**
     * 以Trace方式，请求资源或服务
     *
     * @param client   client对象
     * @param url      资源地址
     * @param headers  请求头信息
     * @param context  http上下文，用于cookie操作
     * @param encoding 编码
     * @param handler  回调处理对象
     * @throws HttpProcessException
     */
    public static void trace(CloseableHttpAsyncClient client, String url, Header[] headers, HttpContext context, String encoding, IHandler handler) throws HttpProcessException {
        trace(HttpConfig.custom().method(HttpMethods.TRACE).asynclient(client).url(url).headers(headers).context(context).encoding(encoding).handler(handler));
    }

    /**
     * 以Trace方式，请求资源或服务
     *
     * @param config 请求参数配置
     * @throws HttpProcessException
     */
    public static void trace(HttpConfig config) throws HttpProcessException {
        send(config.method(HttpMethods.TRACE));
    }

    /**
     * 下载图片
     *
     * @param client  client对象
     * @param url     资源地址
     * @param headers 请求头信息
     * @param context http上下文，用于cookie操作
     * @param out     输出流
     * @throws HttpProcessException
     */
    public static void down(CloseableHttpAsyncClient client, String url, Header[] headers, HttpContext context, OutputStream out) throws HttpProcessException {
        down(HttpConfig.custom().method(HttpMethods.GET).asynclient(client).url(url).headers(headers).context(context).out(out));
    }

    /**
     * 下载图片
     *
     * @param config 请求参数配置
     * @throws HttpProcessException
     */
    public static void down(HttpConfig config) throws HttpProcessException {
        execute(config.method(HttpMethods.GET));
    }

    /**
     * 请求资源或服务
     *
     * @param config
     * @return
     * @throws HttpProcessException
     */
    public static void send(HttpConfig config) throws HttpProcessException {
        execute(config);
    }


    /**
     * 请求资源或服务
     *
     * @param config 请求参数配置
     * @throws HttpProcessException
     */
    private static void execute(HttpConfig config) throws HttpProcessException {
        // 获取AsyncHttpClient对象
        create(config);
        try {
            // 创建请求对象
            HttpRequestBase request = getRequest(config.url(), config.method());

            // 设置header信息
            request.setHeaders(config.headers());

            // 判断是否支持设置entity(仅HttpPost、HttpPut、HttpPatch支持)
            if (HttpEntityEnclosingRequestBase.class.isAssignableFrom(request.getClass())) {
                List<NameValuePair> nvps = new ArrayList<NameValuePair>();

                // 检测url中是否存在参数
                config.url(Utils.checkHasParas(config.url(), nvps, config.inenc()));

                // 装填参数
                HttpEntity entity = Utils.map2HttpEntity(nvps, config.map(), config.inenc());

                // 设置参数到请求对象中
                ((HttpEntityEnclosingRequestBase) request).setEntity(entity);

                Utils.info("请求地址：" + config.url());
                Utils.info("请求参数：" + nvps.toString());
            } else {
                int idx = config.url().indexOf("?");
                Utils.info("请求地址：" + config.url().substring(0, (idx > 0 ? idx : config.url().length())));
                if (idx > 0) {
                    Utils.info("请求参数：" + config.url().substring(idx + 1));
                }
            }
            // 执行请求
            final CloseableHttpAsyncClient client = config.asynclient();
            final String encoding = config.outenc();
            final IHandler handler = config.handler();
            final OutputStream out = config.out();

            // Start the client
            client.start();
            // 异步执行请求操作，通过回调，处理结果
            client.execute(request, new FutureCallback<HttpResponse>() {
                @Override
                public void failed(Exception e) {
                    handler.failed(e);
                    IoKit.close(client);
                }

                @Override
                public void completed(HttpResponse resp) {
                    try {
                        if (out == null) {
                            handler.completed(fmt2String(resp, encoding));
                        } else {
                            handler.down(fmt2Stream(resp, out));
                        }
                    } catch (HttpProcessException e) {
                        e.printStackTrace();
                    }
                    IoKit.close(client);
                }

                @Override
                public void cancelled() {
                    handler.cancelled();
                    IoKit.close(client);
                }
            });

        } catch (UnsupportedEncodingException e) {
            throw new HttpProcessException(e);
        }
    }

    /**
     * 根据请求方法名，获取request对象
     *
     * @param url    资源地址
     * @param method 请求方式名称：get、post、head、put、delete、patch、trace、options
     * @return
     */
    private static HttpRequestBase getRequest(String url, HttpMethods method) {
        HttpRequestBase request;
        switch (method.getCode()) {
            case 0:
                // HttpGet
                request = new HttpGet(url);
                break;
            case 1:
                // HttpPost
                request = new HttpPost(url);
                break;
            case 2:
                // HttpHead
                request = new HttpHead(url);
                break;
            case 3:
                // HttpPut
                request = new HttpPut(url);
                break;
            case 4:
                // HttpDelete
                request = new HttpDelete(url);
                break;
            case 5:
                // HttpTrace
                request = new HttpTrace(url);
                break;
            case 6:
                // HttpPatch
                request = new HttpPatch(url);
                break;
            case 7:
                // HttpOptions
                request = new HttpOptions(url);
                break;
            default:
                request = new HttpPost(url);
                break;
        }
        return request;
    }

    /**
     * 转化为字符串
     *
     * @param resp     响应实体
     * @param encoding 编码
     * @return
     * @throws HttpProcessException
     */
    private static String fmt2String(HttpResponse resp, String encoding) throws HttpProcessException {
        String body = StrKit.S_EMPTY;
        try {
            HttpEntity entity = resp.getEntity();
            if (entity != null) {
                final InputStream instream = entity.getContent();
                final Reader reader = new InputStreamReader(instream, encoding);
                try {
                    final StringBuilder sb = new StringBuilder();
                    final char[] tmp = new char[1024];
                    int l;
                    while ((l = reader.read(tmp)) != -1) {
                        sb.append(tmp, 0, l);
                    }
                    body = sb.toString();
                } finally {
                    IoKit.close(reader, instream);
                    EntityUtils.consume(entity);
                }
            }
        } catch (UnsupportedOperationException | IOException e) {
            throw new KitException(e);
        }
        return body;
    }

    /**
     * 转化为流
     *
     * @param resp 响应实体
     * @param out  输出流
     * @return
     * @throws HttpProcessException
     */
    private static OutputStream fmt2Stream(HttpResponse resp, OutputStream out) throws HttpProcessException {
        try {
            resp.getEntity().writeTo(out);
            EntityUtils.consume(resp.getEntity());
        } catch (IOException e) {
            throw new HttpProcessException(e);
        } finally {
            if (CloseableHttpResponse.class.isAssignableFrom(resp.getClass())) {
                IoKit.close((CloseableHttpResponse) resp);
            }
        }
        return out;
    }

    /**
     * 回调处理接口
     *
     * @author taomk
     * @version 1.0
     * @since 2017/08/21 18:29
     */
    public interface IHandler {

        /**
         * 处理异常时，执行该方法
         *
         * @param e 异常对象
         * @return
         */
        Object failed(Exception e);

        /**
         * 处理正常时，执行该方法
         *
         * @param respBody 响应Body
         * @return
         */
        Object completed(String respBody);

        /**
         * 处理正常时，执行该方法
         *
         * @param out 输出流
         * @return
         */
        Object down(OutputStream out);

        /**
         * 处理取消时，执行该方法
         *
         * @return
         */
        Object cancelled();
    }
}
