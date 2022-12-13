package com.jeasy.base.web.resolver;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.jeasy.common.Func;
import com.jeasy.common.json.JsonKit;
import com.jeasy.common.str.StrKit;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.ExtendedServletRequestDataBinder;

import javax.servlet.ServletRequest;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author taomk
 * @version 1.0
 * @since 15-8-4 下午4:45
 */
@Data
public class ArgumentFromJsonResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return true;
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer, final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) throws Exception {
        return bindParametersToTarget(parameter, mavContainer, webRequest, binderFactory);
    }

    private Object bindParametersToTarget(final MethodParameter parameter, final ModelAndViewContainer mavContainer, final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) throws Exception {
        // 获取请求参数别名
        String alias = getAlias(parameter);

        // 拿到obj, 先从ModelAndViewContainer中拿，若没有则创建参数类型的实例
        Object obj = (mavContainer.containsAttribute(alias)) ? mavContainer.getModel().get(alias) : createAttribute(parameter.getParameterType());
        // 获得WebDataBinder，这里的具体WebDataBinder是ExtendedServletRequestDataBinder
        WebDataBinder binder = binderFactory.createBinder(webRequest, obj, alias);

        Object targetObj;
        if (parameter.hasParameterAnnotation(FromJson.class)) {
            // 带有@FromJson注解，则必须从JSON参数中取值
            targetObj = buildTargetFromJsonParams(alias, parameter, webRequest);
            if (Func.isNotEmpty(targetObj)) {
                return targetObj;
            }
        }
        // 未带有@FromJson注解 或 从FromJson中未取到值，则必须从KV参数中取值
        return buildTargetFromKvParams(alias, parameter, webRequest, binder);
    }

    private Object buildTargetFromJsonParams(final String alias, final MethodParameter parameter, final NativeWebRequest webRequest) {
        String fromJson = parseFromJson(webRequest);
        if (StrKit.isNotBlank(fromJson)) {

            // 判断fromJson中是否存在alias
            if (JsonKit.hasKey(fromJson, alias)) {
                // fromJson中alias为 基本类型 || 具体Object类型
                if (JsonKit.isJsonPrimitive(fromJson, alias) || JsonKit.isJsonObject(fromJson, alias)) {
                    return JsonKit.fromJson(fromJson, alias, parameter.getParameterType());
                }

                // fromJson中alias为 集合类型
                if (JsonKit.isJsonArray(fromJson, alias)) {
                    List<Object> target = buildArrayListFromJsonObject(fromJson, alias, parameter);
                    if (parameter.getParameterType().isAssignableFrom(Set.class)) {
                        return Sets.newHashSet(target);
                    } else if (parameter.getParameterType().isArray()) {
                        return target.toArray();
                    } else {
                        return target;
                    }
                }
            }

            if (isObjectParameter(parameter)) {
                // fromJson为 具体Object类型
                return JsonKit.fromJson(fromJson, parameter.getParameterType());
            }
        }
        return null;
    }

    private boolean isObjectParameter(final MethodParameter parameter) {
        return !parameter.getParameterType().isPrimitive()
            && !parameter.getParameterType().isAssignableFrom(Integer.class)
            && !parameter.getParameterType().isAssignableFrom(Long.class)
            && !parameter.getParameterType().isAssignableFrom(Double.class)
            && !parameter.getParameterType().isAssignableFrom(Float.class)
            && !parameter.getParameterType().isAssignableFrom(String.class);
    }

    private List<Object> buildArrayListFromJsonObject(final String fromJson, final String alias, final MethodParameter parameter) {
        List<Object> target = Lists.newArrayList();
        ParameterizedType type = (ParameterizedType) parameter.getGenericParameterType();
        Type[] types = type.getActualTypeArguments();
        if (!Func.isEmpty(types) && types.length >= 1) {
            JsonArray jsonArray = JsonKit.parseJsonArray(fromJson, alias);
            for (int i = 0; i < jsonArray.size(); i++) {
                JsonElement jsonElement = jsonArray.get(i);
                target.add(JsonKit.fromJson(jsonElement, types[0]));
            }
        }
        return target;
    }

    public List<Object> buildArrayListFromKvParams(final MethodParameter parameter, final String[] aliasVals) {
        List<Object> targetList = Lists.newArrayList();
        // 参数List类型，仅支持基本包装类型集合，不支持对象类型集合
        if (!Func.isEmpty(aliasVals)) {
            ParameterizedType type = (ParameterizedType) parameter.getGenericParameterType();
            Type[] types = type.getActualTypeArguments();
            if (types.length == 1) {
                if (types[0].equals(String.class)) {
                    for (String val : aliasVals) {
                        targetList.add(val);
                    }
                } else if (types[0].equals(Integer.class)) {
                    for (String val : aliasVals) {
                        targetList.add(Integer.parseInt(val));
                    }
                } else if (types[0].equals(Long.class)) {
                    for (String val : aliasVals) {
                        targetList.add(Long.parseLong(val));
                    }
                } else if (types[0].equals(Double.class)) {
                    for (String val : aliasVals) {
                        targetList.add(Double.parseDouble(val));
                    }
                } else if (types[0].equals(Float.class)) {
                    for (String val : aliasVals) {
                        targetList.add(Float.parseFloat(val));
                    }
                }
            }
        }
        return targetList;
    }

    private String parseFromJson(final NativeWebRequest webRequest) {
        Map<String, String[]> params = webRequest.getParameterMap();
        for (Map.Entry<String, String[]> param : params.entrySet()) {
            String[] paramVals = param.getValue();
            if (Func.isEmpty(paramVals)) {
                continue;
            }

            String paramVal = paramVals[0].trim();
            if (isNotBlankJson(paramVal)) {
                return paramVal;
            }
        }
        return StrKit.S_EMPTY;
    }

    private boolean isNotBlankJson(String paramVal) {
        return paramVal.startsWith(StrKit.S_DELIM_START) && paramVal.endsWith(StrKit.S_DELIM_END) && !StrKit.equals(paramVal, StrKit.S_EMPTY_JSON);
    }

    private Object buildTargetFromKvParams(final String alias, final MethodParameter parameter, final NativeWebRequest webRequest, final WebDataBinder binder) {
        if (parameter.getParameterType().isAssignableFrom(String.class)) {
            return webRequest.getParameter(alias);
        } else if (parameter.getParameterType().isAssignableFrom(Integer.class)) {
            return Func.isEmpty(webRequest.getParameter(alias)) ? null : Integer.valueOf(webRequest.getParameter(alias));
        } else if (parameter.getParameterType().isAssignableFrom(Long.class)) {
            return Func.isEmpty(webRequest.getParameter(alias)) ? null : Long.valueOf(webRequest.getParameter(alias));
        } else if (parameter.getParameterType().isAssignableFrom(Double.class)) {
            return Func.isEmpty(webRequest.getParameter(alias)) ? null : Double.valueOf(webRequest.getParameter(alias));
        } else if (parameter.getParameterType().isAssignableFrom(Float.class)) {
            return Func.isEmpty(webRequest.getParameter(alias)) ? null : Float.valueOf(webRequest.getParameter(alias));
        } else if (parameter.getParameterType().isAssignableFrom(List.class)) {
            return buildArrayListFromKvParams(parameter, webRequest.getParameterValues(alias));
        } else if (parameter.getParameterType().isAssignableFrom(Set.class)) {
            List<Object> targetList = buildArrayListFromKvParams(parameter, webRequest.getParameterValues(alias));
            return Sets.newHashSet(targetList);
        } else if (parameter.getParameterType().isArray()) {
            List<Object> targetList = buildArrayListFromKvParams(parameter, webRequest.getParameterValues(alias));
            return targetList.toArray();
        } else {
            // 其他，处理Object参数
            Object target = binder.getTarget();
            ServletRequest servletRequest = webRequest.getNativeRequest(ServletRequest.class);
            ((ExtendedServletRequestDataBinder) binder).bind(servletRequest);
            return target;
        }
    }

    private Object createAttribute(final Class<?> parameterType) {
        Object result;
        if (parameterType.isAssignableFrom(String.class)) {
            result = StrKit.S_EMPTY;
        } else if (parameterType.isAssignableFrom(Integer.class)) {
            result = 0;
        } else if (parameterType.isAssignableFrom(Long.class)) {
            result = 0L;
        } else if (parameterType.isAssignableFrom(Double.class)) {
            result = 0d;
        } else if (parameterType.isAssignableFrom(Float.class)) {
            result = 0f;
        } else if (parameterType.isAssignableFrom(List.class)) {
            result = Lists.newArrayList();
        } else if (parameterType.isAssignableFrom(Set.class)) {
            result = Sets.newHashSet();
        } else if (parameterType.isArray()) {
            result = new Object[0];
        } else {
            result = BeanUtils.instantiateClass(parameterType);
        }
        return result;
    }

    /**
     * 获取对象参数的简称
     *
     * @param parameter
     * @return
     */
    private String getAlias(final MethodParameter parameter) {
        String alias = null;
        if (parameter.hasParameterAnnotation(FromJson.class)) {
            alias = parameter.getParameterAnnotation(FromJson.class).key();
        }

        if (parameter.hasParameterAnnotation(RequestParam.class)) {
            alias = parameter.getParameterAnnotation(RequestParam.class).value();
        }

        if (StrKit.isBlank(alias)) {
            alias = parameter.getParameterName();
        }
        return alias;
    }
}
