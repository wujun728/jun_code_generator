package com.jeasy.doc.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author taomk
 * @version 1.0
 * @since 15-8-24 下午3:00
 */
@Data
@AllArgsConstructor
public class NodeUrl {

    private String url;

    private String desc;

    private String version;

    private String platform;

    private String device;

    private String classDesc;

    private String method;

    private String status;

    private String author;

    private String finish;
}
