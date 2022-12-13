package com.jeasy.common.redis;

import com.jeasy.common.io.IoKit;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 使用 sdk的序列 、反序列<br>
 * 序列化，反序列化 与fastjson相比较快，所以取数据较快<br>
 * 但是占用空间较大,是 fastjson策略的一倍多
 *
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
public class JdkRedisSerializer implements RedisSerializer<Object> {

    @Override
    public byte[] serialize(Object obj) throws SerializationException {
        if (obj == null) {
            return new byte[0];
        }
        byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray();
        } catch (Exception ex) {
            throw new SerializationException("序列化对象异常", ex);
        } finally {
            IoKit.close(oos, bos);
        }
        return bytes;
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        if (ArrayUtils.isEmpty(bytes)) {
            return null;
        }
        Object obj = null;
        ByteArrayInputStream bis = null;
        ObjectInputStream ois = null;
        try {
            bis = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bis);
            obj = ois.readObject();
        } catch (Exception ex) {
            throw new RuntimeException("反序列化对象异常", ex);
        } finally {
            IoKit.close(ois, bis);
        }
        return obj;
    }

}
