package com.jeasy.common.ueditor.manager;

import com.jeasy.common.str.StrKit;
import com.jeasy.common.ueditor.ActionConfig;
import com.jeasy.common.ueditor.PathFormat;
import com.jeasy.common.ueditor.define.AppInfo;
import com.jeasy.common.ueditor.define.BaseState;
import com.jeasy.common.ueditor.define.MultiState;
import com.jeasy.common.ueditor.define.State;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
@Slf4j
public class DefaultFileManager implements IUeditorFileManager {

    public static final int BUFFER_SIZE = 1024 * 8;

    private State getState(String rootPath, File[] files) {
        MultiState state = new MultiState(true);

        for (File file : files) {
            if (file == null) {
                break;
            }
            BaseState fileState = new BaseState(true);
            String path = file.getAbsolutePath()
                .replace(rootPath, "/")
                .replace("\\", "/")
                .replace("//", "/");

            fileState.putInfo("url", PathFormat.format(path));
            state.addState(fileState);
        }

        return state;
    }

    @Override
    public State list(ActionConfig conf, int start) {
        String rootPath = conf.getRootPath();
        String dirPath = rootPath + conf.getDir();
        List<String> allowFiles = ManagerUtils.getAllowFiles(conf.getAllowFiles());
        int count = conf.getCount();

        File dir = new File(dirPath);

        if (!dir.exists()) {
            return new BaseState(false, AppInfo.NOT_EXIST);
        }

        if (!dir.isDirectory()) {
            return new BaseState(false, AppInfo.NOT_DIRECTORY);
        }

        Collection<File> list = FileUtils.listFiles(dir, allowFiles.toArray(new String[0]), true);

        Collections.reverse((List<File>) list);

        State state = null;
        if (start < 0 || start > list.size()) {
            state = new MultiState(true);
        } else {
            File[] fileList = Arrays.copyOfRange(list.toArray(new File[0]), start, start + count);
            state = getState(rootPath, fileList);
        }

        state.putInfo("start", start);
        state.putInfo("total", list.size());

        return state;
    }

    @Override
    public State saveFile(byte[] data, String rootPath, String savePath) {
        File file = new File(rootPath + savePath);

        State state = valid(file);
        if (!state.isSuccess()) {
            return state;
        }

        try {
            FileUtils.writeByteArrayToFile(file, data);
        } catch (IOException ioe) {
            return new BaseState(false, AppInfo.IO_ERROR);
        }

        state = new BaseState(true, file.getAbsolutePath());
        state.putInfo("size", data.length);
        state.putInfo("title", file.getName());
        return state;
    }

    @Override
    public State saveFile(InputStream is, String rootPath, String savePath, long maxSize) {
        byte[] dataBuf = new byte[BUFFER_SIZE];
        BufferedInputStream bis = new BufferedInputStream(is, BUFFER_SIZE);

        try {
            File tmpFile = getTmpFile();
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(tmpFile), BUFFER_SIZE);

            int count = 0;
            while ((count = bis.read(dataBuf)) != -1) {
                bos.write(dataBuf, 0, count);
            }
            bos.flush();
            bos.close();

            if (tmpFile.length() > maxSize && tmpFile.delete()) {
                return new BaseState(false, AppInfo.MAX_SIZE);
            }

            State state = saveTmpFile(tmpFile, rootPath + savePath);

            if (!state.isSuccess() && tmpFile.delete()) {
                log.debug(tmpFile.getName() + " delete success ");
            }
            return state;
        } catch (IOException e) {
            // ignore
        }
        return new BaseState(false, AppInfo.IO_ERROR);
    }

    @Override
    public State saveFile(InputStream is, String rootPath, String savePath) {
        byte[] dataBuf = new byte[BUFFER_SIZE];
        BufferedInputStream bis = new BufferedInputStream(is, BUFFER_SIZE);

        State state = null;
        try {
            File tmpFile = getTmpFile();
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(tmpFile), BUFFER_SIZE);

            int count = 0;
            while ((count = bis.read(dataBuf)) != -1) {
                bos.write(dataBuf, 0, count);
            }
            bos.flush();
            bos.close();

            state = saveTmpFile(tmpFile, rootPath + savePath);

            if (!state.isSuccess() && tmpFile.delete()) {
                log.debug(tmpFile.getName() + " delete success ");
            }
            return state;
        } catch (IOException e) {
            // ignore
        }
        return new BaseState(false, AppInfo.IO_ERROR);
    }

    private static File getTmpFile() throws IOException {
        File tmpDir = FileUtils.getTempDirectory();
        if (!tmpDir.exists() && tmpDir.mkdirs()) {
            log.debug(tmpDir.getName() + " mkdirs success ");
        }
        String tmpFileName = (Math.random() * 10000 + StrKit.S_EMPTY).replace(".", StrKit.S_EMPTY);
        return new File(tmpDir, tmpFileName);
    }

    private static State saveTmpFile(File tmpFile, String path) {
        File targetFile = new File(path);

        if (targetFile.canWrite()) {
            return new BaseState(false, AppInfo.PERMISSION_DENIED);
        }
        try {
            FileUtils.moveFile(tmpFile, targetFile);
        } catch (IOException e) {
            return new BaseState(false, AppInfo.IO_ERROR);
        }

        State state = new BaseState(true);
        state.putInfo("size", targetFile.length());
        state.putInfo("title", targetFile.getName());
        return state;
    }

    private static State valid(File file) {
        File parentPath = file.getParentFile();
        if ((!parentPath.exists()) && (!parentPath.mkdirs())) {
            return new BaseState(false, AppInfo.FAILED_CREATE_FILE);
        }
        if (!parentPath.canWrite()) {
            return new BaseState(false, AppInfo.PERMISSION_DENIED);
        }
        return new BaseState(true);
    }

}
