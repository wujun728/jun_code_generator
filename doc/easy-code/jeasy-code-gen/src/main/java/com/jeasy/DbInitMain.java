package com.jeasy;

import com.beust.jcommander.internal.Lists;
import com.beust.jcommander.internal.Maps;
import com.jeasy.common.Func;
import com.jeasy.common.charset.CharsetKit;
import com.jeasy.common.number.RandomKit;
import com.jeasy.common.pinyin.PinYinKit;
import com.jeasy.common.str.StrKit;
import com.jeasy.common.template.TemplateKit;
import com.jeasy.model.Dic;
import com.jeasy.model.DicType;

import java.util.List;
import java.util.Map;

/**
 * @author TaoBangren
 * @version 1.0
 * @since 2017/4/8 下午3:00
 */
public class DbInitMain {
    public static void main(String[] args) {
        initDictionary();
//        initResourcesAndRole();
//        genDictionaryKit();
//        randomMember();
    }

    private static final List<String> MEMBERS = Lists.newArrayList("陶明凯", "姚慧欣", "李小波", "凌晓斌", "王振兴", "许晨熙", "王秀虎", "郭薇", "高羽", "徐宁", "吴爽", "陈静");

    private static void randomMember() {
        for (int i = 0; i < 10; i++) {
            System.out.println("恭喜，您已中奖：" + RandomKit.random(MEMBERS));
        }
    }

    private static final String DICTIONARY_PARAMS =
        "用户状态:[1000=启用,1001=停用]|" +
            "机构类型:[2000=其他]|" +
            "日志类型:3000=登录登出[操作类型$3001=登录,3002=登出]&4000=启用停用[操作类型$4001=启用,4002=停用]";

