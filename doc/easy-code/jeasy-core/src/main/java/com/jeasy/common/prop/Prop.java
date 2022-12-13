package com.jeasy.common.prop;

import com.jeasy.common.ConvertKit;
import com.jeasy.common.charset.CharsetKit;
import com.jeasy.common.io.IoKit;
import com.jeasy.exception.KitException;

import java.io.*;
import java.math.BigDecimal;
import java.util.Properties;

/**
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
public class Prop {

    private Properties properties = null;

    /**
     * Prop constructor.
     *
     * @see #Prop(String, String)
     */
    public Prop(String fileName) {
        this(fileName, CharsetKit.DEFAULT_ENCODE);
    }

    /**
     * Prop constructor
     * <p/>
     * Example:<br>
     * Prop prop = new Prop("my_config.txt", "UTF-8");<br>
     * String userName = prop.get("userName");<br>
     * <br>
     * <p/>
     * prop = new Prop("com/jfinal/file_in_sub_path_of_classpath.txt", "UTF-8");
     * <br>
     * String value = prop.get("key");
     *
     * @param fileName the properties file's name in classpath or the sub directory
     *                 of classpath
     * @param encoding the encoding
     */
    public Prop(String fileName, String encoding) {
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        try {
            inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            if (inputStream == null) {
                throw new IllegalArgumentException("Properties file not found in classpath: " + fileName);
            }
            properties = new Properties();
            inputStreamReader = new InputStreamReader(inputStream, encoding);
            properties.load(inputStreamReader);
        } catch (IOException e) {
            throw new KitException("Error loading properties file.", e);
        } finally {
            IoKit.close(inputStream, inputStreamReader);
        }
    }

    /**
     * Prop constructor.
     *
     * @see #Prop(File, String)
     */
    public Prop(File file) {
        this(file, CharsetKit.DEFAULT_ENCODE);
    }

    /**
     * Prop constructor
     * <p/>
     * Example:<br>
     * Prop prop = new Prop(new File("/var/config/my_config.txt"), "UTF-8");<br>
     * String userName = prop.get("userName");
     *
     * @param file     the properties File object
     * @param encoding the encoding
     */
    public Prop(File file, String encoding) {
        if (file == null) {
            throw new IllegalArgumentException("File can not be null.");
        }
        if (!file.isFile()) {
            throw new IllegalArgumentException("File not found : " + file.getName());
        }

        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        try {
            inputStream = new FileInputStream(file);
            properties = new Properties();
            inputStreamReader = new InputStreamReader(inputStream, encoding);
            properties.load(inputStreamReader);
        } catch (IOException e) {
            throw new RuntimeException("Error loading properties file.", e);
        } finally {
            IoKit.close(inputStream, inputStreamReader);
        }
    }

    public String get(String key) {
        return properties.getProperty(key);
    }

    public String get(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public Integer getInt(String key) {
        return getInt(key, null);
    }

    public Integer getInt(String key, Integer defaultValue) {
        String value = properties.getProperty(key);
        if (value != null) {
            return Integer.parseInt(value.trim());
        }
        return defaultValue;
    }

    public BigDecimal getBigDecimal(String key) {
        return getBigDecimal(key, null);
    }

    public BigDecimal getBigDecimal(String key, BigDecimal defaultValue) {
        String value = properties.getProperty(key);
        if (value != null) {
            return ConvertKit.toBigDecimal(value.trim());
        }
        return defaultValue;
    }

    public Long getLong(String key) {
        return getLong(key, null);
    }

    public Long getLong(String key, Long defaultValue) {
        String value = properties.getProperty(key);
        if (value != null) {
            return Long.parseLong(value.trim());
        }
        return defaultValue;
    }

    public Boolean getBoolean(String key) {
        return getBoolean(key, null);
    }

    public Boolean getBoolean(String key, Boolean defaultValue) {
        String value = properties.getProperty(key);
        if (value != null) {
            value = value.toLowerCase().trim();
            if (ConvertKit.toBool(value)) {
                return true;
            } else {
                return false;
            }
        }
        return defaultValue;
    }

    public boolean containsKey(String key) {
        return properties.containsKey(key);
    }

    public Properties getProperties() {
        return properties;
    }
}
