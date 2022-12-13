package com.jeasy.common.clazz;

import com.beust.jcommander.internal.Lists;
import com.google.common.collect.Sets;
import com.jeasy.common.Func;
import com.jeasy.common.charset.CharsetKit;
import com.jeasy.common.collection.CollectionKit;
import com.jeasy.common.file.FileKit;
import com.jeasy.common.io.IoKit;
import com.jeasy.common.str.StrKit;
import com.jeasy.common.support.BasicType;
import com.jeasy.common.support.Filter;
import com.jeasy.common.support.SingletonPool;
import com.jeasy.common.web.UrlKit;
import com.jeasy.exception.KitException;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.net.JarURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 类工具类
 * 1、扫描指定包下的所有类<br>
 *
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
public final class ClassKit {

    private static final String EQUALS = "equals";

    /**
     * 原始类型为Key，包装类型为Value，例如： int.class -> Integer.class.
     */
    private static final Map<Class<?>, Class<?>> WRAPPER_PRIMITIVE_MAP = new HashMap<>(8);
    /**
     * 包装类型为Key，原始类型为Value，例如： Integer.class -> int.class.
     */
    private static final Map<Class<?>, Class<?>> PRIMITIVE_WRAPPER_MAP = new HashMap<>(8);

    static {
        WRAPPER_PRIMITIVE_MAP.put(Boolean.class, boolean.class);
        WRAPPER_PRIMITIVE_MAP.put(Byte.class, byte.class);
        WRAPPER_PRIMITIVE_MAP.put(Character.class, char.class);
        WRAPPER_PRIMITIVE_MAP.put(Double.class, double.class);
        WRAPPER_PRIMITIVE_MAP.put(Float.class, float.class);
        WRAPPER_PRIMITIVE_MAP.put(Integer.class, int.class);
        WRAPPER_PRIMITIVE_MAP.put(Long.class, long.class);
        WRAPPER_PRIMITIVE_MAP.put(Short.class, short.class);

        for (Map.Entry<Class<?>, Class<?>> entry : WRAPPER_PRIMITIVE_MAP.entrySet()) {
            PRIMITIVE_WRAPPER_MAP.put(entry.getValue(), entry.getKey());
        }
    }

    private ClassKit() {
        // 静态类不可实例化
    }

    /**
     * 取得某个接口下所有实现这个接口的类
     */
    public static List<Class> getAllClassByType(final String basePackage, final Class c) {
        List<Class> resultClasses = new ArrayList<>();
        // 获取当前包下以及子包下所以的类
        List<Class<?>> allClass = getClasses(basePackage);
        if (allClass != null && !allClass.isEmpty()) {
            for (Class cl : allClass) {
                // 判断是否是同一个接口
                if (c.isAssignableFrom(cl)) {
                    // 本身不加入进去
                    if (!c.equals(cl)) {
                        resultClasses.add(cl);
                    }
                }
            }
        }
        return resultClasses;
    }