    private static void initDictionary() {
        String[] dictionaryParams = StrKit.split(DICTIONARY_PARAMS, "|");

        // 主键ID从1开始递增
        int id = 1;
        for (String dictionary : dictionaryParams) {
            String[] dictionaryParts = StrKit.split(dictionary, ":");
            String subDictionary;
            String parentDictionary;
            // 判断是否符合XXXX:0000=YYYY[1111=AAAA,2222=BBBB]
            if (StrKit.containsIgnoreCase(dictionaryParts[1], "[")) {
                if (StrKit.startWith(dictionaryParts[1], "[", false)) {
                    // 处理XXXX:[1111=AAAA,2222=BBBB]，无父字典情况
                    subDictionary = StrKit.sub(dictionaryParts[1], 1, dictionaryParts[1].length() - 1);

                    // 插入子字典
                    String[] subDictionaryParams = StrKit.split(subDictionary, ",");
                    for (String subDictionaryParam : subDictionaryParams) {
                        String[] parts = StrKit.split(subDictionaryParam, "=");
                        System.out.println(
                            StrKit.format("INSERT INTO `bd_dictionary` " +
                                    "(`id`, `name`, `code`, `value`, `type`, `typeName`, `sort`, `pid`, `pcode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) " +
                                    "VALUES " +
                                    "({}, '{}', '{}', {}, '{}', '{}', {}, {}, '{}', {}, {}, '{}', {}, {}, '{}', {}, {});",
                                id++, parts[1], PinYinKit.getPinYinHeadChar(parts[1]).toUpperCase(), Integer.valueOf(parts[0].trim()), PinYinKit.getPinYinHeadChar(dictionaryParts[0]).toUpperCase(), dictionaryParts[0], 0, 0, StrKit.S_EMPTY, System.currentTimeMillis(), 0, "SYSTEM", System.currentTimeMillis(), 0, "SYSTEM", 0, 0));
                    }
                } else {
                    // 处理XXXX:0000=YYYY[EEEE$1111=AAAA,2222=BBBB]&1111=ZZZZ[FFFF$1111=CCCC,2222=DDDD]，有父字典情况
                    String[] subDictionaryParts = StrKit.split(dictionaryParts[1], "&");
                    for (String part : subDictionaryParts) {
                        String dictionaryType = PinYinKit.getPinYinHeadChar(dictionaryParts[0]).toUpperCase();
                        String dictionaryTypeName = dictionaryParts[0];
                        parentDictionary = StrKit.subPre(part, part.indexOf("["));
                        subDictionary = StrKit.sub(part, part.indexOf("[") + 1, part.length() - 1);
                        if (subDictionary.contains("$")) {
                            dictionaryType = PinYinKit.getPinYinHeadChar(StrKit.subPre(subDictionary, subDictionary.indexOf("$"))).toUpperCase();
                            dictionaryTypeName = StrKit.subPre(subDictionary, subDictionary.indexOf("$"));
                            subDictionary = StrKit.sub(subDictionary, subDictionary.indexOf("$") + 1, subDictionary.length());
                        }

                        // 优先插入父字典
                        String[] parentParts = StrKit.split(parentDictionary, "=");

                        int pid = id++;
                        System.out.println(
                            StrKit.format("INSERT INTO `bd_dictionary` " +
                                    "(`id`, `name`, `code`, `value`, `type`, `typeName`, `sort`, `pid`, `pcode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) " +
                                    "VALUES " +
                                    "({}, '{}', '{}', {}, '{}', '{}', {}, {}, '{}', {}, {}, '{}', {}, {}, '{}', {}, {});",
                                pid, parentParts[1], PinYinKit.getPinYinHeadChar(parentParts[1]).toUpperCase(), Integer.valueOf(parentParts[0].trim()), PinYinKit.getPinYinHeadChar(dictionaryParts[0]).toUpperCase(), dictionaryParts[0], 0, 0, StrKit.S_EMPTY, System.currentTimeMillis(), 0, "SYSTEM", System.currentTimeMillis(), 0, "SYSTEM", 0, 0));

                        // 插入子字典
                        String[] subDictionaryParams = StrKit.split(subDictionary, ",");
                        for (String subDictionaryParam : subDictionaryParams) {
                            String[] subParts = StrKit.split(subDictionaryParam, "=");
                            System.out.println(
                                StrKit.format("INSERT INTO `bd_dictionary` " +
                                        "(`id`, `name`, `code`, `value`, `type`, `typeName`, `sort`, `pid`, `pcode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) " +
                                        "VALUES " +
                                        "({}, '{}', '{}', {}, '{}', '{}', {}, {}, '{}', {}, {}, '{}', {}, {}, '{}', {}, {});",
                                    id++, subParts[1], PinYinKit.getPinYinHeadChar(subParts[1]).toUpperCase(), Integer.valueOf(subParts[0].trim()), dictionaryType, dictionaryTypeName, 0, pid, StrKit.S_EMPTY, System.currentTimeMillis(), 0, "SYSTEM", System.currentTimeMillis(), 0, "SYSTEM", 0, 0));
                        }
                    }
                }
            }
        }
    }

