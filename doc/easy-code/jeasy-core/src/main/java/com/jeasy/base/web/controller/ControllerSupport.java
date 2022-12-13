package com.jeasy.base.web.controller;

import com.jeasy.base.web.dto.ModelResult;
import com.jeasy.base.web.dto.ResultPage;
import com.jeasy.common.charset.CharsetKit;
import com.jeasy.common.web.ResponseKit;
import com.jeasy.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * HttpSupport
 *
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
@Slf4j
public class ControllerSupport {

    protected static final String RESULT_KEY = "result";

    protected static final String DEFAULT_CHARSET = CharsetKit.DEFAULT_ENCODE;

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpServletResponse response;

    /**
     * 处理响应信息
     *
     * @param code
     * @param message
     * @return
     */
    protected final void responseMessage(final int code, final String message) {
        ModelResult modelResult = new ModelResult(code);
        modelResult.setMessage(message);

        request.setAttribute(RESULT_KEY, modelResult);
    }

    /**
     * 处理响应单个实体
     *
     * @param code
     * @param message
     * @param entity
     * @return
     */
    protected final void responseEntity(final int code, final String message, final Object entity) {
        ModelResult modelResult = new ModelResult(code);
        modelResult.setMessage(message);
        modelResult.setEntity(entity);

        request.setAttribute(RESULT_KEY, modelResult);
    }

    /**
     * 处理响应list
     *
     * @param code
     * @param message
     * @param list
     * @return
     */
    protected final void responseList(final int code, final String message, final List list) {
        ModelResult modelResult = new ModelResult(code);
        modelResult.setMessage(message);
        modelResult.setList(list);

        request.setAttribute(RESULT_KEY, modelResult);
    }

    /**
     * 处理响应page
     *
     * @param code
     * @param message
     * @param totalCount
     * @param items
     * @return
     */
    protected final void responsePage(final int code, final String message, final Long totalCount, final List items, final Integer pageSize, final Integer pageNo) {
        ModelResult modelResult = new ModelResult(code);
        modelResult.setMessage(message);
        modelResult.setResultPage(new ResultPage(totalCount, pageSize, pageNo, items));

        request.setAttribute(RESULT_KEY, modelResult);
    }

    protected final String responseRedirect(final String url) {
        return "redirect:" + url;
    }

    protected final void responseExcelByte(final byte[] bytes, final String fileName) {
        ResponseKit.renderExcelFile(response, bytes, fileName);
    }

    protected final void responseFile(final String content, final String fileName) {
        try {
            byte[] bytes = content.getBytes(DEFAULT_CHARSET);
            ResponseKit.renderFile(response, bytes, fileName);
        } catch (UnsupportedEncodingException e) {
            throw new ServiceException(ModelResult.CODE_500, e);
        }
    }
}
