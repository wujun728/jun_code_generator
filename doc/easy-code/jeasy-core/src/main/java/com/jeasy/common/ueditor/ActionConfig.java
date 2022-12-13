package com.jeasy.common.ueditor;

import lombok.Data;

import java.util.List;

/**
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
@Data
public class ActionConfig {
    private boolean isBase64;
    private long maxSize;
    private List<String> allowFiles;
    private String fieldName;
    private String filename;
    private List<String> filter;
    private String dir;
    private int count;

    private String savePath;
    private String rootPath;
}
