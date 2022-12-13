package com.jeasy.common.prop;

import com.jeasy.common.Func;
import com.jeasy.common.charset.CharsetKit;

import java.io.File;
import java.util.concurrent.ConcurrentHashMap;

/**
 * PropKit. PropKit can load properties file from CLASSPATH or File object.
 *
 * @author taomk
 * @version 1.0
 * @since 2017/08/21 18:29
 */
public class PropKit {

    private static Prop prop = null;
    private static final ConcurrentHashMap<String, Prop> MAP = new ConcurrentHashMap<String, Prop>();

    private PropKit() {
    }

    /**
     * Using the properties file. It will loading the properties file if not loading.
     *
     * @see #use(String, String)
     */
    public static Prop use(String fileName) {
        return use(fileName, CharsetKit.DEFAULT_ENCODE);
    }

    /**
     * Using the properties file. It will loading the properties file if not loading.
     * <p/>
     * Example:<br>
     * PropKit.use("config.txt", "UTF-8");<br>
     * PropKit.use("other_config.txt", "UTF-8");<br><br>
     * String userName = PropKit.get("userName");<br>
     * String password = PropKit.get("password");<br><br>
     * <p/>
     * userName = PropKit.use("other_config.txt").get("userName");<br>
     * password = PropKit.use("other_config.txt").get("password");<br><br>
     * <p/>
     * PropKit.use("com/jfinal/config_in_sub_directory_of_classpath.txt");
     *
     * @param fileName the properties file's name in classpath or the sub directory of classpath
     * @param encoding the encoding
     */
    public static Prop use(String fileName, String encoding) {
        Prop result = new Prop(fileName, encoding);
        Prop preResult = MAP.putIfAbsent(fileName, result);
        if (Func.isEmpty(preResult)) {
            if (PropKit.prop == null) {
                PropKit.prop = result;
            }
            return result;
        }
        return preResult;
    }

    /**
     * Using the properties file bye File object. It will loading the properties file if not loading.
     *
     * @see #use(File, String)
     */
    public static Prop use(File file) {
        return use(file, CharsetKit.DEFAULT_ENCODE);
    }

    /**
     * Using the properties file bye File object. It will loading the properties file if not loading.
     * <p/>
     * Example:<br>
     * PropKit.use(new File("/var/config/my_config.txt"), "UTF-8");<br>
     * Strig userName = PropKit.use("my_config.txt").get("userName");
     *
     * @param file     the properties File object
     * @param encoding the encoding
     */
    public static Prop use(File file, String encoding) {
        return use(file.getName(), encoding);
    }

    public static Prop useless(String fileName) {
        Prop previous = MAP.remove(fileName);
        if (PropKit.prop == previous) {
            PropKit.prop = null;
        }
        return previous;
    }

    public static void clear() {
        prop = null;
        MAP.clear();
    }

    public static Prop getProp() {
        if (prop == null) {
            throw new IllegalStateException("Load propties file by invoking PropKit.use(String fileName) method first.");
        }
        return prop;
    }

    public static Prop getProp(String fileName) {
        return MAP.get(fileName);
    }

    public static String get(String key) {
        return getProp().get(key);
    }

    public static String get(String key, String defaultValue) {
        return getProp().get(key, defaultValue);
    }

    public static Integer getInt(String key) {
        return getProp().getInt(key);
    }

    public static Integer getInt(String key, Integer defaultValue) {
        return getProp().getInt(key, defaultValue);
    }

    public static Long getLong(String key) {
        return getProp().getLong(key);
    }

    public static Long getLong(String key, Long defaultValue) {
        return getProp().getLong(key, defaultValue);
    }

    public static Boolean getBoolean(String key) {
        return getProp().getBoolean(key);
    }

    public static Boolean getBoolean(String key, Boolean defaultValue) {
        return getProp().getBoolean(key, defaultValue);
    }

    public static boolean containsKey(String key) {
        return getProp().containsKey(key);
    }
}
