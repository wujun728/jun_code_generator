package com.jeasy.common.ueditor.upload;

import com.jeasy.common.ueditor.ActionConfig;
import com.jeasy.common.ueditor.define.State;
import com.jeasy.common.ueditor.manager.IUeditorFileManager;

import javax.servlet.http.HttpServletRequest;

/**
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
public class Uploader {

    private HttpServletRequest request = null;

    private ActionConfig conf = null;

    public Uploader(HttpServletRequest request, ActionConfig conf) {
        this.request = request;
        this.conf = conf;
    }

    public final State doExec(IUeditorFileManager fileManager) {
        String fieldName = conf.getFieldName();
        State state = null;
        if (conf.isBase64()) {
            state = Base64Uploader.save(fileManager, request.getParameter(fieldName), conf);
        } else {
            state = BinaryUploader.save(fileManager, request, conf);
        }
        return state;
    }
}
