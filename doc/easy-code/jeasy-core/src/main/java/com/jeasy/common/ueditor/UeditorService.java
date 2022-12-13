package com.jeasy.common.ueditor;

import com.jeasy.common.Func;
import com.jeasy.common.json.JsonKit;
import com.jeasy.common.str.StrKit;
import com.jeasy.common.ueditor.define.ActionMap;
import com.jeasy.common.ueditor.define.AppInfo;
import com.jeasy.common.ueditor.define.BaseState;
import com.jeasy.common.ueditor.define.State;
import com.jeasy.common.ueditor.hunter.ImageHunter;
import com.jeasy.common.ueditor.manager.IUeditorFileManager;
import com.jeasy.common.ueditor.upload.Uploader;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
public class UeditorService {

    @Autowired
    private UeditorManager ueditorManager;

    public String exec(HttpServletRequest request) {
        String callbackName = request.getParameter("callback");
        if (callbackName != null) {
            if (!validCallbackName(callbackName)) {
                return new BaseState(false, AppInfo.ILLEGAL).toJSONString();
            }
            return callbackName + "(" + invoke(request) + ");";
        } else {
            return invoke(request);
        }
    }

    private String invoke(HttpServletRequest request) {
        String actionType = request.getParameter("action");
        String rootPath = request.getContextPath();
        String ctxPath = request.getContextPath();

        if (actionType == null || !ActionMap.ACTION_MAP.containsKey(actionType)) {
            return new BaseState(false, AppInfo.INVALID_ACTION).toJSONString();
        }
        if (ueditorManager == null || !ueditorManager.valid()) {
            return new BaseState(false, AppInfo.CONFIG_ERROR).toJSONString();
        }

        IUeditorFileManager fileManager = ueditorManager.getFileManager();

        State state = null;
        int actionCode = ActionMap.getType(actionType);
        ActionConfig conf = null;

        switch (actionCode) {
            case ActionMap.CONFIG:
                UeditorConfig allConfig = ueditorManager.getConfig();
                String imageUrlPrefix = allConfig.getImageUrlPrefix();
                String scrawlUrlPrefix = allConfig.getScrawlUrlPrefix();
                String snapscreenUrlPrefix = allConfig.getSnapscreenUrlPrefix();
                String catcherUrlPrefix = allConfig.getCatcherUrlPrefix();
                String videoUrlPrefix = allConfig.getVideoUrlPrefix();
                String fileUrlPrefix = allConfig.getFileUrlPrefix();
                String imageManagerUrlPrefix = allConfig.getImageManagerUrlPrefix();
                String fileManagerUrlPrefix = allConfig.getFileManagerUrlPrefix();

                if (StrKit.isBlank(imageUrlPrefix)) {
                    allConfig.setImageUrlPrefix(ctxPath);
                }
                if (StrKit.isBlank(scrawlUrlPrefix)) {
                    allConfig.setScrawlUrlPrefix(ctxPath);
                }
                if (StrKit.isBlank(snapscreenUrlPrefix)) {
                    allConfig.setSnapscreenUrlPrefix(ctxPath);
                }
                if (StrKit.isBlank(catcherUrlPrefix)) {
                    allConfig.setCatcherUrlPrefix(ctxPath);
                }
                if (StrKit.isBlank(videoUrlPrefix)) {
                    allConfig.setVideoUrlPrefix(ctxPath);
                }
                if (StrKit.isBlank(fileUrlPrefix)) {
                    allConfig.setFileUrlPrefix(ctxPath);
                }
                if (StrKit.isBlank(imageManagerUrlPrefix)) {
                    allConfig.setImageManagerUrlPrefix(ctxPath);
                }
                if (StrKit.isBlank(fileManagerUrlPrefix)) {
                    allConfig.setFileManagerUrlPrefix(ctxPath);
                }
                return JsonKit.toJson(allConfig);

            case ActionMap.UPLOAD_IMAGE:
            case ActionMap.UPLOAD_SCRAWL:
            case ActionMap.UPLOAD_VIDEO:
            case ActionMap.UPLOAD_FILE:
                conf = ueditorManager.getConfig(actionCode, rootPath);
                state = new Uploader(request, conf).doExec(fileManager);
                break;

            case ActionMap.CATCH_IMAGE:
                conf = ueditorManager.getConfig(actionCode, rootPath);
                String[] list = request.getParameterValues(conf.getFieldName());
                state = new ImageHunter(fileManager, conf).capture(list);
                break;

            case ActionMap.LIST_IMAGE:
            case ActionMap.LIST_FILE:
                conf = ueditorManager.getConfig(actionCode, rootPath);
                int start = getStartIndex(request);
                state = fileManager.list(conf, start);
                break;
            default:
                break;
        }
        return Func.isNotEmpty(state) ? state.toJSONString() : StrKit.S_EMPTY_JSON;
    }

    public int getStartIndex(HttpServletRequest request) {
        String start = request.getParameter("start");
        try {
            return Integer.parseInt(start);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * callback参数验证
     *
     * @param name 参数名
     * @return boolean 是否校验通过
     */
    public boolean validCallbackName(String name) {
        return name.matches("^[a-zA-Z_]+[\\w0-9_]*$");
    }
}