    private static void genDictionaryKit() {
        Map<String, List<DicType>> model = Maps.newHashMap();
        Map<String, DicType> dicTypeMap = Maps.newHashMap();
        List<DicType> dicTypes = Lists.newArrayList();
        model.put("dicTypes", dicTypes);

        String[] dictionaryParams = StrKit.split(DICTIONARY_PARAMS, "|");

        for (String dictionary : dictionaryParams) {
            String[] dictionaryParts = StrKit.split(dictionary, ":");
            DicType dicType = new DicType();
            dicType.setCode(PinYinKit.getPinYinHeadChar(dictionaryParts[0]).toUpperCase());
            dicType.setName(dictionaryParts[0]);
            dicTypes.add(dicType);
            dicTypeMap.put(dicType.getName(), dicType);

            String subDictionary;
            // 判断是否符合XXXX:0000=YYYY[1111=AAAA,2222=BBBB]
            if (StrKit.containsIgnoreCase(dictionaryParts[1], "[")) {
                if (StrKit.startWith(dictionaryParts[1], "[", false)) {
                    // 处理XXXX:[1111=AAAA,2222=BBBB]，无父字典情况
                    subDictionary = StrKit.sub(dictionaryParts[1], 1, dictionaryParts[1].length() - 1);

                    // 插入子字典
                    String[] subDictionaryParams = StrKit.split(subDictionary, ",");
                    List<Dic> subDics = Lists.newArrayList();
                    if (Func.isNotEmpty(dicType.getDics())) {
                        subDics = dicType.getDics();
                    } else {
                        dicType.setDics(subDics);
                    }

                    for (String subDictionaryParam : subDictionaryParams) {
                        String[] parts = StrKit.split(subDictionaryParam, "=");
                        Dic dic = new Dic();
                        Map<String, String> params = Maps.newHashMap();
                        params.put("\\(", "_");
                        params.put("\\)", StrKit.S_EMPTY);

                        dic.setName(parts[1]);
                        dic.setCode(StrKit.replace(PinYinKit.getPinYinHeadChar(parts[1]).toUpperCase(), params));
                        dic.setOrigCode(PinYinKit.getPinYinHeadChar(parts[1]).toUpperCase());
                        subDics.add(dic);
                    }
                } else {
                    // 处理XXXX:0000=YYYY[EEEE$1111=AAAA,2222=BBBB]&1111=ZZZZ[FFFF$1111=CCCC,2222=DDDD]，有父字典情况
                    List<Dic> subDics = Lists.newArrayList();
                    if (Func.isNotEmpty(dicType.getDics())) {
                        subDics = dicType.getDics();
                    } else {
                        dicType.setDics(subDics);
                    }

                    String[] subDictionaryParts = StrKit.split(dictionaryParts[1], "&");
                    for (String part : subDictionaryParts) {
                        String dictionaryType = PinYinKit.getPinYinHeadChar(dictionaryParts[0]).toUpperCase();
                        String dictionaryTypeName = dictionaryParts[0];
                        subDictionary = StrKit.sub(part, part.indexOf("[") + 1, part.length() - 1);
                        if (subDictionary.contains("$")) {
                            dictionaryType = PinYinKit.getPinYinHeadChar(StrKit.subPre(subDictionary, subDictionary.indexOf("$"))).toUpperCase();
                            dictionaryTypeName = StrKit.subPre(subDictionary, subDictionary.indexOf("$"));
                            subDictionary = StrKit.sub(subDictionary, subDictionary.indexOf("$") + 1, subDictionary.length());
                        }

                        String parentDictionary = StrKit.subPre(part, part.indexOf("["));
                        String[] parentParts = StrKit.split(parentDictionary, "=");

                        Dic dic = new Dic();
                        Map<String, String> params = Maps.newHashMap();
                        params.put("\\(", "_");
                        params.put("\\)", StrKit.S_EMPTY);

                        dic.setName(parentParts[1]);
                        dic.setCode(StrKit.replace(PinYinKit.getPinYinHeadChar(parentParts[1]).toUpperCase(), params));
                        dic.setOrigCode(PinYinKit.getPinYinHeadChar(parentParts[1]).toUpperCase());
                        subDics.add(dic);

                        DicType subDicType;
                        if (dicTypeMap.containsKey(dictionaryTypeName)) {
                            subDicType = dicTypeMap.get(dictionaryTypeName);
                        } else {
                            subDicType = new DicType();
                            dicTypes.add(subDicType);
                            dicTypeMap.put(dictionaryTypeName, subDicType);
                        }

                        subDicType.setName(dictionaryTypeName);
                        subDicType.setCode(dictionaryType);

                        // 插入子字典
                        String[] subDictionaryParams = StrKit.split(subDictionary, ",");
                        List<Dic> subDictionarys = Lists.newArrayList();
                        if (Func.isNotEmpty(subDicType.getDics())) {
                            subDictionarys = subDicType.getDics();
                        } else {
                            subDicType.setDics(subDictionarys);
                        }

                        for (String subDictionaryParam : subDictionaryParams) {
                            String[] subParts = StrKit.split(subDictionaryParam, "=");

                            Dic subDic = new Dic();
                            Map<String, String> subParams = Maps.newHashMap();
                            subParams.put("\\(", "_");
                            subParams.put("\\)", StrKit.S_EMPTY);

                            subDic.setName(subParts[1]);
                            subDic.setCode(StrKit.replace(PinYinKit.getPinYinHeadChar(subParts[1]).toUpperCase(), subParams));
                            subDic.setOrigCode(PinYinKit.getPinYinHeadChar(subParts[1]).toUpperCase());
                            subDictionarys.add(subDic);
                        }
                    }
                }
            }
        }

        TemplateKit.executeFreemarker("/Users/TaoBangren/git@osc/easy-code/jeasy-code-gen/src/main/resources", "DictionaryKit.ftl", CharsetKit.DEFAULT_ENCODE, model, "/Users/TaoBangren/git@osc/easy-code/jeasy-code-gen/src/main/java/com/jeasy", "DictionaryKit.java");
    }

