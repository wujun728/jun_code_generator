package com.jeasy.doc.controller;

import com.google.common.base.Joiner;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import com.jeasy.base.web.controller.ControllerSupport;
import com.jeasy.base.web.dto.ModelResult;
import com.jeasy.base.web.resolver.FromJson;
import com.jeasy.common.Func;
import com.jeasy.common.str.StrKit;
import com.jeasy.doc.annotation.InitField;
import com.jeasy.doc.annotation.MethodDoc;
import com.jeasy.doc.annotation.StatusEnum;
import com.jeasy.doc.model.MenuTree;
import com.jeasy.doc.model.NodeUrl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;

import java.lang.reflect.Method;
import java.util.*;

/**
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
@Slf4j
@Controller
@RequestMapping("/doc")
public class DocController extends ControllerSupport implements ApplicationContextAware {

    private WebApplicationContext webApplicationContext;

    @RequestMapping(value = StrKit.S_EMPTY)
    public String index() throws Exception {
        return "doc";
    }

    @RequestMapping(value = "index")
    public String docIndex() throws Exception {
        return "doc_index";
    }

    @RequestMapping(value = "listUrl")
    @ResponseBody
    public void listUrl(@RequestParam(value = "node") String node) throws Exception {

        List<NodeUrl> nodeUrls = new ArrayList<>();
        Map<String, Object> controllerMap = webApplicationContext.getBeansWithAnnotation(Controller.class);

        for (Map.Entry<String, Object> entry : controllerMap.entrySet()) {
            String controllerClassName = entry.getValue().getClass().getName();

            if (controllerClassName.contains("$$")) {
                controllerClassName = controllerClassName.substring(0, controllerClassName.indexOf("$$"));
            }

            Class controllerClass = Class.forName(controllerClassName);
            RequestMapping controllerMapping = (RequestMapping) controllerClass.getAnnotation(RequestMapping.class);

            Method[] methods = controllerClass.getMethods();
            for (Method method : methods) {
                MethodDoc methodDoc = method.getAnnotation(MethodDoc.class);
                RequestMapping methodMapping = method.getAnnotation(RequestMapping.class);

                if (Func.isEmpty(methodDoc)) {
                    continue;
                }

                String[] methodDesc = methodDoc.desc();
                boolean isNode = false;
                for (String nodeDesc : methodDesc) {
                    if (nodeDesc.equals(node)) {
                        isNode = true;
                    }
                }

                if (isNode && methodDesc.length >= 3) {
                    String url = (Func.isEmpty(controllerMapping) ? StrKit.S_EMPTY : controllerMapping.value()[0]) + "/" + methodMapping.value()[0];
                    url = StrKit.replace(url, "//", "/");
                    String classDesc = controllerClass.getSimpleName() + "." + method.getName();
                    String methodType = Joiner.on("/").join(methodMapping.method());
                    NodeUrl nodeUrl = new NodeUrl(url, methodDesc[2],
                        methodMapping.headers().length > 0 ? methodMapping.headers()[0].split("=")[1] : StrKit.S_EMPTY,
                        methodMapping.headers().length > 1 ? methodMapping.headers()[1].split("=")[1] : StrKit.S_EMPTY,
                        methodMapping.headers().length > 2 ? methodMapping.headers()[2].split("=")[1] : StrKit.S_EMPTY,
                        classDesc, StrKit.isBlank(methodType) ? "GET/POST" : methodType, methodDoc.status().statusName(), methodDoc.author(), methodDoc.finishTime());
                    nodeUrls.add(nodeUrl);
                }
            }
        }

        Collections.sort(nodeUrls, new Comparator<NodeUrl>() {
            @Override
            public int compare(NodeUrl o1, NodeUrl o2) {
                if (o1.getDesc().compareToIgnoreCase(o2.getDesc()) > 0) {
                    return 1;
                }
                return -1;
            }
        });

        Map<String, Object> result = Maps.newHashMap();
        result.put("total", nodeUrls.size());
        result.put("rows", nodeUrls);
        responseEntity(ModelResult.CODE_200, ModelResult.SUCCESS, result);
    }

    @RequestMapping(value = "menu")
    @ResponseBody
    public void menu() throws Exception {
        List<MenuTree> menuTree = new ArrayList<>();
        Map<String, Object> controllerMap = webApplicationContext.getBeansWithAnnotation(Controller.class);

        Table<String, String, List<String>> treeTable = HashBasedTable.create();
        for (Map.Entry<String, Object> entry : controllerMap.entrySet()) {
            String controllerClassName = entry.getValue().getClass().getName();

            if (controllerClassName.contains("$$")) {
                controllerClassName = controllerClassName.substring(0, controllerClassName.indexOf("$$"));
            }

            Class controllerClass = Class.forName(controllerClassName);

            Method[] methods = controllerClass.getMethods();
            for (Method method : methods) {
                MethodDoc methodDoc = method.getAnnotation(MethodDoc.class);
                if (Func.isEmpty(methodDoc)) {
                    continue;
                }

                String[] methodDesc = methodDoc.desc();
                if (methodDesc.length >= 3) {
                    if (treeTable.contains(methodDesc[0], methodDesc[1])) {
                        List<String> nodeList = treeTable.get(methodDesc[0], methodDesc[1]);
                        if (!nodeList.contains(methodDesc[2])) {
                            nodeList.add(methodDesc[2]);
                        }
                    } else {
                        treeTable.put(methodDesc[0], methodDesc[1], Lists.newArrayList(methodDesc[2]));
                    }
                }
            }
        }

        for (String rowKey : treeTable.rowKeySet()) {
            MenuTree nodeMenu1 = new MenuTree(0L, rowKey);
            List<MenuTree> nodeLevel2 = new ArrayList<>();
            for (String columnKey : treeTable.row(rowKey).keySet()) {
                MenuTree nodeMenu2 = new MenuTree(1L, columnKey);
                List<MenuTree> nodeLevel3 = new ArrayList<>();
                for (String value : treeTable.get(rowKey, columnKey)) {
                    nodeLevel3.add(new MenuTree(2L, value));
                }

                Collections.sort(nodeLevel3, new Comparator<MenuTree>() {
                    @Override
                    public int compare(MenuTree o1, MenuTree o2) {
                        if (o1.getText().compareToIgnoreCase(o2.getText()) > 0) {
                            return 1;
                        }
                        return -1;
                    }
                });
                nodeMenu2.setChildren(nodeLevel3);
                nodeLevel2.add(nodeMenu2);
            }

            Collections.sort(nodeLevel2, new Comparator<MenuTree>() {
                @Override
                public int compare(MenuTree o1, MenuTree o2) {
                    if (o1.getText().compareToIgnoreCase(o2.getText()) > 0) {
                        return 1;
                    }
                    return -1;
                }
            });

            nodeMenu1.setChildren(nodeLevel2);
            menuTree.add(nodeMenu1);
        }

        responseList(ModelResult.CODE_200, ModelResult.SUCCESS, menuTree);
    }

    @MethodDoc(entity = Person0.class, desc = {"1-接口演示", "1. @FromJson参数", "1.1 基本类型+集合"}, status = StatusEnum.TODO, author = "taomk", finishTime = "2015-07-31")
    @RequestMapping(value = "test0", headers = {"version=1.0.0", "platform=PC", "device=PC"})
    @ResponseBody
    public void test0(@FromJson Person0 person0) throws Exception {
        responseEntity(ModelResult.CODE_200, ModelResult.SUCCESS, person0);
    }

    @MethodDoc(entity = Person1.class, desc = {"1-接口演示", "1. @FromJson参数", "1.2 基本类型+集合+对象属性"}, status = StatusEnum.TODO, author = "taomk", finishTime = "2015-07-31")
    @RequestMapping(value = "test1", headers = {"version=1.0.0", "platform=PC", "device=PC"})
    @ResponseBody
    public void test1(@FromJson Person1 person1) throws Exception {
        responseEntity(ModelResult.CODE_200, ModelResult.SUCCESS, person1);
    }

    @MethodDoc(entity = Page.class, desc = {"1-接口演示", "2. KV参数", "2.1 @FromJson基本类型"}, status = StatusEnum.TODO, author = "taomk", finishTime = "2015-07-31")
    @RequestMapping(value = "test2", headers = {"version=1.0.0", "platform=PC", "device=PC"})
    @ResponseBody
    public void test2(@InitField(name = "pageNo", value = "1", desc = "当前页码") @FromJson Integer pageNo,
                      @InitField(name = "pageSize", value = "10", desc = "每页大小") @FromJson Integer pageSize) throws Exception {
        Page page = new Page();
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        responseEntity(ModelResult.CODE_200, ModelResult.SUCCESS, page);
    }

    @MethodDoc(entity = Person1Page.class, desc = {"1-接口演示", "2. KV参数", "2.2 @FromJson对象类型 + 基本类型"}, status = StatusEnum.TODO, author = "taomk", finishTime = "2015-07-31")
    @RequestMapping(value = "test3", headers = {"version=1.0.0", "platform=PC", "device=PC"})
    @ResponseBody
    public void test3(@FromJson Person1Page person1Page,
                      @InitField(name = "pageNo", value = "1", desc = "当前页码") Integer pageNo,
                      @InitField(name = "pageSize", value = "10", desc = "每页大小") Integer pageSize) throws Exception {
        person1Page.setPageNo(pageNo);
        person1Page.setPageSize(pageSize);
        responseEntity(ModelResult.CODE_200, ModelResult.SUCCESS, person1Page);
    }

    @MethodDoc(entity = Person2.class, desc = {"1-接口演示", "2. KV参数", "2.3 @FromJson对象类型 + 基本类型集合"}, status = StatusEnum.TODO, author = "taomk", finishTime = "2015-07-31")
    @RequestMapping(value = "test4", headers = {"version=1.0.0", "platform=PC", "device=PC"})
    @ResponseBody
    public void test4(@FromJson Person2 person2,
                      @InitField(name = "personAges", value = "[\"1\",\"2\",\"3\",\"4\",\"5\"]", desc = "家庭成员年龄") @FromJson List<Integer> personAges,
                      @InitField(name = "personNames", value = "[\"小红\",\"小明\",\"小亮\",\"小涛\",\"张三\"]", desc = "家庭成员姓名") List<String> personNames,
                      @InitField(name = "personWeights", value = "[\"40.2\",\"50.6\",\"70.2\"]", desc = "家庭成员体重") Set<Double> personWeights,
                      @InitField(name = "personHeights", value = "[\"188\",\"189\",\"200\"]", desc = "家庭成员身高") Long[] personHeights) throws Exception {
        person2.setPersonAges(personAges);
        person2.setPersonHeights(personHeights);
        person2.setPersonWeights(personWeights);
        person2.setPersonNames(personNames);
        responseEntity(ModelResult.CODE_200, ModelResult.SUCCESS, person2);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        webApplicationContext = (WebApplicationContext) applicationContext;
    }
}
