package com.jeasy.common.object;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.jeasy.common.ConvertKit;
import com.jeasy.common.Func;
import com.jeasy.common.clazz.ClassKit;
import com.jeasy.common.collection.CollectionKit;
import com.jeasy.common.str.StrKit;
import com.jeasy.exception.KitException;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.beans.BeanGenerator;
import org.springframework.cglib.beans.BeanMap;

import java.beans.*;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Bean工具类
 *
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
public class BeanKit extends org.springframework.beans.BeanUtils {

    /**
     * 判断是否为Bean对象
     *
     * @param clazz 待测试类
     * @return 是否为Bean对象
     */
    public static boolean isBean(Class<?> clazz) {
        if (ClassKit.isNormalClass(clazz)) {
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                if (method.getParameterTypes().length == 1 && method.getName().startsWith("set")) {
                    //检测包含标准的setXXX方法即视为标准的JavaBean
                    return true;
                }
            }
        }
        return false;
    }

    public static PropertyEditor findEditor(Class<?> type) {
        return PropertyEditorManager.findEditor(type);
    }

    /**
     * 获得Bean字段描述数组
     *
     * @param clazz Bean类
     * @return 字段描述数组
     * @throws IntrospectionException
     */
    public static PropertyDescriptor[] getPropertyDescriptors(Class<?> clazz) {
        try {
            return Introspector.getBeanInfo(clazz).getPropertyDescriptors();
        } catch (IntrospectionException e) {
            throw new KitException(e);
        }
    }

    /**
     * 获得字段名和字段描述Map
     *
     * @param clazz Bean类
     * @return 字段名和字段描述Map
     * @throws IntrospectionException
     */
    public static Map<String, PropertyDescriptor> getFieldNamePropertyDescriptorMap(Class<?> clazz) throws IntrospectionException {
        final PropertyDescriptor[] propertyDescriptors = getPropertyDescriptors(clazz);
        Map<String, PropertyDescriptor> map = Maps.newHashMap();
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            map.put(propertyDescriptor.getName(), propertyDescriptor);
        }
        return map;
    }

    /**
     * 获得Bean类属性描述
     *
     * @param clazz     Bean类
     * @param fieldName 字段名
     * @return PropertyDescriptor
     * @throws IntrospectionException
     */
    public static PropertyDescriptor getPropertyDescriptor(Class<?> clazz, final String fieldName) {
        PropertyDescriptor[] propertyDescriptors = getPropertyDescriptors(clazz);
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            if (ObjectKit.equals(fieldName, propertyDescriptor.getName())) {
                return propertyDescriptor;
            }
        }
        return null;
    }

    /**
     * Map转换为Bean对象
     *
     * @param map       Map
     * @param beanClass Bean Class
     * @return Bean
     */
    public static <T> T mapToBean(Map<?, ?> map, Class<T> beanClass) {
        return fillBeanWithMap(map, ClassKit.newInstance(beanClass));
    }

    /**
     * Map转换为Bean对象<br>
     * 忽略大小写
     *
     * @param map       Map
     * @param beanClass Bean Class
     * @return Bean
     */
    public static <T> T mapToBeanIgnoreCase(Map<?, ?> map, Class<T> beanClass) {
        return fillBeanWithMapIgnoreCase(map, ClassKit.newInstance(beanClass));
    }

    /**
     * 使用Map填充Bean对象
     *
     * @param map  Map
     * @param bean Bean
     * @return Bean
     */
    public static <T> T fillBeanWithMap(final Map<?, ?> map, T bean) {
        return fillBean(bean, new ValueProvider() {
            @Override
            public Object value(String name) {
                return map.get(name);
            }
        });
    }

    /**
     * 使用Map填充Bean对象，可配置将下划线转换为驼峰
     *
     * @param map           Map
     * @param bean          Bean
     * @param isToCamelCase 是否将下划线模式转换为驼峰模式
     * @return Bean
     */
    public static <T> T fillBeanWithMap(Map<?, ?> map, T bean, boolean isToCamelCase) {
        if (isToCamelCase) {
            final Map<Object, Object> map2 = Maps.newHashMap();
            for (Entry<?, ?> entry : map.entrySet()) {
                final Object key = entry.getKey();
                if (null != key && key instanceof String) {
                    final String keyStr = (String) key;
                    map2.put(StrKit.toCamelCase(keyStr), entry.getValue());
                } else {
                    map2.put(key, entry.getValue());
                }
            }
            return fillBeanWithMap(map2, bean);
        }

        return fillBeanWithMap(map, bean);
    }

    /**
     * 使用Map填充Bean对象，忽略大小写
     *
     * @param map  Map
     * @param bean Bean
     * @return Bean
     */
    public static <T> T fillBeanWithMapIgnoreCase(Map<?, ?> map, T bean) {
        final Map<Object, Object> map2 = Maps.newHashMap();
        for (Entry<?, ?> entry : map.entrySet()) {
            final Object key = entry.getKey();
            if (key instanceof String) {
                final String keyStr = (String) key;
                map2.put(keyStr.toLowerCase(), entry.getValue());
            } else {
                map2.put(key, entry.getValue());
            }
        }

        return fillBean(bean, new ValueProvider() {
            @Override
            public Object value(String name) {
                return map2.get(name.toLowerCase());
            }
        });
    }

    /**
     * ServletRequest 参数转Bean
     *
     * @param request   ServletRequest
     * @param beanClass Bean Class
     * @return Bean
     */
    public static <T> T requestParamToBean(javax.servlet.ServletRequest request, Class<T> beanClass) {
        return fillBeanWithRequestParam(request, ClassKit.newInstance(beanClass));
    }

    /**
     * ServletRequest 参数转Bean
     *
     * @param request ServletRequest
     * @param bean    Bean
     * @return Bean
     */
    public static <T> T fillBeanWithRequestParam(final javax.servlet.ServletRequest request, T bean) {
        final String beanName = StrKit.lowerFirst(bean.getClass().getSimpleName());
        return fillBean(bean, new ValueProvider() {
            @Override
            public Object value(String name) {
                String value = request.getParameter(name);
                if (StrKit.isEmpty(value)) {
                    // 使用类名前缀尝试查找值
                    value = request.getParameter(beanName + StrKit.S_DOT + name);
                    if (StrKit.isEmpty(value)) {
                        // 此处取得的值为空时跳过，包括null和StrKit.S_EMPTY
                        value = null;
                    }
                }
                return value;
            }
        });
    }

    /**
     * ServletRequest 参数转Bean
     *
     * @param <T>
     * @param beanClass     Bean Class
     * @param valueProvider 值提供者
     * @return Bean
     */
    public static <T> T toBean(Class<T> beanClass, ValueProvider valueProvider) {
        return fillBean(ClassKit.newInstance(beanClass), valueProvider);
    }

    /**
     * 填充Bean
     *
     * @param <T>
     * @param bean          Bean
     * @param valueProvider 值提供者
     * @return Bean
     */
    public static <T> T fillBean(T bean, ValueProvider valueProvider) {
        if (null == valueProvider) {
            return bean;
        }

        Class<?> beanClass = bean.getClass();
        try {
            PropertyDescriptor[] propertyDescriptors = getPropertyDescriptors(beanClass);
            String propertyName;
            Object value;
            for (PropertyDescriptor property : propertyDescriptors) {
                propertyName = property.getName();
                value = valueProvider.value(propertyName);
                if (null == value) {
                    continue;
                }
                try {
                    property.getWriteMethod().invoke(bean, ConvertKit.parse(property.getPropertyType(), value));
                } catch (Exception e) {
                    throw new KitException(e);
                }
            }
        } catch (Exception e) {
            throw new KitException(e);
        }
        return bean;
    }

    /**
     * 对象转Map
     *
     * @param bean bean对象
     * @return Map
     */
    public static <T> Map<String, Object> beanToMap(T bean) {
        return beanToMap(bean, false);
    }

    /**
     * 对象转Map
     *
     * @param bean              bean对象
     * @param isToUnderlineCase 是否转换为下划线模式
     * @return Map
     */
    public static <T> Map<String, Object> beanToMap(T bean, boolean isToUnderlineCase) {

        if (bean == null) {
            return null;
        }
        Map<String, Object> map = Maps.newHashMap();
        try {
            final PropertyDescriptor[] propertyDescriptors = getPropertyDescriptors(bean.getClass());
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                // 过滤class属性
                if (!StrKit.equals("class", key)) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(bean);
                    if (null != value) {
                        map.put(isToUnderlineCase ? StrKit.toUnderlineCase(key) : key, value);
                    }
                }
            }
        } catch (Exception e) {
            throw new KitException(e);
        }
        return map;
    }

    /**
     * 复制Bean对象属性
     *
     * @param source 源Bean对象
     * @param target 目标Bean对象
     */
    public static void copyProperties(Object source, Object target, AbstractConverter... converters) {
        copyProperties(source, target, CopyOptions.create().setConverters(converters));
    }

    /**
     * 复制Bean对象属性
     *
     * @param source 源Bean对象
     * @param target 目标Bean对象
     */
    public static void copyProperties(Object source, Object target) {
        copyProperties(source, target, CopyOptions.create());
    }

    /**
     * 复制Bean对象属性<br>
     * 限制类用于限制拷贝的属性，例如一个类我只想复制其父类的一些属性，就可以将editable设置为父类
     *
     * @param source           源Bean对象
     * @param target           目标Bean对象
     * @param ignoreProperties 不拷贝的的属性列表
     */
    public static void copyProperties(Object source, Object target, String... ignoreProperties) {
        copyProperties(source, target, CopyOptions.create().setIgnoreProperties(ignoreProperties));
    }

    /**
     * 复制Bean对象属性
     *
     * @param source       源Bean对象
     * @param target       目标Bean对象
     * @param isNullIgnore 忽略源对象里的null值,不进行copy
     */
    public static void copyProperties(Object source, Object target, boolean isNullIgnore) {
        copyProperties(source, target, CopyOptions.create(), isNullIgnore);
    }

    /**
     * 复制Bean对象属性<br>
     * 限制类用于限制拷贝的属性，例如一个类我只想复制其父类的一些属性，就可以将editable设置为父类
     *
     * @param source      源Bean对象
     * @param target      目标Bean对象
     * @param copyOptions 拷贝选项，见 {@link CopyOptions}
     */
    public static void copyProperties(Object source, Object target, CopyOptions copyOptions) {
        if (null == copyOptions) {
            copyOptions = new CopyOptions();
        }

        Class<?> actualEditable = target.getClass();
        if (copyOptions.editable != null) {
            //检查限制类是否为target的父类或接口
            if (!copyOptions.editable.isInstance(target)) {
                throw new IllegalArgumentException(StrKit.format("Target class [{}] not assignable to Editable class [{}]", target.getClass().getName(), copyOptions.editable.getName()));
            }
            actualEditable = copyOptions.editable;
        }

        PropertyDescriptor[] targetPds;
        Map<String, PropertyDescriptor> sourcePdMap;
        try {
            sourcePdMap = getFieldNamePropertyDescriptorMap(source.getClass());
            targetPds = getPropertyDescriptors(actualEditable);
        } catch (IntrospectionException e) {
            throw new KitException(e);
        }

        HashSet<String> ignoreSet = copyOptions.ignoreProperties != null ? Sets.newHashSet(copyOptions.ignoreProperties) : null;
        for (PropertyDescriptor targetPd : targetPds) {
            Method writeMethod = targetPd.getWriteMethod();
            boolean isFlag = Func.isNotEmpty(writeMethod) && (Func.isEmpty(ignoreSet) || !ignoreSet.contains(targetPd.getName()));
            if (isFlag) {
                AbstractConverter converter = null;
                PropertyDescriptor sourcePd;
                if (CollectionKit.isNotEmpty(copyOptions.converterMap) && copyOptions.converterMap.containsKey(targetPd.getName())) {
                    converter = copyOptions.converterMap.get(targetPd.getName());
                    sourcePd = sourcePdMap.get(converter.getFrom());
                } else {
                    sourcePd = sourcePdMap.get(targetPd.getName());
                }

                if (sourcePd != null) {
                    Method readMethod = sourcePd.getReadMethod();
                    if (!Func.isEmpty(readMethod)) {
                        try {
                            Object value = ClassKit.setAccessible(readMethod).invoke(source);
                            if (!Func.isEmpty(converter)) {
                                value = converter.convert(value);
                                if (!Func.isEmpty(value) || !copyOptions.isIgnoreNullValue) {
                                    ClassKit.setAccessible(writeMethod).invoke(target, value);
                                }
                            } else {
                                // 源对象字段的getter方法返回值必须可转换为目标对象setter方法的第一个参数
                                if (ClassKit.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType())) {
                                    if (!Func.isEmpty(value) || !copyOptions.isIgnoreNullValue) {
                                        ClassKit.setAccessible(writeMethod).invoke(target, value);
                                    }
                                }
                            }
                        } catch (Throwable ex) {
                            throw new KitException(ex, "Copy property [{}] to [{}] error: {}", sourcePd.getName(), targetPd.getName(), ex.getMessage());
                        }
                    }
                }
            }
        }
    }

    /**
     * 复制Bean对象属性<br>
     * 限制类用于限制拷贝的属性，例如一个类我只想复制其父类的一些属性，就可以将editable设置为父类
     *
     * @param source       源Bean对象
     * @param target       目标Bean对象
     * @param copyOptions  拷贝选项，见 {@link CopyOptions}
     * @param isNullIgnore 忽略源对象里的null值,不进行copy
     */
    public static void copyProperties(Object source, Object target, CopyOptions copyOptions, boolean isNullIgnore) {
        if (null == copyOptions) {
            copyOptions = new CopyOptions();
        }

        Class<?> actualEditable = target.getClass();
        if (copyOptions.editable != null) {
            //检查限制类是否为target的父类或接口
            if (!copyOptions.editable.isInstance(target)) {
                throw new IllegalArgumentException(StrKit.format("Target class [{}] not assignable to Editable class [{}]", target.getClass().getName(), copyOptions.editable.getName()));
            }
            actualEditable = copyOptions.editable;
        }

        PropertyDescriptor[] targetPds;
        Map<String, PropertyDescriptor> sourcePdMap;
        try {
            sourcePdMap = getFieldNamePropertyDescriptorMap(source.getClass());
            targetPds = getPropertyDescriptors(actualEditable);
        } catch (IntrospectionException e) {
            throw new KitException(e);
        }

        HashSet<String> ignoreSet = copyOptions.ignoreProperties != null ? Sets.newHashSet(copyOptions.ignoreProperties) : null;
        for (PropertyDescriptor targetPd : targetPds) {
            Method writeMethod = targetPd.getWriteMethod();
            boolean isNotIgnore = writeMethod != null && (ignoreSet == null || !ignoreSet.contains(targetPd.getName()));
            if (isNotIgnore) {
                AbstractConverter converter = null;
                PropertyDescriptor sourcePd;
                if (CollectionKit.isNotEmpty(copyOptions.converterMap) && copyOptions.converterMap.containsKey(targetPd.getName())) {
                    converter = copyOptions.converterMap.get(targetPd.getName());
                    sourcePd = sourcePdMap.get(converter.getFrom());
                } else {
                    sourcePd = sourcePdMap.get(targetPd.getName());
                }

                if (sourcePd != null) {
                    Method readMethod = sourcePd.getReadMethod();
                    if (!Func.isEmpty(readMethod)) {
                        try {
                            Object value = ClassKit.setAccessible(readMethod).invoke(source);
                            if (Func.isEmpty(value) && isNullIgnore) {
                                continue;
                            }
                            if (Func.isNotEmpty(converter)) {
                                value = converter.convert(value);
                                if (!Func.isEmpty(value) && !copyOptions.isIgnoreNullValue) {
                                    ClassKit.setAccessible(writeMethod).invoke(target, value);
                                }
                            } else {
                                // 源对象字段的getter方法返回值必须可转换为目标对象setter方法的第一个参数
                                if (ClassKit.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType())) {
                                    if (!Func.isEmpty(value) || !copyOptions.isIgnoreNullValue) {
                                        ClassKit.setAccessible(writeMethod).invoke(target, value);
                                    }
                                }
                            }
                        } catch (Throwable ex) {
                            throw new KitException(ex, "Copy property [{}] to [{}] error: {}", sourcePd.getName(), targetPd.getName(), ex.getMessage());
                        }
                    }
                }
            }
        }
    }

    /**
     * 实例化对象
     *
     * @param clazz 类
     * @return 对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T newInstance(Class<?> clazz) {
        return (T) instantiate(clazz);
    }

    /**
     * 实例化对象
     *
     * @param clazzStr 类名
     * @return 对象
     */
    public static <T> T newInstance(String clazzStr) {
        try {
            Class<?> clazz = Class.forName(clazzStr);
            return newInstance(clazz);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取Bean的属性
     *
     * @param bean         bean
     * @param propertyName 属性名
     * @return 属性值
     */
    public static Object getProperty(Object bean, String propertyName) {
        PropertyDescriptor pd = getPropertyDescriptor(bean.getClass(), propertyName);
        if (pd == null) {
            throw new RuntimeException("Could not read property '" + propertyName + "' from bean PropertyDescriptor is null");
        }
        Method readMethod = pd.getReadMethod();
        if (readMethod == null) {
            throw new RuntimeException("Could not read property '" + propertyName + "' from bean readMethod is null");
        }
        if (!readMethod.isAccessible()) {
            readMethod.setAccessible(true);
        }
        try {
            return readMethod.invoke(bean);
        } catch (Throwable ex) {
            throw new RuntimeException("Could not read property '" + propertyName + "' from bean", ex);
        }
    }

    /**
     * 设置Bean属性
     *
     * @param bean         bean
     * @param propertyName 属性名
     * @param value        属性值
     */
    public static void setProperty(Object bean, String propertyName, Object value) {
        PropertyDescriptor pd = getPropertyDescriptor(bean.getClass(), propertyName);
        if (pd == null) {
            throw new RuntimeException("Could not set property '" + propertyName + "' to bean PropertyDescriptor is null");
        }
        Method writeMethod = pd.getWriteMethod();
        if (writeMethod == null) {
            throw new RuntimeException("Could not set property '" + propertyName + "' to bean writeMethod is null");
        }
        if (!writeMethod.isAccessible()) {
            writeMethod.setAccessible(true);
        }
        try {
            writeMethod.invoke(bean, value);
        } catch (Throwable ex) {
            throw new RuntimeException("Could not set property '" + propertyName + "' to bean", ex);
        }
    }

    /**
     * 给一个Bean添加字段
     *
     * @param superBean 父级Bean
     * @param props     新增属性
     * @return {Object}
     */
    public static Object generator(Object superBean, BeanProperty... props) {
        Class<?> superclass = superBean.getClass();
        Object genBean = generator(superclass, props);
        copy(superBean, genBean);
        return genBean;
    }

    /**
     * 给一个class添加字段
     *
     * @param superclass 父级
     * @param props      新增属性
     * @return {Object}
     */
    public static Object generator(Class<?> superclass, BeanProperty... props) {
        BeanGenerator generator = new BeanGenerator();
        generator.setSuperclass(superclass);
        generator.setUseCache(true);
        for (BeanProperty prop : props) {
            generator.addProperty(prop.getName(), prop.getType());
        }
        return generator.create();
    }

    /**
     * copy 对象属性到另一个对象，默认不使用Convert
     *
     * @param src
     * @param clazz 类名
     * @return T
     */
    public static <T> T copy(Object src, Class<T> clazz) {
        BeanCopier copier = BeanCopier.create(src.getClass(), clazz, false);

        T to = newInstance(clazz);
        copier.copy(src, to, null);
        return to;
    }

    /**
     * 拷贝对象
     *
     * @param src  源对象
     * @param dist 需要赋值的对象
     */
    public static void copy(Object src, Object dist) {
        BeanCopier copier = BeanCopier
            .create(src.getClass(), dist.getClass(), false);

        copier.copy(src, dist, null);
    }

    /**
     * 将对象装成map形式
     *
     * @param src
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static Map toMap(Object src) {
        return BeanMap.create(src);
    }

    /**
     * 将map 转为 bean
     */
    public static <T> T toBean(Map<String, Object> beanMap, Class<T> valueType) {
        T bean = newInstance(valueType);
        PropertyDescriptor[] beanPds = getPropertyDescriptors(valueType);
        for (PropertyDescriptor propDescriptor : beanPds) {
            String propName = propDescriptor.getName();
            // 过滤class属性
            if (StrKit.equals("class", propName)) {
                continue;
            }
            if (beanMap.containsKey(propName)) {
                Method writeMethod = propDescriptor.getWriteMethod();
                if (null == writeMethod) {
                    continue;
                }
                Object value = beanMap.get(propName);
                if (!writeMethod.isAccessible()) {
                    writeMethod.setAccessible(true);
                }
                try {
                    writeMethod.invoke(bean, value);
                } catch (Throwable e) {
                    throw new RuntimeException("Could not set property '" + propName + "' to bean", e);
                }
            }
        }
        return bean;
    }

    /**
     * 值提供者，用于提供Bean注入时参数对应值得抽象接口<br>
     * 继承或匿名实例化此接口<br>
     * 在Bean注入过程中，Bean获得字段名，通过外部方式根据这个字段名查找相应的字段值，然后注入Bean<br>
     */
    public static interface ValueProvider {
        /**
         * 获取值
         *
         * @param name Bean对象中参数名
         * @return 对应参数名的值
         */
        public Object value(String name);
    }

    /**
     * 属性拷贝选项<br>
     * 包括：<br>
     * 1、限制的类或接口，必须为目标对象的实现接口或父类，用于限制拷贝的属性，例如一个类我只想复制其父类的一些属性，就可以将editable设置为父类<br>
     * 2、是否忽略空值，当源对象的值为null时，true: 忽略而不注入此值，false: 注入null<br>
     * 3、忽略的属性列表，设置一个属性列表，不拷贝这些属性值<br>
     *
     * @author Looly
     */
    public static class CopyOptions {
        /**
         * 限制的类或接口，必须为目标对象的实现接口或父类，用于限制拷贝的属性，例如一个类我只想复制其父类的一些属性，就可以将editable设置为父类
         */
        private Class<?> editable;
        /**
         * 是否忽略空值，当源对象的值为null时，true: 忽略而不注入此值，false: 注入null
         */
        private boolean isIgnoreNullValue;
        /**
         * 忽略的属性列表，设置一个属性列表，不拷贝这些属性值
         */
        private String[] ignoreProperties;
        /**
         * 转换器映射Map
         */
        private Map<String, AbstractConverter> converterMap;

        /**
         * 创建拷贝选项
         *
         * @return 拷贝选项
         */
        public static CopyOptions create() {
            return new CopyOptions();
        }

        /**
         * 创建拷贝选项
         *
         * @param editable          限制的类或接口，必须为目标对象的实现接口或父类，用于限制拷贝的属性
         * @param isIgnoreNullValue 是否忽略空值，当源对象的值为null时，true: 忽略而不注入此值，false: 注入null
         * @param ignoreProperties  忽略的属性列表，设置一个属性列表，不拷贝这些属性值
         * @return 拷贝选项
         */
        public static CopyOptions create(Class<?> editable, boolean isIgnoreNullValue, String... ignoreProperties) {
            return new CopyOptions(editable, isIgnoreNullValue, ignoreProperties);
        }

        /**
         * 构造拷贝选项
         */
        public CopyOptions() {
        }

        /**
         * 构造拷贝选项
         *
         * @param editable          限制的类或接口，必须为目标对象的实现接口或父类，用于限制拷贝的属性
         * @param isIgnoreNullValue 是否忽略空值，当源对象的值为null时，true: 忽略而不注入此值，false: 注入null
         * @param ignoreProperties  忽略的属性列表，设置一个属性列表，不拷贝这些属性值
         */
        public CopyOptions(Class<?> editable, boolean isIgnoreNullValue, String... ignoreProperties) {
            this.editable = editable;
            this.isIgnoreNullValue = isIgnoreNullValue;
            this.ignoreProperties = ignoreProperties;
        }

        /**
         * 设置限制的类或接口，必须为目标对象的实现接口或父类，用于限制拷贝的属性
         *
         * @param editable 限制的类或接口
         * @return CopyOptions
         */
        public CopyOptions setEditable(Class<?> editable) {
            this.editable = editable;
            return this;
        }

        /**
         * 设置是否忽略空值，当源对象的值为null时，true: 忽略而不注入此值，false: 注入null
         *
         * @param isIgnoreNullVall 是否忽略空值，当源对象的值为null时，true: 忽略而不注入此值，false: 注入null
         * @return CopyOptions
         */
        public CopyOptions setIgnoreNullValue(boolean isIgnoreNullVall) {
            this.isIgnoreNullValue = isIgnoreNullVall;
            return this;
        }

        /**
         * 设置忽略的属性列表，设置一个属性列表，不拷贝这些属性值
         *
         * @param ignoreProperties 忽略的属性列表，设置一个属性列表，不拷贝这些属性值
         * @return CopyOptions
         */
        public CopyOptions setIgnoreProperties(String... ignoreProperties) {
            this.ignoreProperties = ignoreProperties;
            return this;
        }

        /**
         * 设置属性转换器
         *
         * @param converters
         */
        public CopyOptions setConverters(AbstractConverter... converters) {
            this.converterMap = MapKit.transferConverterMap(converters, false);
            return this;
        }
    }
}