    /**
     * 从包package中获取所有的Class
     *
     * @param packageName
     * @return
     */
    public static List<Class<?>> getClasses(final String packageName) {
        String newPackageName = packageName;
        // 第一个class类的集合
        List<Class<?>> classes = new ArrayList<>();
        // 是否循环迭代
        boolean recursive = true;
        // 获取包的名字 并进行替换
        String packageDirName = newPackageName.replace('.', '/');
        // 定义一个枚举的集合 并进行循环来处理这个目录下的things
        Enumeration<URL> dirs;
        try {
            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
            // 循环迭代下去
            while (dirs.hasMoreElements()) {
                // 获取下一个元素
                URL url = dirs.nextElement();
                // 得到协议的名称
                String protocol = url.getProtocol();
                // 如果是以文件的形式保存在服务器上
                if ("file".equals(protocol)) {
                    // 获取包的物理路径
                    String filePath = URLDecoder.decode(url.getFile(), CharsetKit.DEFAULT_ENCODE);
                    // 以文件的方式扫描整个包下的文件 并添加到集合中
                    findAndAddClassesInPackageByFile(newPackageName, filePath, recursive, classes);
                } else if ("jar".equals(protocol)) {
                    // 如果是jar包文件 定义一个JarFile
                    JarFile jar = ((JarURLConnection) url.openConnection()).getJarFile();
                    // 从此jar包 得到一个枚举类
                    Enumeration<JarEntry> entries = jar.entries();
                    // 同样的进行循环迭代
                    while (entries.hasMoreElements()) {
                        // 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
                        JarEntry entry = entries.nextElement();
                        String name = entry.getName();
                        // 如果是以/开头的
                        if (name.charAt(0) == '/') {
                            //获取后面的字符串
                            name = name.substring(1);
                        }
                        // 如果前半部分和定义的包名相同
                        if (name.startsWith(packageDirName)) {
                            int idx = name.lastIndexOf('/');
                            // 如果以"/"结尾 是一个包
                            if (idx != -1) {
                                // 获取包名 把"/"替换成"."
                                newPackageName = name.substring(0, idx).replace('/', '.');
                            }
                            // 如果可以迭代下去 并且是一个包
                            // 如果是一个.class文件 而且不是目录
                            if (name.endsWith(".class") && !entry.isDirectory()) {
                                // 去掉后面的".class" 获取真正的类名
                                String className = name.substring(newPackageName.length() + 1, name.length() - 6);
                                try {
                                    //添加到classes
                                    classes.add(Class.forName(newPackageName + '.' + className));
                                } catch (ClassNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return classes;
    }

    /**
     * 以文件的形式来获取包下的所有Class
     *
     * @param packageName
     * @param packagePath
     * @param recursive
     * @param classes
     */
    public static void findAndAddClassesInPackageByFile(final String packageName, final String packagePath, final boolean recursive, final List<Class<?>> classes) {
        // 获取此包的目录 建立一个File
        File dir = new File(packagePath);
        // 如果不存在或者 也不是目录就直接返回
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        // 如果存在 就获取包下的所有文件 包括目录
        File[] dirfiles = dir.listFiles(new FileFilter() {
            // 自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
            @Override
            public boolean accept(File file) {
                return (recursive && file.isDirectory()) || (file.getName().endsWith(".class"));
            }
        });
        if (Func.isEmpty(dirfiles)) {
            return;
        }
        // 循环所有文件
        for (File file : dirfiles) {
            // 如果是目录 则继续扫描
            if (file.isDirectory()) {
                findAndAddClassesInPackageByFile(packageName + "." + file.getName(),
                    file.getAbsolutePath(),
                    recursive,
                    classes);
            } else {
                // 如果是java类文件 去掉后面的.class 只留下类名
                String className = file.getName().substring(0, file.getName().length() - 6);
                try {
                    // 添加到集合中去
                    classes.add(Class.forName(packageName + '.' + className));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获得对象数组的类数组
     *
     * @param objects 对象数组
     * @return 类数组
     */
    public static Class<?>[] getClasses(final Object... objects) {
        Class<?>[] classes = new Class<?>[objects.length];
        for (int i = 0; i < objects.length; i++) {
            classes[i] = objects[i].getClass();
        }
        return classes;
    }

    /**
     * 获得泛型的第一个类
     *
     * @param clazz 当前类
     * @return 泛型类
     */
    @SuppressWarnings("rawtypes")
    public static Class getSuperClassGenricFirstType(final Class clazz) {
        return getSuperClassGenricType(clazz, 0);
    }

    /**
     * 获得泛型的类
     *
     * @param clazz 当前类
     * @param index 泛型顺序
     * @return 泛型类
     */
    @SuppressWarnings("rawtypes")
    public static Class getSuperClassGenricType(final Class clazz, final int index) {
        Type t = clazz.getGenericSuperclass();
        if (!(t instanceof ParameterizedType)) {
            return null;
        }
        ParameterizedType parameterizedType = (ParameterizedType) t;
        Type[] params = parameterizedType.getActualTypeArguments();
        if (null == params) {
            return null;
        }
        if (index < 0 || index > params.length - 1) {
            return null;
        }
        Type param = params[index];
        if (param instanceof Class) {
            return (Class) param;
        }
        return null;
    }

    /**
     * 扫面该包路径下所有class文件
     *
     * @return 类集合
     */
    public static Set<Class<?>> scanPackage() {
        return scanPackage(StrKit.S_EMPTY, null);
    }

    /**
     * 扫面该包路径下所有class文件
     *
     * @param packageName 包路径 com | com. | com.abs | com.abs.
     * @return 类集合
     */
    public static Set<Class<?>> scanPackage(final String packageName) {
        return scanPackage(packageName, null);
    }

    /**
     * 扫描指定包路径下所有包含指定注解的类
     *
     * @param packageName     包路径
     * @param annotationClass 注解类
     * @return 类集合
     */
    public static Set<Class<?>> scanPackageByAnnotation(final String packageName, final Class<? extends Annotation> annotationClass) {
        return scanPackage(packageName, new ClassFilter() {
            @Override
            public boolean accept(Class<?> clazz) {
                return clazz.isAnnotationPresent(annotationClass);
            }
        });
    }

    /**
     * 扫描指定包路径下所有指定类的子类
     *
     * @param packageName 包路径
     * @param superClass  父类
     * @return 类集合
     */
    public static Set<Class<?>> scanPackageBySuper(final String packageName, final Class<?> superClass) {
        return scanPackage(packageName, new ClassFilter() {
            @Override
            public boolean accept(Class<?> clazz) {
                return superClass.isAssignableFrom(clazz) && !superClass.equals(clazz);
            }
        });
    }

    /**
     * 扫面包路径下满足class过滤器条件的所有class文件，</br>
     * 如果包路径为 com.abs + A.class 但是输入 abs会产生classNotFoundException</br>
     * 因为className 应该为 com.abs.A 现在却成为abs.A,此工具类对该异常进行忽略处理,有可能是一个不完善的地方，以后需要进行修改</br>
     *
     * @param packageName 包路径 com | com. | com.abs | com.abs.
     * @param classFilter class过滤器，过滤掉不需要的class
     * @return 类集合
     */
    public static Set<Class<?>> scanPackage(final String packageName, final ClassFilter classFilter) {
        String newPackageName = packageName;
        if (StrKit.isBlank(newPackageName)) {
            newPackageName = StrKit.S_EMPTY;
        }
        newPackageName = getWellFormedPackageName(newPackageName);

        final Set<Class<?>> classes = new HashSet<Class<?>>();
        for (String classPath : getClassPaths(newPackageName)) {
            //bug修复，由于路径中空格和中文导致的Jar找不到
            classPath = UrlKit.decode(classPath, CharsetKit.systemCharset());
            // 填充 classes
            fillClasses(classPath, newPackageName, classFilter, classes);
        }

        //如果在项目的ClassPath中未找到，去系统定义的ClassPath里找
        if (classes.isEmpty()) {
            for (String classPath : getJavaClassPaths()) {
                //bug修复，由于路径中空格和中文导致的Jar找不到
                classPath = UrlKit.decode(classPath, CharsetKit.systemCharset());

                // 填充 classes
                fillClasses(classPath, new File(classPath), newPackageName, classFilter, classes);
            }
        }
        return classes;
    }

    // ----------------------------------------------------------------------------------------- Method

    /**
     * 获得指定类中的Public方法名<br>
     * 去重重载的方法
     *
     * @param clazz 类
     */
    public static Set<String> getPublicMethodNames(final Class<?> clazz) {
        HashSet<String> methodSet = new HashSet<String>();
        Method[] methodArray = getPublicMethods(clazz);
        for (Method method : methodArray) {
            String methodName = method.getName();
            methodSet.add(methodName);
        }
        return methodSet;
    }

    /**
     * 获得本类及其父类所有Public方法
     *
     * @param clazz 查找方法的类
     * @return 过滤后的方法列表
     */
    public static Method[] getPublicMethods(final Class<?> clazz) {
        return clazz.getMethods();
    }

    /**
     * 获得指定类过滤后的Public方法列表
     *
     * @param clazz  查找方法的类
     * @param filter 过滤器
     * @return 过滤后的方法列表
     */
    public static List<Method> getPublicMethods(final Class<?> clazz, final Filter<Method> filter) {
        if (null == clazz) {
            return null;
        }

        Method[] methods = getPublicMethods(clazz);
        List<Method> methodList;
        if (null != filter) {
            methodList = new ArrayList<>();
            Method filteredMethod;
            for (Method method : methods) {
                filteredMethod = filter.modify(method);
                if (null != filteredMethod) {
                    methodList.add(method);
                }
            }
        } else {
            methodList = Lists.newArrayList(methods);
        }
        return methodList;
    }

    /**
     * 获得指定类过滤后的Public方法列表
     *
     * @param clazz          查找方法的类
     * @param excludeMethods 不包括的方法
     * @return 过滤后的方法列表
     */
    public static List<Method> getPublicMethods(final Class<?> clazz, final Method... excludeMethods) {
        final HashSet<Method> excludeMethodSet = Sets.newHashSet(excludeMethods);
        Filter<Method> filter = new Filter<Method>() {
            @Override
            public Method modify(Method method) {
                return excludeMethodSet.contains(method) ? null : method;
            }
        };

        return getPublicMethods(clazz, filter);
    }

    /**
     * 获得指定类过滤后的Public方法列表
     *
     * @param clazz              查找方法的类
     * @param excludeMethodNames 不包括的方法名列表
     * @return 过滤后的方法列表
     */
    public static List<Method> getPublicMethods(final Class<?> clazz, final String... excludeMethodNames) {
        final HashSet<String> excludeMethodNameSet = Sets.newHashSet(excludeMethodNames);
        Filter<Method> filter = new Filter<Method>() {
            @Override
            public Method modify(Method method) {
                return excludeMethodNameSet.contains(method.getName()) ? null : method;
            }
        };

        return getPublicMethods(clazz, filter);
    }

    /**
     * 查找指定Public方法
     *
     * @param clazz      类
     * @param methodName 方法名
     * @param paramTypes 参数类型
     * @return 方法
     * @throws SecurityException
     * @throws NoSuchMethodException
     */
    public static Method getPublicMethod(final Class<?> clazz, final String methodName, final Class<?>... paramTypes) throws NoSuchMethodException, SecurityException {
        try {
            return clazz.getMethod(methodName, paramTypes);
        } catch (NoSuchMethodException ex) {
            return getDeclaredMethod(clazz, methodName, paramTypes);
        }
    }

    /**
     * 获得指定类中的Public方法名<br>
     * 去重重载的方法
     *
     * @param clazz 类
     */
    public static Set<String> getDeclaredMethodNames(final Class<?> clazz) {
        HashSet<String> methodSet = new HashSet<String>();
        Method[] methodArray = getDeclaredMethods(clazz);
        for (Method method : methodArray) {
            String methodName = method.getName();
            methodSet.add(methodName);
        }
        return methodSet;
    }

    /**
     * 获得声明的所有方法，包括本类及其父类和接口的所有方法和Object类的方法
     *
     * @param clazz 类
     * @return 方法数组
     */
    public static Method[] getDeclaredMethods(final Class<?> clazz) {
        Class<?> newClazz = clazz;
        Set<Method> methodSet = new HashSet<>();
        Method[] declaredMethods;
        for (; null != newClazz; newClazz = newClazz.getSuperclass()) {
            declaredMethods = newClazz.getDeclaredMethods();
            for (Method method : declaredMethods) {
                methodSet.add(method);
            }
        }
        return methodSet.toArray(new Method[methodSet.size()]);
    }

    /**
     * 查找指定对象中的所有方法（包括非public方法），也包括父对象和Object类的方法
     *
     * @param obj        被查找的对象
     * @param methodName 方法名
     * @param args       参数
     * @return 方法
     * @throws NoSuchMethodException 无此方法
     * @throws SecurityException
     */
    public static Method getDeclaredMethod(final Object obj, final String methodName, final Object... args) throws NoSuchMethodException, SecurityException {
        return getDeclaredMethod(obj.getClass(), methodName, getClasses(args));
    }

    /**
     * 查找指定类中的所有方法（包括非public方法），也包括父类和Object类的方法
     *
     * @param clazz          被查找的类
     * @param methodName     方法名
     * @param parameterTypes 参数类型
     * @return 方法
     * @throws NoSuchMethodException 无此方法
     * @throws SecurityException
     */
    public static Method getDeclaredMethod(final Class<?> clazz, final String methodName, final Class<?>... parameterTypes) throws NoSuchMethodException, SecurityException {
        Method method = null;
        Class<?> newClazz = clazz;
        for (; null != newClazz; newClazz = newClazz.getSuperclass()) {
            try {
                method = newClazz.getDeclaredMethod(methodName, parameterTypes);
                break;
            } catch (NoSuchMethodException e) {
                // 继续向上寻找
            }
        }
        return method;
    }

    /**
     * 是否为equals方法
     *
     * @param method 方法
     * @return 是否为equals方法
     */
    public static boolean isEqualsMethod(final Method method) {
        if (Func.isEmpty(method) || !StrKit.equals(EQUALS, method.getName())) {
            return false;
        }
        Class<?>[] paramTypes = method.getParameterTypes();
        return (paramTypes.length == 1 && paramTypes[0] == Object.class);
    }

    /**
     * 是否为hashCode方法
     *
     * @param method 方法
     * @return 是否为hashCode方法
     */
    public static boolean isHashCodeMethod(final Method method) {
        return (Func.isNotEmpty(method) && StrKit.equals("hashCode", method.getName()) && method.getParameterTypes().length == 0);
    }

    /**
     * 是否为toString方法
     *
     * @param method 方法
     * @return 是否为toString方法
     */
    public static boolean isToStringMethod(final Method method) {
        return (Func.isNotEmpty(method) && StrKit.equals("toString", method.getName()) && method.getParameterTypes().length == 0);
    }

    // ----------------------------------------------------------------------------------------- Classpath

    /**
     * 获得ClassPath
     *
     * @return ClassPath集合
     */
    public static Set<String> getClassPathResources() {
        return getClassPaths(StrKit.S_EMPTY);
    }

    /**
     * 获得ClassPath
     *
     * @param packageName 包名称
     * @return ClassPath路径字符串集合
     */
    public static Set<String> getClassPaths(final String packageName) {
        String packagePath = packageName.replace(StrKit.S_DOT, StrKit.S_SLASH);
        Enumeration<URL> resources;
        try {
            resources = getClassLoader().getResources(packagePath);
        } catch (IOException e) {
            throw new KitException(StrKit.format("Loading classPath [{}] error!", packagePath), e);
        }
        Set<String> paths = new HashSet<String>();
        while (resources.hasMoreElements()) {
            paths.add(resources.nextElement().getPath());
        }
        return paths;
    }

    /**
     * 获得ClassPath
     *
     * @return ClassPath
     */
    public static String getClassPath() {
        return getClassPathURL().getPath();
    }

    /**
     * 获得ClassPath URL
     *
     * @return ClassPath URL
     */
    public static URL getClassPathURL() {
        return getURL(StrKit.S_EMPTY);
    }

    /**
     * 获得资源的URL
     *
     * @param resource 资源（相对Classpath的路径）
     * @return 资源URL
     */
    public static URL getURL(final String resource) {
        return ClassKit.getClassLoader().getResource(resource);
    }

    /**
     * @return 获得Java ClassPath路径，不包括 jre
     */
    public static String[] getJavaClassPaths() {
        return System.getProperty("java.class.path").split(System.getProperty("path.separator"));
    }

    /**
     * 转换为原始类型
     *
     * @param clazz 被转换为原始类型的类，必须为包装类型的类
     * @return 基本类型类
     */
    public static Class<?> castToPrimitive(final Class<?> clazz) {
        if (null == clazz || clazz.isPrimitive()) {
            return clazz;
        }

        BasicType basicType;
        try {
            basicType = BasicType.valueOf(clazz.getSimpleName().toUpperCase());
        } catch (Exception e) {
            return clazz;
        }

        // 基本类型
        switch (basicType) {
            case BYTE:
                return byte.class;
            case SHORT:
                return short.class;
            case INTEGER:
                return int.class;
            case LONG:
                return long.class;
            case DOUBLE:
                return double.class;
            case FLOAT:
                return float.class;
            case BOOLEAN:
                return boolean.class;
            case CHAR:
                return char.class;
            default:
                return clazz;
        }
    }

    /**
     * @return 当前线程的class loader
     */
    public static ClassLoader getContextClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * 获得class loader<br>
     * 若当前线程class loader不存在，取当前类的class loader
     *
     * @return 类加载器
     */
    public static ClassLoader getClassLoader() {
        ClassLoader classLoader = getContextClassLoader();
        if (classLoader == null) {
            classLoader = ClassKit.class.getClassLoader();
        }
        return classLoader;
    }

    /**
     * 实例化对象
     *
     * @param clazz 类名
     * @return 对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T newInstance(final String clazz) {
        if (null == clazz) {
            return null;
        }
        try {
            return (T) Class.forName(clazz).newInstance();
        } catch (Exception e) {
            throw new KitException(StrKit.format("Instance class [{}] error!", clazz), e);
        }
    }

    /**
     * 实例化对象
     *
     * @param clazz 类
     * @return 对象
     */
    public static <T> T newInstance(final Class<T> clazz) {
        if (null == clazz) {
            return null;
        }
        try {
            return (T) clazz.newInstance();
        } catch (Exception e) {
            throw new KitException(StrKit.format("Instance class [{}] error!", clazz), e);
        }
    }

    /**
     * 实例化对象
     *
     * @param clazz 类
     * @return 对象
     */
    public static <T> T newInstance(final Class<T> clazz, final Object... params) {
        if (null == clazz) {
            return null;
        }
        if (CollectionKit.isEmpty(params)) {
            return newInstance(clazz);
        }
        try {
            return clazz.getDeclaredConstructor(getClasses(params)).newInstance(params);
        } catch (Exception e) {
            throw new KitException(StrKit.format("Instance class [{}] error!", clazz), e);
        }
    }

    /**
     * 加载类
     *
     * @param <T>
     * @param className     类名
     * @param isInitialized 是否初始化
     * @return 类
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T> loadClass(final String className, final boolean isInitialized) {
        Class<T> clazz;
        try {
            clazz = (Class<T>) Class.forName(className, isInitialized, getClassLoader());
        } catch (ClassNotFoundException e) {
            throw new KitException(e);
        }
        return clazz;
    }

    /**
     * 加载类并初始化
     *
     * @param <T>
     * @param className 类名
     * @return 类
     */
    public static <T> Class<T> loadClass(final String className) {
        return loadClass(className, true);
    }

    // ---------------------------------------------------------------------------------------------------- Invoke start

    /**
     * 执行方法<br>
     * 可执行Private方法，也可执行static方法<br>
     * 执行非static方法时，必须满足对象有默认构造方法<br>
     * 非单例模式，如果是非静态方法，每次创建一个新对象
     *
     * @param <T>
     * @param classNameDotMethodName 类名和方法名表达式，例如：com.xiaoleilu.hutool.StrKit.isEmpty
     * @param args                   参数，必须严格对应指定方法的参数类型和数量
     * @return 返回结果
     */
    public static <T> T invoke(final String classNameDotMethodName, final Object[] args) {
        return invoke(classNameDotMethodName, false, args);
    }

    /**
     * 执行方法<br>
     * 可执行Private方法，也可执行static方法<br>
     * 执行非static方法时，必须满足对象有默认构造方法<br>
     *
     * @param <T>
     * @param classNameDotMethodName 类名和方法名表达式，例如：com.xiaoleilu.hutool.StrKit.isEmpty
     * @param isSingleton            是否为单例对象，如果此参数为false，每次执行方法时创建一个新对象
     * @param args                   参数，必须严格对应指定方法的参数类型和数量
     * @return 返回结果
     */
    public static <T> T invoke(final String classNameDotMethodName, final boolean isSingleton, final Object[] args) {
        if (StrKit.isBlank(classNameDotMethodName)) {
            throw new KitException("Blank classNameDotMethodName!");
        }
        final int dotIndex = classNameDotMethodName.lastIndexOf('.');
        if (dotIndex <= 0) {
            throw new KitException("Invalid classNameDotMethodName [{}]!", classNameDotMethodName);
        }

        final String className = classNameDotMethodName.substring(0, dotIndex);
        final String methodName = classNameDotMethodName.substring(dotIndex + 1);

        return invoke(className, methodName, isSingleton, args);
    }

    /**
     * 执行方法<br>
     * 可执行Private方法，也可执行static方法<br>
     * 执行非static方法时，必须满足对象有默认构造方法<br>
     * 非单例模式，如果是非静态方法，每次创建一个新对象
     *
     * @param <T>
     * @param className  类名，完整类路径
     * @param methodName 方法名
     * @param args       参数，必须严格对应指定方法的参数类型和数量
     * @return 返回结果
     */
    public static <T> T invoke(final String className, final String methodName, final Object[] args) {
        return invoke(className, methodName, false, args);
    }

    /**
     * 执行方法<br>
     * 可执行Private方法，也可执行static方法<br>
     * 执行非static方法时，必须满足对象有默认构造方法<br>
     *
     * @param <T>
     * @param className   类名，完整类路径
     * @param methodName  方法名
     * @param isSingleton 是否为单例对象，如果此参数为false，每次执行方法时创建一个新对象
     * @param args        参数，必须严格对应指定方法的参数类型和数量
     * @return 返回结果
     */
    public static <T> T invoke(final String className, final String methodName, final boolean isSingleton, final Object[] args) {
        Class<Object> clazz = loadClass(className);
        try {
            return invoke(isSingleton ? SingletonPool.create(clazz) : clazz.newInstance(), methodName, args);
        } catch (Exception e) {
            throw new KitException(e);
        }
    }

    /**
     * 执行方法<br>
     * 可执行Private方法，也可执行static方法<br>
     *
     * @param <T>
     * @param obj        对象
     * @param methodName 方法名
     * @param args       参数，必须严格对应指定方法的参数类型和数量
     * @return 返回结果
     */
    public static <T> T invoke(final Object obj, final String methodName, final Object[] args) {
        try {
            final Method method = getDeclaredMethod(obj, methodName, args);
            return invoke(obj, method, args);
        } catch (Exception e) {
            throw new KitException(e);
        }
    }

    /**
     * 执行方法
     *
     * @param obj    对象
     * @param method 方法（对象方法或static方法都可）
     * @param args   参数对象
     * @return 结果
     * @throws KitException              IllegalAccessException and IllegalArgumentException
     * @throws InvocationTargetException
     */
    @SuppressWarnings("unchecked")
    public static <T> T invoke(final Object obj, final Method method, final Object[] args) throws InvocationTargetException {
        if (!method.isAccessible()) {
            method.setAccessible(true);
        }
        try {
            return (T) method.invoke(isStatic(method) ? null : obj, args);
        } catch (IllegalAccessException | IllegalArgumentException e) {
            throw new KitException(e);
        }
    }
    // ---------------------------------------------------------------------------------------------------- Invoke end

    /**
     * 新建代理对象<br>
     * 动态代理类对象用于动态创建一个代理对象，可以在调用接口方法的时候动态执行相应逻辑
     *
     * @param interfaceClass    被代理接口
     * @param invocationHandler 代理执行类，此类用于实现具体的接口方法
     * @return 被代理接口
     */
    @SuppressWarnings("unchecked")
    public static <T> T newProxyInstance(final Class<T> interfaceClass, final InvocationHandler invocationHandler) {
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[]{interfaceClass}, invocationHandler);
    }

    /**
     * 新建代理对象(cglib)<br>
     * 动态代理类对象用于动态创建一个代理对象，可以在调用接口方法的时候动态执行相应逻辑
     *
     * @param clazz             被代理类
     * @param methodInterceptor 代理执行类，此类用于实现具体的接口方法
     * @return 被代理类
     */
    @SuppressWarnings("unchecked")
    public static <T> T newProxyCglibFactory(final Class<T> clazz, final MethodInterceptor methodInterceptor) {
        try {
            Class<?> superClass = Class.forName(clazz.getName());
            Enhancer hancer = new Enhancer();
            hancer.setSuperclass(superClass);
            hancer.setCallback(methodInterceptor);
            return (T) hancer.create();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 是否为包装类型
     *
     * @param clazz 类
     * @return 是否为包装类型
     */
    public static boolean isPrimitiveWrapper(final Class<?> clazz) {
        if (null == clazz) {
            return false;
        }
        return WRAPPER_PRIMITIVE_MAP.containsKey(clazz);
    }

    /**
     * 是否为基本类型（包括包装类和原始类）
     *
     * @param clazz 类
     * @return 是否为基本类型
     */
    public static boolean isBasicType(final Class<?> clazz) {
        if (null == clazz) {
            return false;
        }
        return (clazz.isPrimitive() || isPrimitiveWrapper(clazz));
    }

    /**
     * 是否简单值类型或简单值类型的数组<br>
     * 包括：原始类型,、String、other CharSequence, a Number, a Date, a URI, a URL, a Locale or a Class及其数组
     *
     * @param clazz 属性类
     * @return 是否简单值类型或简单值类型的数组
     */
    public static boolean isSimpleTypeOrArray(final Class<?> clazz) {
        if (null == clazz) {
            return false;
        }
        return isSimpleValueType(clazz) || (clazz.isArray() && isSimpleValueType(clazz.getComponentType()));
    }

    /**
     * 是否为简单值类型<br>
     * 包括：原始类型,、String、other CharSequence, a Number, a Date, a URI, a URL, a Locale or a Class.
     *
     * @param clazz 类
     * @return 是否为简单值类型
     */
    public static boolean isSimpleValueType(final Class<?> clazz) {
        return isBasicType(clazz) || clazz.isEnum() || CharSequence.class.isAssignableFrom(clazz) || Number.class.isAssignableFrom(clazz) || Date.class.isAssignableFrom(clazz) || clazz
            .equals(URI.class) || clazz.equals(URL.class) || clazz.equals(Locale.class) || clazz.equals(Class.class);
    }

    /**
     * 检查目标类是否可以从原类转化<br>
     * 转化包括：<br>
     * 1、原类是对象，目标类型是原类型实现的接口<br>
     * 2、目标类型是原类型的父类<br>
     * 3、两者是原始类型或者包装类型（相互转换）
     *
     * @param targetType 目标类型
     * @param sourceType 原类型
     * @return 是否可转化
     */
    public static boolean isAssignable(final Class<?> targetType, final Class<?> sourceType) {
        if (null == targetType || null == sourceType) {
            return false;
        }

        // 对象类型
        if (targetType.isAssignableFrom(sourceType)) {
            return true;
        }

        // 基本类型
        if (targetType.isPrimitive()) {
            // 原始类型
            Class<?> resolvedPrimitive = WRAPPER_PRIMITIVE_MAP.get(sourceType);
            if (resolvedPrimitive != null && targetType.equals(resolvedPrimitive)) {
                return true;
            }
        } else {
            // 包装类型
            Class<?> resolvedWrapper = PRIMITIVE_WRAPPER_MAP.get(sourceType);
            if (resolvedWrapper != null && targetType.isAssignableFrom(resolvedWrapper)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 指定类是否为Public
     *
     * @param clazz 类
     * @return 是否为public
     */
    public static boolean isPublic(final Class<?> clazz) {
        if (null == clazz) {
            throw new NullPointerException("Class to provided is null.");
        }
        return Modifier.isPublic(clazz.getModifiers());
    }

    /**
     * 指定方法是否为Public
     *
     * @param method 方法
     * @return 是否为public
     */
    public static boolean isPublic(final Method method) {
        if (null == method) {
            throw new NullPointerException("Method to provided is null.");
        }
        return isPublic(method.getDeclaringClass());
    }

    /**
     * 指定类是否为非public
     *
     * @param clazz 类
     * @return 是否为非public
     */
    public static boolean isNotPublic(final Class<?> clazz) {
        return !isPublic(clazz);
    }

    /**
     * 指定方法是否为非public
     *
     * @param method 方法
     * @return 是否为非public
     */
    public static boolean isNotPublic(final Method method) {
        return !isPublic(method);
    }

    /**
     * 是否为静态方法
     *
     * @param method 方法
     * @return 是否为静态方法
     */
    public static boolean isStatic(final Method method) {
        return Modifier.isStatic(method.getModifiers());
    }

    /**
     * 设置方法为可访问
     *
     * @param method 方法
     * @return 方法
     */
    public static Method setAccessible(final Method method) {
        if (null != method && ClassKit.isNotPublic(method)) {
            method.setAccessible(true);
        }
        return method;
    }

    /**
     * 是否为抽象类
     *
     * @param clazz 类
     * @return 是否为抽象类
     */
    public static boolean isAbstract(final Class<?> clazz) {
        return Modifier.isAbstract(clazz.getModifiers());
    }

    /**
     * 是否为标准的类<br>
     * 这个类必须：<br>
     * 1、非接口 2、非抽象类 3、非Enum枚举 4、非数组 5、非注解 6、非原始类型（int, long等）
     *
     * @param clazz 类
     * @return 是否为标准类
     */
    public static boolean isNormalClass(final Class<?> clazz) {
        return null != clazz && !clazz.isInterface() && !isAbstract(clazz) && !clazz.isEnum() && !clazz.isArray() && !clazz.isAnnotation() && !clazz
            .isSynthetic() && !clazz.isPrimitive();
    }

    // --------------------------------------------------------------------------------------------------- Private method start
    /**
     * 文件过滤器，过滤掉不需要的文件<br>
     * 只保留Class文件、目录和Jar
     */
    private static FileFilter fileFilter = new FileFilter() {
        @Override
        public boolean accept(final File pathname) {
            return isClass(pathname.getName()) || pathname.isDirectory() || isJarFile(pathname);
        }
    };

    /**
     * 改变 com -> com. 避免在比较的时候把比如 completeTestSuite.class类扫描进去，如果没有"."</br>
     * 那class里面com开头的class类也会被扫描进去,其实名称后面或前面需要一个 ".",来添加包的特征
     *
     * @param packageName
     * @return 格式化后的包名
     */
    private static String getWellFormedPackageName(final String packageName) {
        return packageName.lastIndexOf(StrKit.S_DOT) != packageName.length() - 1 ? packageName + StrKit.S_DOT : packageName;
    }

    /**
     * 填充满足条件的class 填充到 classes<br>
     * 同时会判断给定的路径是否为Jar包内的路径，如果是，则扫描此Jar包
     *
     * @param path        Class文件路径或者所在目录Jar包路径
     * @param packageName 需要扫面的包名
     * @param classFilter class过滤器
     * @param classes     List 集合
     */
    private static void fillClasses(final String path, final String packageName, final ClassFilter classFilter, final Set<Class<?>> classes) {
        String newPath = path;
        // 判定给定的路径是否为Jar
        int index = newPath.lastIndexOf(FileKit.JAR_PATH_EXT);
        if (index != -1) {
            // Jar文件 截取jar路径
            newPath = newPath.substring(0, index + FileKit.JAR_FILE_EXT.length());
            // 去掉文件前缀
            newPath = StrKit.removePrefix(newPath, FileKit.PATH_FILE_PRE);
            processJarFile(new File(newPath), packageName, classFilter, classes);
        } else {
            fillClasses(newPath, new File(newPath), packageName, classFilter, classes);
        }
    }

    /**
     * 填充满足条件的class 填充到 classes
     *
     * @param classPath   类文件所在目录，当包名为空时使用此参数，用于截掉类名前面的文件路径
     * @param file        Class文件或者所在目录Jar包文件
     * @param packageName 需要扫面的包名
     * @param classFilter class过滤器
     * @param classes     List 集合
     */
    private static void fillClasses(final String classPath, final File file, final String packageName, final ClassFilter classFilter, final Set<Class<?>> classes) {
        if (file.isDirectory()) {
            processDirectory(classPath, file, packageName, classFilter, classes);
        } else if (isClassFile(file)) {
            processClassFile(classPath, file, packageName, classFilter, classes);
        } else if (isJarFile(file)) {
            processJarFile(file, packageName, classFilter, classes);
        }
    }

    /**
     * 处理如果为目录的情况,需要递归调用 fillClasses方法
     *
     * @param directory   目录
     * @param packageName 包名
     * @param classFilter 类过滤器
     * @param classes     类集合
     */
    private static void processDirectory(final String classPath, final File directory, final String packageName, final ClassFilter classFilter, final Set<Class<?>> classes) {
        if (Func.isEmpty(directory)) {
            return;
        }
        File[] files = directory.listFiles(fileFilter);

        if (Func.isEmpty(files)) {
            return;
        }
        for (File file : files) {
            fillClasses(classPath, file, packageName, classFilter, classes);
        }
    }

    /**
     * 处理为class文件的情况,填充满足条件的class 到 classes
     *
     * @param classPath   类文件所在目录，当包名为空时使用此参数，用于截掉类名前面的文件路径
     * @param file        class文件
     * @param packageName 包名
     * @param classFilter 类过滤器
     * @param classes     类集合
     */
    private static void processClassFile(final String classPath, final File file, final String packageName, final ClassFilter classFilter, final Set<Class<?>> classes) {
        String classNewPath = classPath;
        if (!classNewPath.endsWith(File.separator)) {
            classNewPath += File.separator;
        }
        String path = file.getAbsolutePath();
        if (StrKit.isEmpty(packageName)) {
            path = StrKit.removePrefix(path, classNewPath);
        }
        final String filePathWithDot = path.replace(File.separator, StrKit.S_DOT);

        int subIndex;
        if ((subIndex = filePathWithDot.indexOf(packageName)) != -1) {
            final int endIndex = filePathWithDot.lastIndexOf(FileKit.CLASS_EXT);

            final String className = filePathWithDot.substring(subIndex, endIndex);
            fillClass(className, packageName, classes, classFilter);
        }
    }

    /**
     * 处理为jar文件的情况，填充满足条件的class 到 classes
     *
     * @param file        jar文件
     * @param packageName 包名
     * @param classFilter 类过滤器
     * @param classes     类集合
     */
    private static void processJarFile(final File file, final String packageName, final ClassFilter classFilter, final Set<Class<?>> classes) {
        JarFile jarFile = null;
        try {
            jarFile = new JarFile(file);
            List<JarEntry> jarEntries = Collections.list(jarFile.entries());
            for (JarEntry entry : jarEntries) {
                if (isClass(entry.getName())) {
                    final String className = entry.getName().replace(StrKit.S_SLASH, StrKit.S_DOT).replace(FileKit.CLASS_EXT, StrKit.S_EMPTY);
                    fillClass(className, packageName, classes, classFilter);
                }
            }
        } catch (Throwable e) {
            throw new KitException(e);
        } finally {
            IoKit.close(jarFile);
        }
    }

    /**
     * 填充class 到 classes
     *
     * @param className   类名
     * @param packageName 包名
     * @param classes     类集合
     * @param classFilter 类过滤器
     */
    private static void fillClass(final String className, final String packageName, final Set<Class<?>> classes, final ClassFilter classFilter) {
        if (className.startsWith(packageName)) {
            try {
                final Class<?> clazz = Class.forName(className, false, getClassLoader());
                if (classFilter == null || classFilter.accept(clazz)) {
                    classes.add(clazz);
                }
            } catch (Throwable e) {
                throw new KitException(e);
            }
        }
    }

    /**
     * @param file 文件
     * @return 是否为类文件
     */
    private static boolean isClassFile(final File file) {
        return isClass(file.getName());
    }

    /**
     * @param fileName 文件名
     * @return 是否为类文件
     */
    private static boolean isClass(final String fileName) {
        return fileName.endsWith(FileKit.CLASS_EXT);
    }

    /**
     * @param file 文件
     * @return是否为Jar文件
     */
    private static boolean isJarFile(final File file) {
        return file.getName().endsWith(FileKit.JAR_FILE_EXT);
    }
    // --------------------------------------------------------------------------------------------------- Private method end

    /**
     * 类过滤器，用于过滤不需要加载的类<br>
     */
    public interface ClassFilter {
        /**
         * 判断是否接受
         *
         * @param clazz
         * @return
         */
        boolean accept(Class<?> clazz);
    }
}
