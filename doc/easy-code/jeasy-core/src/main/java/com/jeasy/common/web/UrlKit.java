package com.jeasy.common.web;

import com.jeasy.common.charset.CharsetKit;
import com.jeasy.common.clazz.ClassKit;
import com.jeasy.common.str.StrKit;
import com.jeasy.exception.KitException;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.*;

/**
 * 统一资源定位符相关工具类
 *
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
public class UrlKit {

    /**
     * 创建URL对象
     *
     * @param url URL
     * @return URL对象
     */
    public static URL url(String url) {
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            throw new KitException(e.getMessage(), e);
        }
    }

    /**
     * 获得URL
     *
     * @param pathBaseClassLoader 相对路径（相对于classes）
     * @return URL
     */
    public static URL getURL(String pathBaseClassLoader) {
        return ClassKit.getClassLoader().getResource(pathBaseClassLoader);
    }

    /**
     * 获得URL
     *
     * @param path  相对给定 class所在的路径
     * @param clazz 指定class
     * @return URL
     */
    public static URL getURL(String path, Class<?> clazz) {
        return clazz.getResource(path);
    }

    /**
     * 获得URL，常用于使用绝对路径时的情况
     *
     * @param file URL对应的文件对象
     * @return URL
     * @throws KitException MalformedURLException
     */
    public static URL getURL(File file) {
        try {
            return file.toURI().toURL();
        } catch (MalformedURLException e) {
            throw new KitException("Error occured when get URL!", e);
        }
    }

    /**
     * 获得URL，常用于使用绝对路径时的情况
     *
     * @param files URL对应的文件对象
     * @return URL
     * @throws KitException MalformedURLException
     */
    public static URL[] getURLs(File... files) {
        final URL[] urls = new URL[files.length];
        try {
            for (int i = 0; i < files.length; i++) {
                urls[i] = files[i].toURI().toURL();
            }
        } catch (MalformedURLException e) {
            throw new KitException("Error occured when get URL!", e);
        }

        return urls;
    }

    /**
     * 格式化URL链接
     *
     * @param url 需要格式化的URL
     * @return 格式化后的URL，如果提供了null或者空串，返回null
     */
    public static String formatUrl(String url) {
        if (StrKit.isBlank(url)) {
            return null;
        }
        if (url.startsWith(StrKit.S_HTTP_PREFIX) || url.startsWith(StrKit.S_HTTPS_PREFIX)) {
            return url;
        }
        return "http://" + url;
    }

    /**
     * 补全相对路径
     *
     * @param baseUrl      基准URL
     * @param relativePath 相对URL
     * @return 相对路径
     * @throws KitException MalformedURLException
     */
    public static String complateUrl(String baseUrl, String relativePath) {
        baseUrl = formatUrl(baseUrl);
        if (StrKit.isBlank(baseUrl)) {
            return null;
        }

        try {
            final URL absoluteUrl = new URL(baseUrl);
            final URL parseUrl = new URL(absoluteUrl, relativePath);
            return parseUrl.toString();
        } catch (MalformedURLException e) {
            throw new KitException(e);
        }
    }

    /**
     * 编码URL
     *
     * @param url     URL
     * @param charset 编码
     * @return 编码后的URL
     * @throws KitException UnsupportedEncodingException
     */
    public static String encode(String url, String charset) {
        if (StrKit.isBlank(url)) {
            return StrKit.S_EMPTY;
        }
        try {
            return URLEncoder.encode(url, charset);
        } catch (UnsupportedEncodingException e) {
            throw new KitException(e);
        }
    }

    /**
     * 解码URL
     *
     * @param url     URL
     * @param charset 编码
     * @return 解码后的URL
     * @throws KitException UnsupportedEncodingException
     */
    public static String decode(String url, String charset) {
        if (StrKit.isBlank(url)) {
            return StrKit.S_EMPTY;
        }
        try {
            return URLDecoder.decode(url, charset);
        } catch (UnsupportedEncodingException e) {
            throw new KitException(e);
        }
    }

    /**
     * 获得path部分<br>
     * URI -> http://www.aaa.bbb/search?scope=ccc&q=ddd
     * PATH -> /search
     *
     * @param uriStr URI路径
     * @return path
     * @throws KitException URISyntaxException
     */
    public static String getPath(String uriStr) {
        URI uri;
        try {
            uri = new URI(uriStr);
        } catch (URISyntaxException e) {
            throw new KitException(e);
        }

        return uri.getPath();
    }

    public static String decodeURLComponent(String raw) {
        try {
            return URLDecoder.decode(raw, CharsetKit.DEFAULT_ENCODE);
        } catch (UnsupportedEncodingException e) {
            return raw;
        }
    }

    public static String encodeURLComponent(String raw) {
        try {
            return URLEncoder.encode(raw, CharsetKit.DEFAULT_ENCODE);
        } catch (UnsupportedEncodingException e) {
            return raw;
        }
    }
}