    private static final String RESOURCE_PARAMS =
        "用户管理(ios-people)=" +
            "{人员管理(ios-body&/user)[查询-查看-新增-编辑-删除-角色配置-机构配置]};" +
            "{角色管理(ios-person&/role)[查询-查看-新增-编辑-删除-权限配置]};" +
            "{组织机构(ios-people&/organization)[查询-查看-新增-编辑-删除]};" +
            "{菜单资源(android-menu&/resource)[查询-查看-新增-编辑-删除]}|" +
            "基础数据(settings)=" +
            "{公共码表(ios-book&/dictionary)[查询-查看-新增-编辑-删除]};" +
            "{文件管理(ios-folder&/fileAttach)[查询-查看-新增-编辑-删除]}|" +
            "代码平台(code-working)=" +
            "{接口文档(usb&/api)[查询-查看-新增-编辑-删除]};" +
            "{代码生成(code-download&/code)[查询-查看-新增-编辑-删除]}|" +
            "日志监控(monitor)=" +
            "{操作日志(ios-paper&/log)[查询-查看-新增-编辑-删除]};" +
            "{数据监控(ios-analytics&/druid)[查询-查看-新增-编辑-删除]};" +
            "{接口监控(usb&/monitor)[查询-查看-新增-编辑-删除]}";

