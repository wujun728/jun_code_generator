package com.jeasy.doc.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author taomk
 * @version 1.0
 * @since 15-8-25 下午4:53
 */
@Data
@AllArgsConstructor
public class HeaderParam {

    private String name;

    private String value;

    private String desc;

    private String editor;
}
