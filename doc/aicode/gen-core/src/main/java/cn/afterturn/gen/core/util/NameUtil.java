package cn.afterturn.gen.core.util;

import org.apache.commons.lang.StringUtils;

/**
 * 命名规则工具类
 *
 * @author JueYue
 * @date 2014年12月21日
 */
public class NameUtil {

    private NameUtil() {

    }

    /**
     * 实体类驼峰转换,第一个字母大写
     */
    public static String getEntityHumpName(String name) {
        if (StringUtils.isEmpty(name)) {
            return name;
        }
        StringBuilder builder = new StringBuilder();
        name = name.toLowerCase();
        int index = 0;
        while ((index = name.indexOf("_")) != -1) {
            builder.append(name.substring(0, index));
            name = name.substring(index + 1, index + 2).toUpperCase() + name.substring(index + 2);
        }
        builder.append(name);
        return builder.substring(0, 1).toUpperCase() + builder.substring(1);
    }

    /**
     * 字段驼峰转换,第一个字母小写,第二个字母也是小写
     */
    public static String getFieldHumpName(String name) {
        StringBuilder builder = new StringBuilder();
        //不强制小写,兼容数据库按照java写法的代码 -包含下划线才强制
        //如果全都是大写也全部转为小写,只有大小写混合材认为是按照java逻辑写的
        if (name.contains("_") || name.toUpperCase().equals(name)) {
            name = name.toLowerCase();
        }
        int index = 0;
        while ((index = name.indexOf("_")) != -1) {
            builder.append(name.substring(0, index));
            name = name.substring(index + 1, index + 2).toUpperCase() + name.substring(index + 2);
        }
        builder.append(name);
        // 屏蔽第二个字母是大写的情况,强制换成小写,语法不对
        if (builder.length() > 1) {
            return builder.substring(0, 2).toLowerCase()
                    + builder.substring(2);
        }
        return builder.toString();
    }

    /**
     * 去掉无用字符
     *
     * @param name
     * @return
     */
    public static String handlerDBName(String name) {
        if (name.indexOf(".") != -1) {
            return name.substring(name.indexOf(".") + 1);
        }
        name = name.trim();
        if (name.startsWith("\"")) {
            name = name.substring(1);
        }
        if (name.endsWith("\"")) {
            name = name.substring(0, name.length() - 1);
        }
        return name;
    }

}