    private static void initResourcesAndRole() {
        String[] menus = StrKit.split(RESOURCE_PARAMS, "|");

        int menuId = 1;
        int roleResourceId = 1;
        for (String menu : menus) {
            String[] menuParts = menu.split("=");
            String menuName = menuParts[0];
            String icon = menuName.substring(menuName.lastIndexOf("(") + 1, menuName.lastIndexOf(")"));
            menuName = menuName.substring(0, menuName.lastIndexOf("("));

            // 处理一级菜单
            System.out.println(
                StrKit.format("INSERT INTO `su_resource` " +
                        "(`id`, `name`, `code`, `url`, `icon`, `remark`, `pid`, `sort`, `isMenu`, `isLeaf`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) " +
                        "VALUES " +
                        "({}, '{}', '{}', '{}', '{}', '{}', {}, {}, {}, {}, {}, {}, '{}', {}, {}, '{}', {}, {});",
                    menuId, menuName, PinYinKit.getPinYinHeadChar(menuName).toUpperCase(), StrKit.S_EMPTY, icon, StrKit.S_EMPTY, 0, 0, 1, 0, System.currentTimeMillis(), 0, "SYSTEM", System.currentTimeMillis(), 0, "SYSTEM", 0, 0));

            System.out.println(StrKit.format("INSERT INTO `su_role_resource` (`id`, `roleId`, `roleName`, `roleCode`, `resourceId`, `resourceName`, `resourceCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) " +
                    "VALUES " +
                    "({}, {}, '{}', '{}', {}, '{}', '{}', {}, {}, '{}', {}, {}, '{}', {}, {});",
                roleResourceId++, 1, "超级管理员", "CJGLY", menuId, menuName, PinYinKit.getPinYinHeadChar(menuName).toUpperCase(), System.currentTimeMillis(), 0, "SYSTEM", System.currentTimeMillis(), 0, "SYSTEM", 0, 0));

            int pid = menuId++;
            String[] subMenus = menuParts[1].split(";");
            for (String subMenuInfo : subMenus) {

                String[] subMenuParts = subMenuInfo.split("\\[");
                String subMenuName = subMenuParts[0].substring(1);
                icon = subMenuName.substring(subMenuName.lastIndexOf("(") + 1, subMenuName.lastIndexOf("&"));
                String url = subMenuName.substring(subMenuName.lastIndexOf("&") + 1, subMenuName.lastIndexOf(")"));
                subMenuName = subMenuName.substring(0, subMenuName.lastIndexOf("("));

                // 处理二级菜单
                System.out.println(
                    StrKit.format("INSERT INTO `su_resource` " +
                            "(`id`, `name`, `code`, `url`, `icon`, `remark`, `pid`, `sort`, `isMenu`, `isLeaf`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) " +
                            "VALUES " +
                            "({}, '{}', '{}', '{}', '{}', '{}', {}, {}, {}, {}, {}, {}, '{}', {}, {}, '{}', {}, {});",
                        menuId, subMenuName, PinYinKit.getPinYinHeadChar(menuName).toUpperCase() + ":" + PinYinKit.getPinYinHeadChar(subMenuName).toUpperCase(), url, icon, StrKit.S_EMPTY, pid, 0, 1, 1, System.currentTimeMillis(), 0, "SYSTEM", System.currentTimeMillis(), 0, "SYSTEM", 0, 0));

                System.out.println(StrKit.format("INSERT INTO `su_role_resource` (`id`, `roleId`, `roleName`, `roleCode`, `resourceId`, `resourceName`, `resourceCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) " +
                        "VALUES " +
                        "({}, {}, '{}', '{}', {}, '{}', '{}', {}, {}, '{}', {}, {}, '{}', {}, {});",
                    roleResourceId++, 1, "超级管理员", "CJGLY", menuId, subMenuName, PinYinKit.getPinYinHeadChar(menuName).toUpperCase() + ":" + PinYinKit.getPinYinHeadChar(subMenuName).toUpperCase(), System.currentTimeMillis(), 0, "SYSTEM", System.currentTimeMillis(), 0, "SYSTEM", 0, 0));

                int parentMenuId = menuId++;
                String[] tabInfos = subMenuParts[1].substring(0, subMenuParts[1].lastIndexOf("]")).split(",");
                for (String tabInfo : tabInfos) {

                    String[] options;
                    if (tabInfo.contains(":")) {
                        String[] tabParts = tabInfo.split(":");
                        String tabName = tabParts[0];
                        // 处理二级菜单下的选项卡
                        System.out.println(
                            StrKit.format("INSERT INTO `su_resource` " +
                                    "(`id`, `name`, `code`, `url`, `icon`, `remark`, `pid`, `sort`, `isMenu`, `isLeaf`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) " +
                                    "VALUES " +
                                    "({}, '{}', '{}', '{}', '{}', '{}', {}, {}, {}, {}, {}, {}, '{}', {}, {}, '{}', {}, {});",
                                menuId, tabName, PinYinKit.getPinYinHeadChar(menuName).toUpperCase() + ":" + PinYinKit.getPinYinHeadChar(subMenuName).toUpperCase() + ":" + PinYinKit.getPinYinHeadChar(tabName).toUpperCase(), StrKit.S_EMPTY, StrKit.S_EMPTY, StrKit.S_EMPTY, parentMenuId, 0, 0, 0, System.currentTimeMillis(), 0, "SYSTEM", System.currentTimeMillis(), 0, "SYSTEM", 0, 0));

                        System.out.println(StrKit.format("INSERT INTO `su_role_resource` (`id`, `roleId`, `roleName`, `roleCode`, `resourceId`, `resourceName`, `resourceCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) " +
                                "VALUES " +
                                "({}, {}, '{}', '{}', {}, '{}', '{}', {}, {}, '{}', {}, {}, '{}', {}, {});",
                            roleResourceId++, 1, "超级管理员", "CJGLY", menuId, tabName, PinYinKit.getPinYinHeadChar(menuName).toUpperCase() + ":" + PinYinKit.getPinYinHeadChar(subMenuName).toUpperCase() + ":" + PinYinKit.getPinYinHeadChar(tabName).toUpperCase(), System.currentTimeMillis(), 0, "SYSTEM", System.currentTimeMillis(), 0, "SYSTEM", 0, 0));

                        int optionParentMenuId = menuId++;
                        // 处理选项卡下的操作按钮
                        options = StrKit.split(tabParts[1], "-");
                        for (String option : options) {
                            System.out.println(
                                StrKit.format("INSERT INTO `su_resource` " +
                                        "(`id`, `name`, `code`, `url`, `icon`, `remark`, `pid`, `sort`, `isMenu`, `isLeaf`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) " +
                                        "VALUES " +
                                        "({}, '{}', '{}', '{}', '{}', '{}', {}, {}, {}, {}, {}, {}, '{}', {}, {}, '{}', {}, {});",
                                    menuId, option, PinYinKit.getPinYinHeadChar(menuName).toUpperCase() + ":" + PinYinKit.getPinYinHeadChar(subMenuName).toUpperCase() + ":" + PinYinKit.getPinYinHeadChar(tabName).toUpperCase() + ":" + PinYinKit.getPinYinHeadChar(option).toUpperCase(), StrKit.S_EMPTY, StrKit.S_EMPTY, StrKit.S_EMPTY, optionParentMenuId, 0, 0, 1, System.currentTimeMillis(), 0, "SYSTEM", System.currentTimeMillis(), 0, "SYSTEM", 0, 0));

                            System.out.println(StrKit.format("INSERT INTO `su_role_resource` (`id`, `roleId`, `roleName`, `roleCode`, `resourceId`, `resourceName`, `resourceCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) " +
                                    "VALUES " +
                                    "({}, {}, '{}', '{}', {}, '{}', '{}', {}, {}, '{}', {}, {}, '{}', {}, {});",
                                roleResourceId++, 1, "超级管理员", "CJGLY", menuId++, option, PinYinKit.getPinYinHeadChar(menuName).toUpperCase() + ":" + PinYinKit.getPinYinHeadChar(subMenuName).toUpperCase() + ":" + PinYinKit.getPinYinHeadChar(tabName).toUpperCase() + ":" + PinYinKit.getPinYinHeadChar(option).toUpperCase(), System.currentTimeMillis(), 0, "SYSTEM", System.currentTimeMillis(), 0, "SYSTEM", 0, 0));
                        }
                    } else {
                        // 处理二级菜单下的操作按钮
                        options = StrKit.split(tabInfo, "-");

                        for (String option : options) {
                            System.out.println(
                                StrKit.format("INSERT INTO `su_resource` " +
                                        "(`id`, `name`, `code`, `url`, `icon`, `remark`, `pid`, `sort`, `isMenu`, `isLeaf`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) " +
                                        "VALUES " +
                                        "({}, '{}', '{}', '{}', '{}', '{}', {}, {}, {}, {}, {}, {}, '{}', {}, {}, '{}', {}, {});",
                                    menuId, option, PinYinKit.getPinYinHeadChar(menuName).toUpperCase() + ":" + PinYinKit.getPinYinHeadChar(subMenuName).toUpperCase() + ":" + PinYinKit.getPinYinHeadChar(option).toUpperCase(), StrKit.S_EMPTY, StrKit.S_EMPTY, StrKit.S_EMPTY, parentMenuId, 0, 0, 1, System.currentTimeMillis(), 0, "SYSTEM", System.currentTimeMillis(), 0, "SYSTEM", 0, 0));

                            System.out.println(StrKit.format("INSERT INTO `su_role_resource` (`id`, `roleId`, `roleName`, `roleCode`, `resourceId`, `resourceName`, `resourceCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) " +
                                    "VALUES " +
                                    "({}, {}, '{}', '{}', {}, '{}', '{}', {}, {}, '{}', {}, {}, '{}', {}, {});",
                                roleResourceId++, 1, "超级管理员", "CJGLY", menuId++, option, PinYinKit.getPinYinHeadChar(menuName).toUpperCase() + ":" + PinYinKit.getPinYinHeadChar(subMenuName).toUpperCase() + ":" + PinYinKit.getPinYinHeadChar(option).toUpperCase(), System.currentTimeMillis(), 0, "SYSTEM", System.currentTimeMillis(), 0, "SYSTEM", 0, 0));
                        }
                    }
                }
            }
        }
    }
}
