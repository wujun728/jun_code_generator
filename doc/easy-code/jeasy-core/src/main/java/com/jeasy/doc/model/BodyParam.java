package com.jeasy.doc.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NonNull;

/**
 * @author taomk
 * @version 1.0
 * @since 15-8-25 上午11:36
 */
@Data
public class BodyParam {

    @NonNull
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private String value;

    @NonNull
    private String desc;

    @NonNull
    private String type;

    @NonNull
    private String rule;

    private String editor;

    private String iconCls;

    @SerializedName("_parentId")
    private Long parentId;

    private String state;
}
