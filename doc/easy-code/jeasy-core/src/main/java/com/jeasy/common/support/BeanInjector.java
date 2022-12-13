package com.jeasy.common.support;

import com.google.common.collect.Maps;
import com.jeasy.common.collection.CollectionKit;
import com.jeasy.common.object.BeanKit;
import com.jeasy.common.str.StrKit;
import com.jeasy.exception.KitException;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * javabean 、 paras映射
 *
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
public class BeanInjector {

    public static final <T> T inject(Class<T> beanClass, HttpServletRequest request) {
        try {
            return BeanKit.mapToBeanIgnoreCase(getParameterMap(request), beanClass);
        } catch (Exception e) {
            throw new KitException(e);
        }
    }

    public static final <T> T inject(Class<T> beanClass, String prefix, HttpServletRequest request) {
        try {
            Map<String, Object> map = injectPara(prefix, request);
            return BeanKit.mapToBeanIgnoreCase(map, beanClass);
        } catch (Exception e) {
            throw new KitException(e);
        }
    }

    private static final Map<String, Object> injectPara(String prefix, HttpServletRequest request) {
        Map<String, String[]> paramMap = request.getParameterMap();
        Map<String, Object> map = Maps.newHashMap();
        String start = prefix.toLowerCase() + ".";
        String[] value = null;
        for (Entry<String, String[]> param : paramMap.entrySet()) {
            if (!param.getKey().toLowerCase().startsWith(start)) {
                continue;
            }
            value = param.getValue();
            Object o = null;
            if (CollectionKit.isNotEmpty(value)) {
                if (value.length > 1) {
                    o = CollectionKit.join(value, ",");
                } else {
                    o = value[0];
                }
            }
            map.put(StrKit.removePrefixIgnoreCase(param.getKey(), start).toLowerCase(), o);
        }
        return map;
    }

    public static final Map<String, Object> getParameterMap(HttpServletRequest request) {
        Map<String, String[]> paramMap = request.getParameterMap();
        Map<String, Object> map = Maps.newHashMap();
        String[] value;
        for (Entry<String, String[]> param : paramMap.entrySet()) {
            value = param.getValue();
            Object o = null;
            if (CollectionKit.isNotEmpty(value)) {
                if (value.length > 1) {
                    o = CollectionKit.join(value, ",");
                } else {
                    o = value[0];
                }
            }
            map.put(param.getKey(), o);
        }
        return map;
    }

}
