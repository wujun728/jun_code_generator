package com.jeasy.common;

import com.google.common.collect.Maps;
import com.jeasy.common.charset.CharsetKit;
import com.jeasy.common.object.ObjectKit;
import com.jeasy.common.str.StrKit;
import com.jeasy.common.web.UrlKit;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 高频方法集合类
 *
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
public final class Func {

    private Func() {

    }

    /**
     * 两个字符串数值 乘法
     *
     * @return
     */
    public static String multiply(final String arg1, final String arg2) {
        BigDecimal bg1 = new BigDecimal(arg1);
        BigDecimal bg2 = new BigDecimal(arg2);
        return bg1.multiply(bg2).toString();
    }

    /**
     * 比较两个对象是否相等。<br>
     * 相同的条件有两个，满足其一即可：<br>
     * 1. obj1 == null && obj2 == null; 2. obj1.equals(obj2)
     *
     * @param obj1 对象1
     * @param obj2 对象2
     * @return 是否相等
     */
    public static boolean equals(final Object obj1, final Object obj2) {
        return (obj1 != null) ? (obj1.equals(obj2)) : (obj2 == null);
    }

    /**
     * 计算对象长度，如果是字符串调用其length函数，集合类调用其size函数，数组调用其length属性，其他可遍历对象遍历计算长度
     *
     * @param obj 被计算长度的对象
     * @return 长度
     */
    public static int length(final Object obj) {
        return ObjectKit.length(obj);
    }

    /**
     * 对象中是否包含元素
     *
     * @param obj     对象
     * @param element 元素
     * @return 是否包含
     */
    public static boolean contains(final Object obj, final Object element) {
        return ObjectKit.contains(obj, element);
    }


    public static boolean isNotEmpty(final Object obj) {
        return !isEmpty(obj);
    }

    /**
     * 对象是否为空
     *
     * @param obj String,List,Map,Object[],int[],long[]
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static boolean isEmpty(final Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof String) {
            if (StrKit.equals(StrKit.S_EMPTY, obj.toString().trim())) {
                return true;
            }
        } else if (obj instanceof List) {
            if (((List) obj).size() == 0) {
                return true;
            }
        } else if (obj instanceof Map) {
            if (((Map) obj).size() == 0) {
                return true;
            }
        } else if (obj instanceof Set) {
            if (((Set) obj).size() == 0) {
                return true;
            }
        } else if (obj instanceof Object[]) {
            if (((Object[]) obj).length == 0) {
                return true;
            }
        } else if (obj instanceof int[]) {
            if (((int[]) obj).length == 0) {
                return true;
            }
        } else if (obj instanceof long[]) {
            if (((long[]) obj).length == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 对象组中是否存在 Empty Object
     *
     * @param os 对象组
     * @return
     */
    public static boolean isOneEmpty(final Object... os) {
        for (Object o : os) {
            if (isEmpty(o)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 对象组中是否全是 Empty Object
     *
     * @param os
     * @return
     */
    public static boolean isAllEmpty(final Object... os) {
        for (Object o : os) {
            if (!isEmpty(o)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 是否为数字
     *
     * @param obj
     * @return
     */
    public static boolean isNum(final Object obj) {
        try {
            Integer.parseInt(obj.toString());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 数字是否为Null或Zero
     *
     * @param obj
     * @return
     */
    public static boolean isNullOrZero(final Object obj) {
        if (obj == null) {
            return true;
        }

        if (obj instanceof Integer) {
            try {
                return Integer.parseInt(obj.toString()) == 0;
            } catch (Exception e) {
                return false;
            }
        }

        if (obj instanceof Long) {
            try {
                return Long.parseLong(obj.toString()) == 0L;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    /**
     * 如果为空, 则调用默认值
     *
     * @param str
     * @return
     */
    public static Object getValue(final Object str, final Object defaultValue) {
        if (isEmpty(str)) {
            return defaultValue;
        }
        return str;
    }

    /**
     * 格式化文本
     *
     * @param template 文本模板，被替换的部分用 {} 表示
     * @param values   参数值
     * @return 格式化后的文本
     */
    public static String format(final String template, final Object... values) {
        return StrKit.format(template, values);
    }

    /**
     * 格式化文本
     *
     * @param template 文本模板，被替换的部分用 {key} 表示
     * @param map      参数值对
     * @return 格式化后的文本
     */
    public static String format(final String template, final Map<?, ?> map) {
        return StrKit.format(template, map);
    }

    /**
     * 强转->string,并去掉多余空格
     *
     * @param str
     * @return
     */
    public static String toStr(final Object str) {
        return toStr(str, StrKit.S_EMPTY);
    }

    /**
     * 强转->string,并去掉多余空格
     *
     * @param str
     * @param defaultValue
     * @return
     */
    public static String toStr(final Object str, final String defaultValue) {
        if (null == str) {
            return defaultValue;
        }
        return str.toString().trim();
    }

    /**
     * 强转->int
     *
     * @param value
     * @return
     */
    public static int toInt(final Object value) {
        return toInt(value, -1);
    }

    /**
     * 强转->int
     *
     * @param value
     * @param defaultValue
     * @return
     */
    public static int toInt(final Object value, final int defaultValue) {
        return ConvertKit.toInt(value, defaultValue);
    }

    /**
     * 强转->long
     *
     * @param value
     * @return
     */
    public static long toLong(final Object value) {
        return toLong(value, -1);
    }

    /**
     * 强转->long
     *
     * @param value
     * @param defaultValue
     * @return
     */
    public static long toLong(final Object value, final long defaultValue) {
        return ConvertKit.toLong(value, defaultValue);
    }

    public static String encodeUrl(final String url) {
        return UrlKit.encode(url, CharsetKit.UTF_8);
    }

    public static String decodeUrl(final String url) {
        return UrlKit.decode(url, CharsetKit.UTF_8);
    }

    /**
     * map的key转为小写
     *
     * @param map
     * @return Map<String,Object>
     */
    public static Map<String, Object> caseInsensitiveMap(final Map<String, Object> map) {
        Map<String, Object> tempMap = Maps.newHashMap();
        for (Entry<String, Object> entry : map.entrySet()) {
            tempMap.put(entry.getKey().toLowerCase(), entry.getValue());
        }
        return tempMap;
    }

    /**
     * 获取map中第一个数据值
     *
     * @param <K> Key的类型
     * @param <V> Value的类型
     * @param map 数据源
     * @return 返回的值
     */
    public static <K, V> V getFirstOrNull(final Map<K, V> map) {
        V obj = null;
        for (Entry<K, V> entry : map.entrySet()) {
            obj = entry.getValue();
            if (obj != null) {
                break;
            }
        }
        return obj;
    }

    /**
     * 创建StringBuilder对象
     *
     * @return StringBuilder对象
     */
    public static StringBuilder builder(final String... strs) {
        final StringBuilder sb = new StringBuilder();
        for (String str : strs) {
            sb.append(str);
        }
        return sb;
    }

    /**
     * 创建StringBuilder对象
     *
     * @return StringBuilder对象
     */
    public static void builder(final StringBuilder sb, final String... strs) {
        for (String str : strs) {
            sb.append(str);
        }
    }
}
