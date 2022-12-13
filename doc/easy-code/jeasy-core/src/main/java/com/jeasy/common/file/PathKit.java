package com.jeasy.common.file;

import com.jeasy.common.str.StrKit;
import com.jeasy.exception.KitException;

import java.io.File;

/**
 * new File("..\path\abc.txt") 中的三个方法获取路径的方法
 * 1： getPath() 获取相对路径，例如   ..\path\abc.txt
 * 2： getAbslutlyPath() 获取绝对路径，但可能包含 ".." 或 "." 字符，例如  D:\otherPath\..\path\abc.txt
 * 3： getCanonicalPath() 获取绝对路径，但不包含 ".." 或 "." 字符，例如  D:\path\abc.txt
 *
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
public class PathKit {

    private static String webRootPath;
    private static String rootClassPath;

    @SuppressWarnings("rawtypes")
    public static String getPath(Class clazz) {
        String path = clazz.getResource(StrKit.S_EMPTY).getPath();
        return new File(path).getAbsolutePath();
    }

    public static String getPath(Object object) {
        String path = object.getClass().getResource(StrKit.S_EMPTY).getPath();
        return new File(path).getAbsolutePath();
    }

    public static String getRootClassPath() {
        if (rootClassPath == null) {
            try {
                String path = PathKit.class.getClassLoader().getResource(StrKit.S_EMPTY).toURI().getPath();
                rootClassPath = new File(path).getAbsolutePath();
            } catch (Exception e) {
                String path = PathKit.class.getClassLoader().getResource(StrKit.S_EMPTY).getPath();
                rootClassPath = new File(path).getAbsolutePath();
            }
        }
        return rootClassPath;
    }

    public static void setRootClassPath(String rootClassPath) {
        PathKit.rootClassPath = rootClassPath;
    }

    public static String getPackagePath(Object object) {
        Package p = object.getClass().getPackage();
        return p != null ? p.getName().replaceAll("\\.", "/") : StrKit.S_EMPTY;
    }

    public static File getFileFromJar(String file) {
        throw new KitException("Not finish. Do not use this method.");
    }

    public static String getWebRootPath() {
        if (webRootPath == null) {
            webRootPath = detectWebRootPath();
        }
        return webRootPath;
    }

    public static void setWebRootPath(String webRootPath) {
        if (webRootPath == null) {
            return;
        }

        if (webRootPath.endsWith(File.separator)) {
            webRootPath = webRootPath.substring(0, webRootPath.length() - 1);
        }
        PathKit.webRootPath = webRootPath;
    }

    private static String detectWebRootPath() {
        try {
            String path = PathKit.class.getResource("/").toURI().getPath();
            return new File(path).getParentFile().getParentFile().getCanonicalPath();
        } catch (Exception e) {
            throw new KitException(e);
        }
    }
}



