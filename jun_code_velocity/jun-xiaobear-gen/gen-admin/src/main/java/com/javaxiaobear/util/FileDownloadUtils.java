package com.javaxiaobear.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 文件下载工具类
 * 
 * @author javaxiaobear
 * @date 2025-01-08
 */
public class FileDownloadUtils {
    
    private static final Logger logger = LoggerFactory.getLogger(FileDownloadUtils.class);
    
    /**
     * 下载文件到浏览器
     * 
     * @param filePath 文件路径
     * @param fileName 下载时显示的文件名
     * @param response HttpServletResponse
     */
    public static void downloadFile(String filePath, String fileName, HttpServletResponse response) {
        File file = new File(filePath);
        if (!file.exists()) {
            logger.error("文件不存在: {}", filePath);
            try {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "文件不存在");
            } catch (IOException e) {
                logger.error("发送错误响应失败", e);
            }
            return;
        }
        
        try (FileInputStream fis = new FileInputStream(file);
             BufferedInputStream bis = new BufferedInputStream(fis)) {
            
            // 设置响应头
            response.setContentType(getContentType(fileName));
            response.setContentLength((int) file.length());
            response.setHeader("Content-Disposition", 
                "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
            
            // 写入响应流
            try (BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream())) {
                byte[] buffer = new byte[8192];
                int bytesRead;
                while ((bytesRead = bis.read(buffer)) != -1) {
                    bos.write(buffer, 0, bytesRead);
                }
                bos.flush();
            }
            
            logger.info("文件下载成功: {}", fileName);
            
        } catch (IOException e) {
            logger.error("文件下载失败: {}", fileName, e);
            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "文件下载失败");
            } catch (IOException ex) {
                logger.error("发送错误响应失败", ex);
            }
        }
    }
    
    /**
     * 返回文件内容作为ResponseEntity
     * 
     * @param filePath 文件路径
     * @param fileName 文件名
     * @return ResponseEntity<byte[]>
     */
    public static ResponseEntity<byte[]> downloadFileAsResponse(String filePath, String fileName) {
        try {
            Path path = Paths.get(filePath);
            if (!Files.exists(path)) {
                logger.error("文件不存在: {}", filePath);
                return ResponseEntity.notFound().build();
            }
            
            byte[] fileContent = Files.readAllBytes(path);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(getMediaType(fileName));
            headers.setContentDispositionFormData("attachment", URLEncoder.encode(fileName, "UTF-8"));
            headers.setContentLength(fileContent.length);
            
            logger.info("文件下载成功: {}", fileName);
            return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
            
        } catch (IOException e) {
            logger.error("文件下载失败: {}", fileName, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * 删除临时文件
     * 
     * @param filePath 文件路径
     */
    public static void deleteTempFile(String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists() && file.delete()) {
                logger.info("临时文件删除成功: {}", filePath);
            }
        } catch (Exception e) {
            logger.warn("删除临时文件失败: {}", filePath, e);
        }
    }
    
    /**
     * 获取文件的Content-Type
     * 
     * @param fileName 文件名
     * @return Content-Type
     */
    private static String getContentType(String fileName) {
        String extension = getFileExtension(fileName).toLowerCase();
        switch (extension) {
            case "pdf":
                return "application/pdf";
            case "doc":
                return "application/msword";
            case "docx":
                return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
            case "xls":
                return "application/vnd.ms-excel";
            case "xlsx":
                return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
            case "html":
                return "text/html";
            case "txt":
                return "text/plain";
            default:
                return "application/octet-stream";
        }
    }
    
    /**
     * 获取MediaType
     * 
     * @param fileName 文件名
     * @return MediaType
     */
    private static MediaType getMediaType(String fileName) {
        String extension = getFileExtension(fileName).toLowerCase();
        switch (extension) {
            case "pdf":
                return MediaType.APPLICATION_PDF;
            case "doc":
            case "docx":
                return MediaType.APPLICATION_OCTET_STREAM;
            case "xls":
            case "xlsx":
                return MediaType.APPLICATION_OCTET_STREAM;
            case "html":
                return MediaType.TEXT_HTML;
            case "txt":
                return MediaType.TEXT_PLAIN;
            default:
                return MediaType.APPLICATION_OCTET_STREAM;
        }
    }
    
    /**
     * 获取文件扩展名
     * 
     * @param fileName 文件名
     * @return 扩展名
     */
    private static String getFileExtension(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return "";
        }
        int lastDotIndex = fileName.lastIndexOf('.');
        return lastDotIndex > 0 ? fileName.substring(lastDotIndex + 1) : "";
    }
}
