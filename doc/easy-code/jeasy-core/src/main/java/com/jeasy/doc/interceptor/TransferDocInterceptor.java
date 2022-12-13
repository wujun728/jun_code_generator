package com.jeasy.doc.interceptor;

import com.beust.jcommander.internal.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.jeasy.base.interceptor.AbstractResponseLogInterceptor;
import com.jeasy.base.web.dto.ModelResult;
import com.jeasy.base.web.dto.ResultPage;
import com.jeasy.base.web.resolver.FromJson;
import com.jeasy.common.Func;
import com.jeasy.common.clazz.ClassKit;
import com.jeasy.common.json.JsonKit;
import com.jeasy.common.object.BeanKit;
import com.jeasy.common.str.StrKit;
import com.jeasy.common.thread.ThreadLocalKit;
import com.jeasy.doc.annotation.InitField;
import com.jeasy.doc.annotation.MethodDoc;
import com.jeasy.doc.annotation.StatusEnum;
import com.jeasy.doc.model.BodyParam;
import com.jeasy.doc.model.HeaderParam;
import com.jeasy.doc.model.KvParam;
import com.jeasy.doc.model.ResParam;
import com.jeasy.exception.MessageException;
import com.jeasy.exception.ServiceException;
import com.jeasy.validate.handler.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;

