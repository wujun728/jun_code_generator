package com.jeasy.base.web.dto;

import com.google.common.collect.Maps;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * ModelResult
 *
 * @author taobr
 * @version 1.0
 * @since 2015/05/13 17:34
 */
@Data
public final class ModelResult<T> {

    public static final String SUCCESS = "success";
    public static final String FAIL = "fail";
    public static final String UNAUTHORIZED = "未经授权, 请联系管理员";

    public static final int CODE_200 = 200;
    public static final int CODE_400 = 400;
    public static final int CODE_500 = 500;

    /**
     * (未授权） 请求要求身份验证。对于需要登录的网页，服务器可能返回此响应。
     */
    public static final int CODE_401 = 401;
    /**
     * (未授权) 不接受
     */
    public static final int CODE_406 = 406;
    public static final String TOTAL_COUNT = "totalCount";
    public static final String TOTAL_PAGE = "totalPage";
    public static final String PAGE_SIZE = "pageSize";
    public static final String PAGE_NO = "pageNo";
    public static final String RECORD_COUNT = "recordCount";

    private int code;

    private final Map<String, Object> data = Maps.newHashMap();

    public ModelResult() {
    }

    public ModelResult(final int code) {
        this.code = code;
    }

    public ModelResult setResultPage(final ResultPage resultPage) {
        data.put(TOTAL_COUNT, resultPage.getTotalCount());
        data.put(TOTAL_PAGE, resultPage.getTotalPage());
        data.put(PAGE_SIZE, resultPage.getPageSize());
        data.put("list", resultPage.getItems());

        data.put(PAGE_NO, resultPage.getPageNo());
        data.put(RECORD_COUNT, resultPage.getItems() == null ? 0 : resultPage.getItems().size());
        return this;
    }

    public int getTotalCount() {
        if (!data.containsKey(TOTAL_COUNT)) {
            return 0;
        }
        return Integer.parseInt(String.valueOf(data.get(TOTAL_COUNT)));
    }

    public int getTotalPage() {
        if (!data.containsKey(TOTAL_PAGE)) {
            return 0;
        }
        return Integer.parseInt(String.valueOf(data.get(TOTAL_PAGE)));
    }

    public int getPageSize() {
        if (!data.containsKey(PAGE_SIZE)) {
            return 0;
        }
        return Integer.parseInt(String.valueOf(data.get(PAGE_SIZE)));
    }

    public int getPageNo() {
        if (!data.containsKey(PAGE_NO)) {
            return 0;
        }
        return Integer.parseInt(String.valueOf(data.get(PAGE_NO)));
    }

    public int getRecordCount() {
        if (!data.containsKey(RECORD_COUNT)) {
            return 0;
        }
        return Integer.parseInt(String.valueOf(data.get(RECORD_COUNT)));
    }

    public void setMessage(final String message) {
        data.put("message", message);
    }

    public String getMessage() {
        return String.valueOf(data.get("message"));
    }

    public void setList(final List<T> list) {
        data.put("list", list);
    }

    public List<T> getList() {
        return (List<T>) data.get("list");
    }

    public void setEntity(T entity) {
        data.put("entity", entity);
    }

    public T getEntity() {
        return (T) data.get("entity");
    }
}
