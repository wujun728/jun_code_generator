package com.jeasy.common.ueditor.upload;

import com.jeasy.common.str.StrKit;
import com.jeasy.common.ueditor.ActionConfig;
import com.jeasy.common.ueditor.PathFormat;
import com.jeasy.common.ueditor.define.AppInfo;
import com.jeasy.common.ueditor.define.BaseState;
import com.jeasy.common.ueditor.define.FileType;
import com.jeasy.common.ueditor.define.State;
import com.jeasy.common.ueditor.manager.IUeditorFileManager;
import org.apache.shiro.codec.Base64;

/**
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
public final class Base64Uploader {

    public static State save(IUeditorFileManager fileManager, String content, ActionConfig conf) {
        byte[] data = decode(content);
        long maxSize = conf.getMaxSize();

        if (!validSize(data, maxSize)) {
            return new BaseState(false, AppInfo.MAX_SIZE);
        }

        String suffix = FileType.getSuffix("JPG");

        String savePath = PathFormat.parse(conf.getSavePath(), conf.getFilename());

        savePath = savePath + suffix;
        String rootPath = conf.getRootPath();

        State storageState = fileManager.saveFile(data, rootPath, savePath);

        if (storageState.isSuccess()) {
            storageState.putInfo("url", PathFormat.format(savePath));
            storageState.putInfo("type", suffix);
            storageState.putInfo("original", StrKit.S_EMPTY);
        }
        return storageState;
    }

    private static byte[] decode(String content) {
        return Base64.decode(content);
    }

    private static boolean validSize(byte[] data, long length) {
        return data.length <= length;
    }

}
