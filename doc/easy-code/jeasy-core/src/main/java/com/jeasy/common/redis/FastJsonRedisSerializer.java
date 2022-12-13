package com.jeasy.common.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.jeasy.common.Func;
import com.jeasy.common.charset.CharsetKit;
import com.jeasy.common.json.JsonKit;
import com.jeasy.common.str.StrKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.util.List;
import java.util.Map;

/**
 * redis fastjson序列化实现<br>
 * 速度较 jdk的序列化 、 反序列化慢<br>
 * 但是占用空间小，是jdk序列化反序列化的一半
 *
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
@Slf4j
public class FastJsonRedisSerializer implements RedisSerializer<Object> {

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
    public byte[] serialize(Object object) throws SerializationException {
        if (object == null) {
            return new byte[0];
        }
        try {
            Class<?> clazz = object.getClass();
//            if (this.isBaseDataType(clazz) == true) {
//                // 基础类型直接返回
//                String json = FastJSONUtils.toJsonString(object);
//                return json.getBytes(CharsetKit.DEFAULT_ENCODE);
//            } else {
            // 将类名和对象序列化数据，添加到entity中
            ClazzObjectMappingEntity clazzObjectMappingEntity;
            if (object instanceof List || object instanceof Map) {
                // class要写入
                clazzObjectMappingEntity = new ClazzObjectMappingEntity(clazz.getName(), JSON.toJSONString(object, FEATURES_2));
            } else {
                clazzObjectMappingEntity = new ClazzObjectMappingEntity(clazz.getName(), JSON.toJSONString(object, FEATURES_1));
            }
            // 将entity序列化
            return JsonKit.toJson(clazzObjectMappingEntity).getBytes(CharsetKit.DEFAULT_ENCODE);
//            }

        } catch (Exception e) {
            throw new SerializationException("序列化对象异常", e);
        }

    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        if (Func.isEmpty(bytes)) {
            return null;
        }
        try {
            // redis中取出的数据
            String stringTemp = new String(bytes, CharsetKit.DEFAULT_ENCODE);
            if (StrKit.startWith(stringTemp, "{\"a\":", true) || StrKit.startWith(stringTemp, "{\"b\":", true)) {
                try {
                    // 进行反序列化对象
                    ClazzObjectMappingEntity clazzObjectMappingEntity = JsonKit.fromJson(stringTemp, ClazzObjectMappingEntity.class);
                    return JsonKit.fromJson(clazzObjectMappingEntity.getB(), Class.forName(clazzObjectMappingEntity.getA()));
                } catch (Exception ex) {
                    log.error("反邓列化对象异常，string:" + stringTemp, ex);
                }
            }
            return stringTemp;
        } catch (Exception e) {
            throw new RuntimeException("反序列化对象异常", e);
        }
    }

    /**
     * 用于将class name 与 对象序列化的json数据对应实体
     */
    public static class ClazzObjectMappingEntity {

        /**
         * 对象名
         */
        private String a;
        /**
         * 对象序列化数据
         */
        private String b;

        public ClazzObjectMappingEntity() {

        }

        public ClazzObjectMappingEntity(String clazzName, String objectSerialJson) {
            a = clazzName;
            b = objectSerialJson;
        }

        /**
         * get a
         *
         * @return
         * @auth nibili 2015年5月14日
         */
        public String getA() {
            return a;
        }

        /**
         * get b
         *
         * @return
         * @auth nibili 2015年5月14日
         */
        public String getB() {
            return b;
        }

        /**
         * set 对象名
         *
         * @param a
         * @对象名uth nibili 2015年5月14日
         */
        public void setA(String a) {
            this.a = a;
        }

        /**
         * set 对象序列化数据
         *
         * @param b
         * @auth ni对象序列化数据ili 2015年5月14日
         */
        public void setB(String b) {
            this.b = b;
        }

    }

//    /**
//     * 是否是基础类型数据
//     *
//     * @param clazz
//     * @return
//     * @auth nibili 2015年5月14日
//     */
//    private boolean isBaseDataType(Class<?> clazz) {
//        return clazz.equals(String.class) || clazz.equals(Integer.class) || clazz.equals(Byte.class) || clazz.equals(Long.class)
//                || clazz.equals(Double.class) || clazz.equals(Float.class) || clazz.equals(Character.class) || clazz.equals(Short.class)
//                || clazz.equals(BigDecimal.class) || clazz.equals(BigInteger.class) || clazz.equals(Boolean.class);
//    }
}
