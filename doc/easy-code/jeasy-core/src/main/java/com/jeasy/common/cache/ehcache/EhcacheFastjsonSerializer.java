package com.jeasy.common.cache.ehcache;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.jeasy.common.json.JsonKit;
import com.jeasy.common.str.StrKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * ehcache对象序列化接口，fastjson的实现
 *
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
@Slf4j
public class EhcacheFastjsonSerializer implements IEhcacheSerializer {

    private static final SerializerFeature[] FEATURES_1 = {
        // list字段如果为null，输出为[]，而不是null
        SerializerFeature.WriteNullListAsEmpty,
        // 数值字段如果为null，输出为0，而不是null
        SerializerFeature.WriteNullNumberAsZero,
        // Boolean字段如果为null，输出为false，而不是null
        SerializerFeature.WriteNullBooleanAsFalse,
        // 字符类型字段如果为null，输出为StrKit.S_EMPTY，而不是null
        SerializerFeature.WriteNullStringAsEmpty,
        // 禁止以引用形式输出
        SerializerFeature.DisableCircularReferenceDetect
    };

    private static final SerializerFeature[] FEATURES_2 = {
        // list字段如果为null，输出为[]，而不是null
        SerializerFeature.WriteNullListAsEmpty,
        // 数值字段如果为null，输出为0，而不是null
        SerializerFeature.WriteNullNumberAsZero,
        // Boolean字段如果为null，输出为false，而不是null
        SerializerFeature.WriteNullBooleanAsFalse,
        // 字符类型字段如果为null，输出为StrKit.S_EMPTY，而不是null
        SerializerFeature.WriteNullStringAsEmpty,
        // 禁止以引用形式输出
        SerializerFeature.DisableCircularReferenceDetect,
        SerializerFeature.WriteClassName};

    @Override
    public String serialize(final Object object) throws Exception {

        if (object == null) {
            return StrKit.S_EMPTY;
        }
        try {
            Class<?> clazz = object.getClass();
            // if (this.isBaseDataType(clazz) == true) {
            // // 基础类型直接返回
            // String json = JSON.toJSONString(object);
            // return json;
            //
            // } else {

            // 将类名和对象序列化数据，添加到entity中
            ClazzObjectMappingEntity clazzObjectMappingEntity;
            if (object instanceof List || object instanceof Map) {
                // class要写入
                clazzObjectMappingEntity = new ClazzObjectMappingEntity(clazz.getName(), JSON.toJSONString(object, FEATURES_2));
            } else {
                clazzObjectMappingEntity = new ClazzObjectMappingEntity(clazz.getName(), JSON.toJSONString(object, FEATURES_1));
            }
            // 将entity序列化
            return JsonKit.toJson(clazzObjectMappingEntity);
            // }
        } catch (Exception e) {
            throw new Exception("序列化对象异常", e);
        }

    }

    @Override
    public Object deserialize(final String value) {
        if (StrKit.isBlank(value)) {
            return null;
        }
        try {
            // redis中取出的数据
            if (StrKit.startWith(value, "{\"a\":", true) || StrKit.startWith(value, "{\"b\":", true)) {
                try {
                    // 进行反序列化对象
                    ClazzObjectMappingEntity clazzObjectMappingEntity = JSON.parseObject(value, ClazzObjectMappingEntity.class);
                    return JsonKit.fromJson(clazzObjectMappingEntity.getB(), Class.forName(clazzObjectMappingEntity.getA()));
                } catch (Exception ex) {
                    log.error("反序列化对象异常，string:" + value, ex);
                }
            }
            return value;
        } catch (Exception e) {
            throw new RuntimeException("反序列化对象异常", e);
        }
    }

    /**
     * 用于将class name 与 对象序列化的json数据对应实体
     */
    public static class ClazzObjectMappingEntity {

        /**
         * 对象类名
         */
        private String a;
        /**
         * 对象序列化数据
         */
        private String b;

        public ClazzObjectMappingEntity() {

        }

        public ClazzObjectMappingEntity(final String clazzName, final String objectSerialJson) {
            this.a = clazzName;
            this.b = objectSerialJson;
        }

        /**
         * get 对象类名
         *
         * @return
         */
        public String getA() {
            return a;
        }

        /**
         * set 对象类名
         *
         * @param a
         */
        public void setA(final String a) {
            this.a = a;
        }

        /**
         * get 对象序列化数据
         *
         * @return
         */
        public String getB() {
            return b;
        }

        /**
         * set 对象序列化数据
         *
         * @param b
         */
        public void setB(final String b) {
            this.b = b;
        }

    }
}
