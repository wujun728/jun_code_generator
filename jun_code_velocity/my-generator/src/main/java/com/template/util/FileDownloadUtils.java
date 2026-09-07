package com.template.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * 文件下载工具类
 *
 * @author Wujun
 */
public class FileDownloadUtils {

    private static final Logger log = LoggerFactory.getLogger(FileDownloadUtils.class);

    public static void downloadFile(String filePath, String fileName, HttpServletResponse response) {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new RuntimeException("文件不存在: " + filePath);
        }
        try (FileInputStream fis = new FileInputStream(file);
             ServletOutputStream outputStream = response.getOutputStream()) {
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            response.setContentType("application/octet-stream");
            response.setCharacterEncoding("UTF-8");
            IOUtils.copy(fis, outputStream);
            response.flushBuffer();
        } catch (Exception e) {
            log.error("文件下载失败", e);
            throw new RuntimeException("文件下载失败");
        }
    }

    public static void deleteTempFile(String filePath) {
        try {
            FileUtils.delete(new File(filePath));
        } catch (Exception e) {
            log.warn("删除临时文件失败: {}", filePath);
        }
    }
}