/**
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
@Slf4j
public class TransferDocInterceptor extends AbstractResponseLogInterceptor {

    private static final Integer CYCLE_OBJECT_PROPERTY_LIMIT = 3;

    private static final Integer ARRAY_OBJECT_PROPERTY_LIMIT = 3;

    private static final String OBJECT_STR = "Object";

    private static final String ZERO_STR = "0";

    private static final String SERIAL_VERSION_UID = "serialVersionUID";
    private static final int NUM_TWO = 2;
    private static final int NUM_ONE = 1;

    @Override
    protected boolean isThreadLocalEnable() {
        return false;
    }

    @Override
    public boolean preHandleProcess(final HttpServletRequest request, final HttpServletResponse response, final Object handler) {

        if (!(handler instanceof HandlerMethod)) {
            return false;
        }

        int isDoc = Integer.parseInt(request.getParameter("doc") == null ? ZERO_STR : request.getParameter("doc"));
        int isDisplay = Integer.parseInt(request.getParameter("display") == null ? ZERO_STR : request.getParameter("display"));

        if (isDoc == NUM_TWO) {
            return true;
        } else if (isDoc == 0 && isDisplay == 0) {
            Method method = ((HandlerMethod) handler).getMethod();
            MethodDoc methodDoc = method.getAnnotation(MethodDoc.class);
            // 当isDoc&isDisplay为空时, 自动构建真正Doc返回JSON数据(而非easyui datagrid要求JSON数据)
            if (Func.isEmpty(methodDoc) || methodDoc.status() == StatusEnum.DONE) {
                return true;
            }
        } else {
            buildResponseContentLog(request, response, (HandlerMethod) handler, null, System.currentTimeMillis() - ThreadLocalKit.getTime());
        }
        return false;
    }

    @Override
    protected String getLogName() {
        return "API execute report -------";
    }

    @Override
    protected ModelResult buildModelResult(HttpServletRequest request, HandlerMethod handler, Exception ex) {
        int isDoc = Integer.parseInt(request.getParameter("doc") == null ? ZERO_STR : request.getParameter("doc"));
        int isDisplay = Integer.parseInt(request.getParameter("display") == null ? ZERO_STR : request.getParameter("display"));

        // 构建DOC接口响应假数据
        if (isDoc == NUM_ONE) {
            List<ResParam> resParams = buildResponseDocResult(handler);
            return responseList(ModelResult.CODE_200, ModelResult.SUCCESS, resParams);
        }

        // 构建右面接口参数
        if (isDisplay == NUM_ONE) {
            Map<String, Object> inputParams = buildRequestDocParams(handler);
            return responseEntity(ModelResult.CODE_200, ModelResult.SUCCESS, inputParams);
        }

        // 当isDoc&isDisplay为空时, 自动构建真正Doc返回JSON数据(而非easyui datagrid要求JSON数据)
        if (isDoc == 0 && isDisplay == 0) {
            Method method = handler.getMethod();
            MethodDoc methodDoc = method.getAnnotation(MethodDoc.class);
            if (Func.isNotEmpty(methodDoc) && methodDoc.status() != StatusEnum.DONE) {
                try {
                    return buildResponseDocJsonResult(methodDoc);
                } catch (IllegalAccessException e) {
                    log.error("buildModelResult occur error ", e);
                }
            }
        }

        // 根据真实Controller执行的ModelResult, 构建easyui datagrid要求JSON数据
        if (isDoc == NUM_TWO) {
            List<ResParam> resParams = buildResponseTrueResult(request, ex);
            return responseList(ModelResult.CODE_200, ModelResult.SUCCESS, resParams);
        }
        return null;
    }

    private ModelResult buildResponseDocJsonResult(final MethodDoc methodDoc) throws IllegalAccessException {
        Class entity = methodDoc.entity();
        Class lists = methodDoc.lists();
        Class pages = methodDoc.pages();

        ModelResult<Object> modelResult = new ModelResult<>();
        modelResult.setCode(ModelResult.CODE_200);
        modelResult.setMessage(ModelResult.SUCCESS);

        if (!entity.getSimpleName().equals(OBJECT_STR)) {
            Object entityObj = ClassKit.newInstance(entity);
            List<Field> fieldList = getFields(entity);
            for (Field f : fieldList) {
                // 跳过private static final变量
                if (f.getModifiers() != (Modifier.FINAL + Modifier.PRIVATE + Modifier.STATIC)) {
                    BeanKit.setProperty(entityObj, f.getName(), initInstanceForField(f));
                }
            }
            modelResult.setEntity(entityObj);
        } else if (!lists.getSimpleName().equals(OBJECT_STR)) {
            List<Object> entityList = Lists.newArrayList();
            buildResponseEntityList(lists, entityList);
            modelResult.setList(entityList);
        } else if (!pages.getSimpleName().equals(OBJECT_STR)) {
            ResultPage resultPage = new ResultPage();
            resultPage.setPageNo(NUM_ONE);
            resultPage.setTotalCount(30);
            resultPage.setPageSize(3);
            resultPage.setTotalPage(10);

            List<Object> entityList = Lists.newArrayList();
            buildResponseEntityList(pages, entityList);
            resultPage.setItems(entityList);
            modelResult.setResultPage(resultPage);
        }
        return modelResult;
    }

    private void buildResponseEntityList(final Class lists, final List<Object> entityList) throws IllegalAccessException {
        for (int j = 0; j < ARRAY_OBJECT_PROPERTY_LIMIT; j++) {
            Object entityObj = ClassKit.newInstance(lists);
            List<Field> fieldList = getFields(lists);
            for (Field f : fieldList) {
                // 跳过private static final变量
                if (f.getModifiers() != (Modifier.FINAL + Modifier.PRIVATE + Modifier.STATIC)) {
                    BeanKit.setProperty(entityObj, f.getName(), initInstanceForField(f));
                }
            }
            entityList.add(entityObj);
        }
    }

    private Map<String, Object> buildRequestDocParams(final HandlerMethod handler) {
        Method method = handler.getMethod();
        RequestMapping methodMapping = method.getAnnotation(RequestMapping.class);

        Map<String, Object> inputParams = Maps.newHashMap();

        Map<String, Object> bodyResult = Maps.newHashMap();
        Map<String, Object> kvResult = Maps.newHashMap();
        Map<String, Object> headerResult = Maps.newHashMap();

        List<KvParam> kvParams = new ArrayList<>();
        List<BodyParam> bodyParams = new ArrayList<>();
        List<HeaderParam> headerParams = new ArrayList<>();

        if (methodMapping.headers().length > 0) {
            HeaderParam version = new HeaderParam("version", methodMapping.headers()[0].split("=")[NUM_ONE], "请求版本", null);
            headerParams.add(version);
        }

        if (methodMapping.headers().length > NUM_ONE) {
            HeaderParam platform = new HeaderParam("platform", methodMapping.headers()[NUM_ONE].split("=")[NUM_ONE], "请求平台", null);
            headerParams.add(platform);
        }

        if (methodMapping.headers().length > NUM_TWO) {
            HeaderParam device = new HeaderParam("device", methodMapping.headers()[NUM_TWO].split("=")[NUM_ONE], "请求设备", null);
            headerParams.add(device);
        }

        HeaderParam deviceNo = new HeaderParam("deviceNo", StrKit.S_EMPTY, "设备ID/IP(APP必填,PC可选)", "text");
        headerParams.add(deviceNo);

        HeaderParam deviceModel = new HeaderParam("deviceModel", StrKit.S_EMPTY, "设备型号(APP必填,PC可选)", "text");
        headerParams.add(deviceModel);

        HeaderParam osVersion = new HeaderParam("osVersion", StrKit.S_EMPTY, "系统版本(APP必填,PC可选)", "text");
        headerParams.add(osVersion);

        MethodParameter[] methodParameters = handler.getMethodParameters();
        long index = 4;
        for (MethodParameter methodParameter : methodParameters) {
            if (methodParameter.hasParameterAnnotation(FromJson.class)) {
                // 带有@FromJson一律构建为JSON格式参数
                index = buildJsonParams(bodyParams, index, methodParameter);
            } else {
                // 未带有@FromJson一律构建为KV格式参数
                buildKvParams(kvParams, methodParameter);
            }
        }

        bodyResult.put("total", bodyParams.size());
        bodyResult.put("rows", bodyParams);

        kvResult.put("total", kvParams.size());
        kvResult.put("rows", kvParams);

        headerResult.put("total", headerParams.size());
        headerResult.put("rows", headerParams);

        inputParams.put("body", bodyResult);
        inputParams.put("kv", kvResult);
        inputParams.put("header", headerResult);
        return inputParams;
    }

    private List<ResParam> buildResponseDocResult(final HandlerMethod handler) {
        Method method = handler.getMethod();
        MethodDoc methodDoc = method.getAnnotation(MethodDoc.class);
        Class entity = methodDoc.entity();
        Class lists = methodDoc.lists();
        Class pages = methodDoc.pages();

        List<ResParam> resParams = new ArrayList<>();
        long index = 0;
        List<ResParam> children = new ArrayList<>();
        index = initResParam(resParams, index, children, null);

        if (!entity.getSimpleName().equals(OBJECT_STR)) {
            ResParam entityParam = new ResParam(++index, "entity", StrKit.S_EMPTY, "实体对象", entity.getSimpleName());
            children.add(entityParam);

            List<ResParam> entityChildren = new ArrayList<>();
            entityParam.setChildren(entityChildren);

            List<Field> fieldList = getFields(entity);

            for (Field field : fieldList) {
                index = buildFieldParamsForDoc(index, entityChildren, field, 0);
            }
        } else if (!lists.getSimpleName().equals(OBJECT_STR)) {
            buildResParamForList(lists, index, children);
        } else if (!pages.getSimpleName().equals(OBJECT_STR)) {
            ResParam recordCountParam = new ResParam(++index, "recordCount", "3", "当前页记录数", "Integer");
            children.add(recordCountParam);

            ResParam totalCountParam = new ResParam(++index, "totalCount", "30", "总记录数", "Integer");
            children.add(totalCountParam);

            ResParam pageNoParam = new ResParam(++index, "pageNo", "1", "当前页码", "Integer");
            children.add(pageNoParam);

            ResParam totalPageParam = new ResParam(++index, "totalPage", "10", "总页数", "Integer");
            children.add(totalPageParam);

            ResParam pageSizeParam = new ResParam(++index, "pageSize", "3", "每页大小", "Integer");
            children.add(pageSizeParam);

            buildResParamForList(pages, index, children);
        }
        return resParams;
    }

    private void buildResParamForList(final Class lists, long index, final List<ResParam> children) {
        ResParam listParam = new ResParam(++index, "list", StrKit.S_EMPTY, "集合对象", "List<" + lists.getSimpleName() + ">");
        children.add(listParam);

        List<ResParam> listChildren = new ArrayList<>();
        listParam.setChildren(listChildren);

        buildArrayObjectForDoc(lists, index, listChildren);
    }

    private long initResParam(final List<ResParam> resParams, long index, final List<ResParam> children, final ModelResult result) {
        ResParam codeParam = new ResParam(++index, "code", result == null ? "200" : String.valueOf(result.getCode()), "HTTP状态", "Integer");
        resParams.add(codeParam);

        ResParam dataParam = new ResParam(++index, "data", StrKit.S_EMPTY, "响应数据", StrKit.S_EMPTY);
        resParams.add(dataParam);

        dataParam.setChildren(children);

        ResParam msgParam = new ResParam(++index, "message", result == null ? ModelResult.SUCCESS : result.getMessage(), "处理信息", "String");
        children.add(msgParam);
        return index;
    }

    private void buildArrayObjectForDoc(final Class lists, long index, final List<ResParam> listChildren) {
        for (int j = 0; j < ARRAY_OBJECT_PROPERTY_LIMIT; j++) {
            ResParam listItem = new ResParam(++index, "[" + j + "]", StrKit.S_EMPTY, StrKit.S_EMPTY, lists.getSimpleName());
            listChildren.add(listItem);

            List<ResParam> itemChildren = new ArrayList<>();
            listItem.setChildren(itemChildren);

            List<Field> fieldList = getFields(lists);

            for (Field field : fieldList) {
                index = buildFieldParamsForDoc(index, itemChildren, field, 0);
            }
        }
    }

    private Object initInstanceForField(final Field field) throws IllegalAccessException {
        InitField initField = field.getAnnotation(InitField.class);
        if (Func.isEmpty(initField)) {
            return null;
        }

        if (List.class.isAssignableFrom(field.getType())) {
            return initFieldListValue(field, initField);
        } else if (field.getType().isArray()) {
            return initFieldArrayValue(field, initField);
        } else if (Set.class.isAssignableFrom(field.getType())) {
            List<Object> initList = initFieldListValue(field, initField);
            return Sets.newHashSet(initList);
        } else if (String.class.isAssignableFrom(field.getType())) {
            return StrKit.isEmpty(initField.value()) ? StrKit.S_EMPTY : String.valueOf(initField.value());
        } else if (Long.class.isAssignableFrom(field.getType())) {
            return StrKit.isEmpty(initField.value()) ? Long.valueOf(0) : Long.valueOf(initField.value());
        } else if (Integer.class.isAssignableFrom(field.getType())) {
            return StrKit.isEmpty(initField.value()) ? Integer.valueOf(0) : Integer.valueOf(initField.value());
        } else if (Double.class.isAssignableFrom(field.getType())) {
            return StrKit.isEmpty(initField.value()) ? Double.valueOf(0) : Double.valueOf(initField.value());
        } else if (Float.class.isAssignableFrom(field.getType())) {
            return StrKit.isEmpty(initField.value()) ? Float.valueOf(0) : Float.valueOf(initField.value());
        } else {
            List<Field> fieldList = getFields(field.getType());
            Object entityObj = ClassKit.newInstance(field.getType());

            for (Field f : fieldList) {
                // 跳过private static final变量
                if (f.getModifiers() != (Modifier.FINAL + Modifier.PRIVATE + Modifier.STATIC)) {
                    BeanKit.setProperty(entityObj, f.getName(), initInstanceForField(f));
                }
            }
            return entityObj;
        }
    }

    private Object initFieldArrayValue(final Field field, final InitField initField) throws IllegalAccessException {
        List<Object> initList = initFieldListValue(field, initField);
        Class genericClass = field.getType().getComponentType();
        Type fc = field.getGenericType();
        // 如果是泛型参数的类型
        if (!Func.isEmpty(fc) && fc instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) fc;
            // 得到泛型里的class类型对象。
            genericClass = (Class) pt.getActualTypeArguments()[0];
        }

        if (Func.isEmpty(genericClass)) {
            return initList.toArray();
        }

        if (String.class.isAssignableFrom(genericClass)) {
            String[] initArr = new String[initList.size()];
            for (int i = 0; i < initList.size(); i++) {
                initArr[i] = String.valueOf(initList.get(i));
            }
            return initArr;
        } else if (Long.class.isAssignableFrom(genericClass)) {
            Long[] initArr = new Long[initList.size()];
            for (int i = 0; i < initList.size(); i++) {
                initArr[i] = Long.valueOf(String.valueOf(initList.get(i)));
            }
            return initArr;
        } else if (Integer.class.isAssignableFrom(genericClass)) {
            Integer[] initArr = new Integer[initList.size()];
            for (int i = 0; i < initList.size(); i++) {
                initArr[i] = Integer.valueOf(String.valueOf(initList.get(i)));
            }
            return initArr;
        } else if (Double.class.isAssignableFrom(genericClass)) {
            Double[] initArr = new Double[initList.size()];
            for (int i = 0; i < initList.size(); i++) {
                initArr[i] = Double.valueOf(String.valueOf(initList.get(i)));
            }
            return initArr;
        } else if (Float.class.isAssignableFrom(genericClass)) {
            Float[] initArr = new Float[initList.size()];
            for (int i = 0; i < initList.size(); i++) {
                initArr[i] = Float.valueOf(String.valueOf(initList.get(i)));
            }
            return initArr;
        }

        Object array = Array.newInstance(genericClass, initList.size());
        for (int i = 0; i < initList.size(); i++) {
            Array.set(array, i, initList.get(i));
        }
        return array;
    }

    private List<Object> initFieldListValue(final Field field, final InitField initField) throws IllegalAccessException {
        Class genericClass = field.getType().getComponentType();
        Type fc = field.getGenericType();

        // 如果是泛型参数的类型
        if (!Func.isEmpty(fc) && fc instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) fc;
            // 得到泛型里的class类型对象。
            genericClass = (Class) pt.getActualTypeArguments()[0];
        }

        List<Object> initList = Lists.newArrayList();
        List fieldValue = JsonKit.fromJson(initField.value(), List.class);
        if (Func.isEmpty(genericClass)) {
            return initList;
        }

        if (String.class.isAssignableFrom(genericClass)) {
            for (Object obj : fieldValue) {
                initList.add(String.valueOf(obj.toString()));
            }
            return initList;
        } else if (Long.class.isAssignableFrom(genericClass)) {
            for (Object obj : fieldValue) {
                initList.add(Long.valueOf(obj.toString()));
            }
            return initList;
        } else if (Integer.class.isAssignableFrom(genericClass)) {
            for (Object obj : fieldValue) {
                initList.add(Integer.valueOf(obj.toString()));
            }
            return initList;
        } else if (Double.class.isAssignableFrom(genericClass)) {
            for (Object obj : fieldValue) {
                initList.add(Double.valueOf(obj.toString()));
            }
            return initList;
        } else if (Float.class.isAssignableFrom(genericClass)) {
            for (Object obj : fieldValue) {
                initList.add(Float.valueOf(obj.toString()));
            }
            return initList;
        } else {
            List<Field> fieldList = getFields(genericClass);
            initInstanceForFieldList(genericClass, initList, fieldList);
            return initList;
        }
    }

    private void initInstanceForFieldList(final Class genericClass, final List<Object> initList, final List<Field> fieldList) throws IllegalAccessException {
        for (int i = 0; i < ARRAY_OBJECT_PROPERTY_LIMIT; i++) {
            Object entityObj = ClassKit.newInstance(genericClass);
            for (Field f : fieldList) {
                // 跳过private static final变量
                if (f.getModifiers() != (Modifier.FINAL + Modifier.PRIVATE + Modifier.STATIC)) {
                    BeanKit.setProperty(entityObj, f.getName(), initInstanceForField(f));
                }
            }
            initList.add(entityObj);
        }
    }

    private long buildFieldParamsForDoc(long index, final List<ResParam> itemChildren, final Field field, int invokeTimes) {
        InitField initField = field.getAnnotation(InitField.class);
        if (Func.isEmpty(initField) || invokeTimes >= CYCLE_OBJECT_PROPERTY_LIMIT) {
            return index;
        }

        ResParam fieldParam = new ResParam(++index, field.getName(), StrKit.S_EMPTY, initField.desc(), field.getType().getSimpleName());
        if (field.getType().isAssignableFrom(List.class) || field.getType().isAssignableFrom(Set.class)) {
            Class genericClass = (Class) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
            index = buildCollectionParamsForDoc(index, field, initField, fieldParam, genericClass, invokeTimes);
        } else if (field.getType().isArray()) {
            Class genericClass = field.getType().getComponentType();
            index = buildCollectionParamsForDoc(index, field, initField, fieldParam, genericClass, invokeTimes);
        } else if (StrKit.isBlank(initField.value())) {
            List<ResParam> fieldParams = new ArrayList<>();
            fieldParam.setChildren(fieldParams);

            invokeTimes += NUM_ONE;
            List<Field> fieldList = getFields(field.getType());
            for (Field f : fieldList) {
                index = buildFieldParamsForDoc(index, fieldParams, f, invokeTimes);
            }
        } else {
            fieldParam.setValue(initField.value());
        }

        itemChildren.add(fieldParam);
        return index;
    }

    private long buildCollectionParamsForDoc(long index, final Field field, final InitField initField, final ResParam fieldParam, Class genericClass, int invokeTimes) {
        List fieldValue = JsonKit.fromJson(initField.value(), List.class);

        List<ResParam> fieldChildren = new ArrayList<>();
        fieldParam.setChildren(fieldChildren);

        if (fieldValue != null) {
            int i = 0;
            for (Object item : fieldValue) {
                ResParam itemParam = new ResParam(++index, "[" + i++ + "]", item.toString(), StrKit.S_EMPTY, genericClass.getSimpleName());
                fieldChildren.add(itemParam);
            }
        } else {
            if (field.getType().isArray()) {
                genericClass = field.getType().getComponentType();
            } else {
                genericClass = (Class) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
            }

            List<Field> fieldList = getFields(genericClass);

            for (int i = 0; i < ARRAY_OBJECT_PROPERTY_LIMIT; i++) {
                ResParam itemParam = new ResParam(++index, "[" + i + "]", StrKit.S_EMPTY, StrKit.S_EMPTY, genericClass.getSimpleName());
                fieldChildren.add(itemParam);

                List<ResParam> fieldParams = new ArrayList<>();
                itemParam.setChildren(fieldParams);
                for (Field f : fieldList) {
                    index = buildFieldParamsForDoc(index, fieldParams, f, invokeTimes);
                }
            }
        }
        return index;
    }

    private void buildKvParams(final List<KvParam> kvParams, final MethodParameter methodParameter) {
        InitField initField = methodParameter.getParameterAnnotation(InitField.class);
        if (Func.isEmpty(initField)) {
            return;
        }

        if (String.class.isAssignableFrom(methodParameter.getParameterType())) {
            KvParam kvParam = new KvParam(initField.name(), initField.value(), "text", "String", initField.desc(), buildRuleDesc(methodParameter.getParameterAnnotations()));
            kvParams.add(kvParam);
        } else if (Long.class.isAssignableFrom(methodParameter.getParameterType())) {
            KvParam kvParam = new KvParam(initField.name(), initField.value(), "text", "Long", initField.desc(), buildRuleDesc(methodParameter.getParameterAnnotations()));
            kvParams.add(kvParam);
        } else if (Integer.class.isAssignableFrom(methodParameter.getParameterType())) {
            KvParam kvParam = new KvParam(initField.name(), initField.value(), "text", "Integer", initField.desc(), buildRuleDesc(methodParameter.getParameterAnnotations()));
            kvParams.add(kvParam);
        } else if (Double.class.isAssignableFrom(methodParameter.getParameterType())) {
            KvParam kvParam = new KvParam(initField.name(), initField.value(), "text", "Double", initField.desc(), buildRuleDesc(methodParameter.getParameterAnnotations()));
            kvParams.add(kvParam);
        } else if (Float.class.isAssignableFrom(methodParameter.getParameterType())) {
            KvParam kvParam = new KvParam(initField.name(), initField.value(), "text", "Float", initField.desc(), buildRuleDesc(methodParameter.getParameterAnnotations()));
            kvParams.add(kvParam);
        } else if (List.class.isAssignableFrom(methodParameter.getParameterType())) {
            Class genericClass = (Class) ((ParameterizedType) methodParameter.getGenericParameterType()).getActualTypeArguments()[0];
            List fieldValue = JsonKit.fromJson(initField.value(), List.class);
            if (fieldValue != null) {
                buildCollectionParams(kvParams, methodParameter, initField, genericClass, fieldValue);
            }
        } else if (Set.class.isAssignableFrom(methodParameter.getParameterType())) {
            Class genericClass = (Class) ((ParameterizedType) methodParameter.getGenericParameterType()).getActualTypeArguments()[0];
            List fieldValue = JsonKit.fromJson(initField.value(), List.class);
            if (fieldValue != null) {
                buildCollectionParams(kvParams, methodParameter, initField, genericClass, fieldValue);
            }
        } else if (methodParameter.getParameterType().isArray()) {
            Class genericClass = methodParameter.getParameterType().getComponentType();
            List fieldValue = JsonKit.fromJson(initField.value(), List.class);
            if (fieldValue != null) {
                buildCollectionParams(kvParams, methodParameter, initField, genericClass, fieldValue);
            }
        } else {
            Class genericClass = methodParameter.getParameterType();
            if (StrKit.isBlank(initField.value())) {
                buildObjectKvParams(kvParams, genericClass);
            }
        }
    }

    private void buildObjectKvParams(final List<KvParam> kvParams, final Class genericClass) {
        List<Field> fieldList = getFields(genericClass);
        for (Field field : fieldList) {
            InitField initField = field.getAnnotation(InitField.class);
            if (String.class.isAssignableFrom(field.getType())) {
                KvParam kvParam = new KvParam(field.getName(), initField.value(), "text", "String", initField.desc(), buildRuleDesc(field.getAnnotations()));
                kvParams.add(kvParam);
            } else if (Long.class.isAssignableFrom(field.getType())) {
                KvParam kvParam = new KvParam(field.getName(), Long.valueOf(initField.value()).toString(), "text", "Long", initField.desc(), buildRuleDesc(field.getAnnotations()));
                kvParams.add(kvParam);
            } else if (Integer.class.isAssignableFrom(field.getType())) {
                KvParam kvParam = new KvParam(field.getName(), Integer.valueOf(initField.value()).toString(), "text", "Integer", initField.desc(), buildRuleDesc(field.getAnnotations()));
                kvParams.add(kvParam);
            } else if (Double.class.isAssignableFrom(field.getType())) {
                KvParam kvParam = new KvParam(field.getName(), Double.valueOf(initField.value()).toString(), "text", "Double", initField.desc(), buildRuleDesc(field.getAnnotations()));
                kvParams.add(kvParam);
            } else if (Float.class.isAssignableFrom(field.getType())) {
                KvParam kvParam = new KvParam(field.getName(), Float.valueOf(initField.value()).toString(), "text", "Float", initField.desc(), buildRuleDesc(field.getAnnotations()));
                kvParams.add(kvParam);
            }
        }
    }

    private void buildCollectionParams(final List<KvParam> kvParams, final MethodParameter methodParameter, final InitField initField, final Class genericClass, final List fieldValue) {
        int i = 0;
        for (Object item : fieldValue) {
            if (String.class.isAssignableFrom(genericClass)) {
                KvParam kvParam = new KvParam(initField.name() + "[" + i++ + "]", item.toString(), "text", "String", initField.desc(), buildRuleDesc(methodParameter.getParameterAnnotations()));
                kvParams.add(kvParam);
            } else if (Long.class.isAssignableFrom(genericClass)) {
                KvParam kvParam = new KvParam(initField.name() + "[" + i++ + "]", Long.valueOf(item.toString()).toString(), "text", "Long", initField.desc(), buildRuleDesc(methodParameter.getParameterAnnotations()));
                kvParams.add(kvParam);
            } else if (Integer.class.isAssignableFrom(genericClass)) {
                KvParam kvParam = new KvParam(initField.name() + "[" + i++ + "]", Integer.valueOf(item.toString()).toString(), "text", "Integer", initField.desc(), buildRuleDesc(methodParameter.getParameterAnnotations()));
                kvParams.add(kvParam);
            } else if (Double.class.isAssignableFrom(genericClass)) {
                KvParam kvParam = new KvParam(initField.name() + "[" + i++ + "]", Double.valueOf(item.toString()).toString(), "text", "Double", initField.desc(), buildRuleDesc(methodParameter.getParameterAnnotations()));
                kvParams.add(kvParam);
            } else if (Float.class.isAssignableFrom(genericClass)) {
                KvParam kvParam = new KvParam(initField.name() + "[" + i++ + "]", Float.valueOf(item.toString()).toString(), "text", "Float", initField.desc(), buildRuleDesc(methodParameter.getParameterAnnotations()));
                kvParams.add(kvParam);
            }
        }
    }

    private long buildJsonParams(final List<BodyParam> bodyParams, long index, final MethodParameter methodParameter) {
        Class curClass = methodParameter.getParameterType();
        if (String.class.isAssignableFrom(curClass)
            || Long.class.isAssignableFrom(curClass)
            || Integer.class.isAssignableFrom(curClass)
            || Double.class.isAssignableFrom(curClass)
            || Float.class.isAssignableFrom(curClass)) {
            // 构建基本类型参数
            InitField initField = methodParameter.getParameterAnnotation(InitField.class);
            if (Func.isEmpty(initField)) {
                return index;
            }

            BodyParam fieldParam = new BodyParam(++index, initField.name(), initField.value(), initField.desc(), curClass.getSimpleName(), buildRuleDesc(methodParameter.getParameterAnnotations()));
            bodyParams.add(fieldParam);
        } else if (List.class.isAssignableFrom(curClass) || Set.class.isAssignableFrom(curClass) || curClass.isArray()) {
            // 构建集合类型参数
            InitField initField = methodParameter.getParameterAnnotation(InitField.class);
            if (Func.isEmpty(initField)) {
                return index;
            }
            BodyParam fieldParam = new BodyParam(++index, initField.name(), StrKit.S_EMPTY, initField.desc(), curClass.getSimpleName(), buildRuleDesc(methodParameter.getParameterAnnotations()));
            bodyParams.add(fieldParam);

            List fieldValue = JsonKit.fromJson(initField.value(), List.class);
            if (Func.isEmpty(fieldValue)) {
                // 构建Object类型集合参数
                if (curClass.isArray()) {
                    int recursiveCount = 0;
                    Class genericClass = methodParameter.getParameterType().getComponentType();
                    index = buildObjectParamsForArray(++recursiveCount, bodyParams, ++index, fieldParam, genericClass);
                } else {
                    int recursiveCount = 0;
                    Class genericClass = (Class) ((ParameterizedType) methodParameter.getGenericParameterType()).getActualTypeArguments()[0];
                    index = buildObjectParamsForArray(++recursiveCount, bodyParams, ++index, fieldParam, genericClass);
                }
            } else {
                // 构建基本包装类型集合参数
                if (curClass.isArray()) {
                    Class genericClass = methodParameter.getParameterType().getComponentType();
                    index = buildFieldParams(bodyParams, ++index, fieldParam, fieldValue, genericClass);
                } else {
                    Class genericClass = (Class) ((ParameterizedType) methodParameter.getGenericParameterType()).getActualTypeArguments()[0];
                    index = buildFieldParams(bodyParams, ++index, fieldParam, fieldValue, genericClass);
                }
            }
        } else {
            // 构建Object类型参数
            List<Field> fieldList = getFields(curClass);
            for (Field field : fieldList) {
                InitField initField = field.getAnnotation(InitField.class);
                if (Func.isEmpty(initField)) {
                    continue;
                }

                BodyParam fieldParam = new BodyParam(++index, field.getName(), StrKit.S_EMPTY, initField.desc(), field.getType().getSimpleName(), buildRuleDesc(field.getAnnotations()));
                bodyParams.add(fieldParam);
                if (List.class.isAssignableFrom(field.getType()) || field.getType().isArray()) {
                    if (StrKit.isNotBlank(initField.value())) {
                        List fieldValue = JsonKit.fromJson(initField.value(), List.class);
                        if (fieldValue != null) {
                            // 构建基本包装类型集合参数
                            if (field.getType().isArray()) {
                                Class genericClass = field.getType().getComponentType();
                                index = buildFieldParams(bodyParams, ++index, fieldParam, fieldValue, genericClass);
                            } else {
                                Class genericClass = (Class) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
                                index = buildFieldParams(bodyParams, ++index, fieldParam, fieldValue, genericClass);
                            }
                        }
                    } else {
                        // 构建Object类型集合参数
                        if (field.getType().isArray()) {
                            int recursiveCount = 0;
                            Class genericClass = field.getType().getComponentType();
                            index = buildObjectParamsForArray(++recursiveCount, bodyParams, ++index, fieldParam, genericClass);
                        } else {
                            int recursiveCount = 0;
                            Class genericClass = (Class) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
                            index = buildObjectParamsForArray(++recursiveCount, bodyParams, ++index, fieldParam, genericClass);
                        }
                    }
                } else if (String.class.isAssignableFrom(field.getType())
                    || Long.class.isAssignableFrom(field.getType())
                    || Integer.class.isAssignableFrom(field.getType())
                    || Double.class.isAssignableFrom(field.getType())
                    || Float.class.isAssignableFrom(field.getType())) {
                    fieldParam.setValue(initField.value());
                    fieldParam.setEditor("text");
                } else {
                    // 对象类型
                    if (StrKit.isBlank(initField.value())) {
                        int recursiveCount = 0;
                        index = buildObjectParams(recursiveCount, bodyParams, ++index, fieldParam, field.getType());
                    }
                }
            }
        }
        return index;
    }

    private long buildObjectParamsForArray(int recursiveCount, final List<BodyParam> bodyParams, long index, final BodyParam fieldParam, final Class clazz) {
        for (int i = 0; i < ARRAY_OBJECT_PROPERTY_LIMIT; i++) {
            BodyParam param = new BodyParam(++index, "[" + i + "]", StrKit.S_EMPTY, StrKit.S_EMPTY, clazz.getSimpleName(), StrKit.S_EMPTY);
            param.setParentId(fieldParam.getId());
            bodyParams.add(param);
            Integer initRecursiveCount = recursiveCount;
            index = buildObjectParams(initRecursiveCount, bodyParams, ++index, param, clazz);
        }
        return index;
    }

    private long buildObjectParams(int recursiveCount, final List<BodyParam> bodyParams, long index, final BodyParam fieldParam, final Class clazz) {
        if (recursiveCount >= CYCLE_OBJECT_PROPERTY_LIMIT) {
            return index;
        }

        List<Field> fieldList = getFields(clazz);
        for (Field field : fieldList) {
            // 基本包装类型
            if (String.class.isAssignableFrom(field.getType())
                || Long.class.isAssignableFrom(field.getType())
                || Integer.class.isAssignableFrom(field.getType())
                || Double.class.isAssignableFrom(field.getType())
                || Float.class.isAssignableFrom(field.getType())) {
                InitField initField = field.getAnnotation(InitField.class);
                if (initField != null) {
                    BodyParam itemParam = new BodyParam(++index, field.getName(), initField.value(), StrKit.S_EMPTY, field.getType().getSimpleName(), buildRuleDesc(field.getAnnotations()));
                    itemParam.setDesc(initField.desc());
                    itemParam.setParentId(fieldParam.getId());
                    itemParam.setEditor("text");
                    bodyParams.add(itemParam);
                }
            } else if (List.class.isAssignableFrom(field.getType()) || field.getType().isArray()) {
                // 集合类型 仅支持List/Array
                Class genericClass;
                if (field.getType().isArray()) {
                    genericClass = field.getType().getComponentType();
                } else {
                    genericClass = (Class) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
                }

                InitField initField = field.getAnnotation(InitField.class);
                if (initField != null) {
                    BodyParam itemParam = new BodyParam(++index, field.getName(), StrKit.S_EMPTY, StrKit.S_EMPTY, field.getType().getSimpleName(), buildRuleDesc(field.getAnnotations()));
                    itemParam.setDesc(initField.desc());
                    itemParam.setParentId(fieldParam.getId());
                    itemParam.setEditor("text");
                    bodyParams.add(itemParam);
                    // 构建：集合元素为基本类型
                    if (StrKit.isNotBlank(initField.value())) {
                        List fieldValue = JsonKit.fromJson(initField.value(), List.class);
                        index = buildFieldParams(bodyParams, ++index, itemParam, fieldValue, genericClass);
                    } else {
                        // 构建：集合元素为Object类型
                        index = buildObjectParamsForArray(++recursiveCount, bodyParams, ++index, itemParam, genericClass);
                    }
                }
            } else {
                // 对象类型
                InitField initField = field.getAnnotation(InitField.class);
                if (initField != null) {
                    BodyParam itemParam = new BodyParam(++index, field.getName(), StrKit.S_EMPTY, StrKit.S_EMPTY, field.getType().getSimpleName(), buildRuleDesc(field.getAnnotations()));
                    itemParam.setDesc(initField.desc());
                    itemParam.setParentId(fieldParam.getId());
                    itemParam.setEditor("text");
                    bodyParams.add(itemParam);

                    if (StrKit.isBlank(initField.value())) {
                        index = buildObjectParams(++recursiveCount, bodyParams, ++index, itemParam, field.getType());
                    }
                }
            }
        }
        return index;
    }

    private long buildFieldParams(final List<BodyParam> bodyParams, long index, final BodyParam fieldParam, final List fieldValue, final Class genericClass) {
        int i = 0;
        for (Object item : fieldValue) {
            if (String.class.isAssignableFrom(genericClass)) {
                BodyParam itemParam = new BodyParam(++index, "[" + i++ + "]", item.toString(), StrKit.S_EMPTY, genericClass.getSimpleName(), StrKit.S_EMPTY);
                itemParam.setParentId(fieldParam.getId());
                itemParam.setEditor("text");
                bodyParams.add(itemParam);
            } else if (Long.class.isAssignableFrom(genericClass)) {
                BodyParam itemParam = new BodyParam(++index, "[" + i++ + "]", Long.valueOf(item.toString()).toString(), StrKit.S_EMPTY, genericClass.getSimpleName(), StrKit.S_EMPTY);
                itemParam.setParentId(fieldParam.getId());
                itemParam.setEditor("text");
                bodyParams.add(itemParam);
            } else if (Integer.class.isAssignableFrom(genericClass)) {
                BodyParam itemParam = new BodyParam(++index, "[" + i++ + "]", Integer.valueOf(item.toString()).toString(), StrKit.S_EMPTY, genericClass.getSimpleName(), StrKit.S_EMPTY);
                itemParam.setParentId(fieldParam.getId());
                itemParam.setEditor("text");
                bodyParams.add(itemParam);
            } else if (Double.class.isAssignableFrom(genericClass)) {
                BodyParam itemParam = new BodyParam(++index, "[" + i++ + "]", Double.valueOf(item.toString()).toString(), StrKit.S_EMPTY, genericClass.getSimpleName(), StrKit.S_EMPTY);
                itemParam.setParentId(fieldParam.getId());
                itemParam.setEditor("text");
                bodyParams.add(itemParam);
            } else if (Float.class.isAssignableFrom(genericClass)) {
                BodyParam itemParam = new BodyParam(++index, "[" + i++ + "]", Float.valueOf(item.toString()).toString(), StrKit.S_EMPTY, genericClass.getSimpleName(), StrKit.S_EMPTY);
                itemParam.setParentId(fieldParam.getId());
                itemParam.setEditor("text");
                bodyParams.add(itemParam);
            }
        }
        return index;
    }

    private String buildRuleDesc(final Annotation[] annotations) {
        StringBuilder rule = new StringBuilder();
        for (Annotation annotation : annotations) {
            if (ValidateDigit.class.isInstance(annotation)) {
                rule.append(((ValidateDigit) annotation).message()).append(";");
            } else if (ValidateInt.class.isInstance(annotation)) {
                rule.append(((ValidateInt) annotation).message()).append(";");
            } else if (ValidateLength.class.isInstance(annotation)) {
                rule.append(((ValidateLength) annotation).message()).append(";");
            } else if (ValidateLong.class.isInstance(annotation)) {
                rule.append(((ValidateLong) annotation).message()).append(";");
            } else if (ValidateNotEmpty.class.isInstance(annotation)) {
                rule.append(((ValidateNotEmpty) annotation).message()).append(";");
            } else if (ValidateNotNull.class.isInstance(annotation)) {
                rule.append(((ValidateNotNull) annotation).message()).append(";");
            } else if (ValidatePattern.class.isInstance(annotation)) {
                rule.append(((ValidatePattern) annotation).message()).append(";");
            } else if (ValidateStringIn.class.isInstance(annotation)) {
                rule.append(((ValidateStringIn) annotation).message()).append(";");
            }
        }
        return rule.toString();
    }

    private List<ResParam> buildResponseTrueResult(final HttpServletRequest request, final Exception exception) {
        List<ResParam> resParams = new ArrayList<>();
        ModelResult result = (ModelResult) request.getAttribute("result");
        if (exception != null) {
            if (result == null) {
                result = new ModelResult(ModelResult.CODE_500);
            } else {
                result.setCode(ModelResult.CODE_500);
            }

            if (exception instanceof IllegalArgumentException) {
                result.setCode(ModelResult.CODE_400);
                result.setMessage(exception.getMessage());
            } else if (exception instanceof UndeclaredThrowableException) {
                result.setMessage(((UndeclaredThrowableException) exception).getUndeclaredThrowable().getMessage());
            } else if (exception instanceof MessageException) {
                result.setCode(((MessageException) exception).getCode());
                result.setMessage(exception.getMessage());
            } else if (exception instanceof ServiceException) {
                result.setCode(((ServiceException) exception).getCode());
                result.setMessage(exception.getMessage());
            } else {
                result.setMessage(exception.getMessage());
            }
        }

        long index = 0;
        List<ResParam> children = new ArrayList<>();
        index = initResParam(resParams, index, children, result);

        if (result.getEntity() != null) {
            buildResParamTrueEntity(result, index, children);
        } else if (CollectionUtils.isNotEmpty(result.getList())) {
            if (!Func.isNullOrZero(result.getRecordCount())
                && !Func.isNullOrZero(result.getTotalCount())
                && !Func.isNullOrZero(result.getPageNo())
                && !Func.isNullOrZero(result.getTotalPage())
                && !Func.isNullOrZero(result.getPageSize())) {
                ResParam recordCountParam = new ResParam(++index, "recordCount", String.valueOf(result.getRecordCount()), "当前页记录数", "Integer");
                children.add(recordCountParam);

                ResParam totalParam = new ResParam(++index, "totalCount", String.valueOf(result.getTotalCount()), "总记录数", "Integer");
                children.add(totalParam);

                ResParam pageNoParam = new ResParam(++index, "pageNo", String.valueOf(result.getPageNo()), "当前页码", "Integer");
                children.add(pageNoParam);

                ResParam totalPageParam = new ResParam(++index, "totalPage", String.valueOf(result.getTotalPage()), "总页数", "Integer");
                children.add(totalPageParam);

                ResParam sizeParam = new ResParam(++index, "pageSize", String.valueOf(result.getPageSize()), "每页大小", "Integer");
                children.add(sizeParam);
            }
            buildResParamTrueList(result, index, children);
        }
        return resParams;
    }

    private void buildResParamTrueList(final ModelResult result, long index, final List<ResParam> children) {
        List items = result.getList();

        ResParam listParam = new ResParam(++index, "list", StrKit.S_EMPTY, "集合对象", "List");
        children.add(listParam);

        List<ResParam> listChildren = new ArrayList<>();
        listParam.setChildren(listChildren);

        for (int j = 0; j < items.size(); j++) {
            ResParam listItem = new ResParam(++index, "[" + j + "]", StrKit.S_EMPTY, StrKit.S_EMPTY, items.get(j).getClass().getSimpleName());
            listChildren.add(listItem);

            List<ResParam> itemChildren = new ArrayList<>();
            listItem.setChildren(itemChildren);

            List<Field> fieldList = getFields(items.get(j).getClass());

            for (Field field : fieldList) {
                index = buildFieldParams(index, items.get(j), itemChildren, field);
            }
        }
    }

    private void buildResParamTrueEntity(final ModelResult result, long index, final List<ResParam> children) {
        Object obj = result.getEntity();
        ResParam entityParam = new ResParam(++index, "entity", StrKit.S_EMPTY, "实体对象", obj.getClass().getSimpleName());
        children.add(entityParam);

        List<ResParam> entityChildren = new ArrayList<>();
        entityParam.setChildren(entityChildren);

        List<Field> fieldList = getFields(obj.getClass());

        for (Field field : fieldList) {
            index = buildFieldParams(index, obj, entityChildren, field);
        }
    }

    private long buildFieldParams(long index, final Object obj, final List<ResParam> children, final Field field) {
        if (StrKit.equalsIgnoreCase(SERIAL_VERSION_UID, field.getName())) {
            return index;
        }

        InitField initField = field.getAnnotation(InitField.class);
        ResParam fieldParam = new ResParam(++index, field.getName(), StrKit.S_EMPTY, initField == null ? StrKit.S_EMPTY : initField.desc(), field.getType().getSimpleName());
        if (List.class.isAssignableFrom(field.getType())) {
            List fieldValue = (List) getFieldValue(obj, field.getName());
            index = buildResParamFieldValue(index, field, fieldParam, fieldValue);
        } else if (Set.class.isAssignableFrom(field.getType())) {
            Set fieldValue = (Set) getFieldValue(obj, field.getName());
            index = buildResParamFieldValue(index, field, fieldParam, fieldValue);
        } else if (field.getType().isArray()) {
            Object[] fieldValue = (Object[]) getFieldValue(obj, field.getName());
            index = buildResParamFieldValue(index, field, fieldParam, Lists.newArrayList(fieldValue));
        } else {
            if (String.class.isAssignableFrom(field.getType())
                || Integer.class.isAssignableFrom(field.getType())
                || Long.class.isAssignableFrom(field.getType())
                || Double.class.isAssignableFrom(field.getType())
                || Float.class.isAssignableFrom(field.getType())) {
                fieldParam.setValue(String.valueOf(getFieldValue(obj, field.getName())));
            } else {
                Object fieldValue = getFieldValue(obj, field.getName());
                if (fieldValue != null) {
                    List<ResParam> fieldChildren = new ArrayList<>();
                    fieldParam.setChildren(fieldChildren);

                    List<Field> fieldList = getFields(fieldValue.getClass());
                    for (Field f : fieldList) {
                        index = buildFieldParams(index, fieldValue, fieldChildren, f);
                    }
                }
            }
        }
        children.add(fieldParam);
        return index;
    }

    private long buildResParamFieldValue(long index, final Field field, final ResParam fieldParam, final Collection fieldValue) {
        InitField initField;
        if (fieldValue != null) {
            List<ResParam> fieldChildren = new ArrayList<>();
            fieldParam.setChildren(fieldChildren);

            Class genericClass = (Class) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
            int i = 0;
            for (Object item : fieldValue) {
                initField = item.getClass().getAnnotation(InitField.class);
                ResParam itemParam = new ResParam(++index, "[" + i++ + "]", StrKit.S_EMPTY, initField == null ? StrKit.S_EMPTY : initField.desc(), genericClass.getSimpleName());
                fieldChildren.add(itemParam);

                if (String.class.isAssignableFrom(item.getClass())
                    || Integer.class.isAssignableFrom(item.getClass())
                    || Long.class.isAssignableFrom(item.getClass())
                    || Double.class.isAssignableFrom(item.getClass())
                    || Float.class.isAssignableFrom(item.getClass())) {
                    itemParam.setValue(item.toString());
                } else {
                    List<ResParam> childrenParams = new ArrayList<>();
                    itemParam.setChildren(childrenParams);

                    List<Field> fieldList = getFields(item.getClass());
                    for (Field f : fieldList) {
                        index = buildFieldParams(index, item, childrenParams, f);
                    }
                }
            }
        }
        return index;
    }

    private List<Field> getFields(final Class clazz) {
        List<Field> fieldList = new ArrayList<>();
        fieldList.addAll(Arrays.asList(clazz.getDeclaredFields()));
        return fieldList;
    }

    /**
     * 处理响应单个实体
     *
     * @param code    编码
     * @param message 消息
     * @param entity  实体对
     * @return 响应对象
     */
    protected final ModelResult responseEntity(final int code, final String message, final Map<String, Object> entity) {
        ModelResult<Map<String, Object>> modelResult = new ModelResult<>(code);
        modelResult.setMessage(message);
        modelResult.setEntity(entity);

        return modelResult;
    }

    /**
     * 处理响应list
     *
     * @param code    编码
     * @param message 消息
     * @param list    实体集合
     * @return 响应对象
     */
    protected final ModelResult responseList(final int code, final String message, final List<ResParam> list) {
        ModelResult<ResParam> modelResult = new ModelResult<>(code);
        modelResult.setMessage(message);
        modelResult.setList(list);

        return modelResult;
    }

    private Object getFieldValue(final Object obj, final String field) {
        String firstLetter = field.substring(0, NUM_ONE).toUpperCase();
        String getMethodName = "get" + firstLetter + field.substring(NUM_ONE);
        Method getMethod;
        try {
            getMethod = obj.getClass().getMethod(getMethodName);
            return getMethod.invoke(obj);
        } catch (Exception e) {
            log.error("getFieldValue occur exception : ", e);
            return StrKit.S_EMPTY;
        }
    }
}
