package com.jeasy.doc.controller;

import com.jeasy.doc.annotation.InitField;
import lombok.Data;

/**
 * @author taomk
 * @version 1.0
 * @since 15-9-8 下午4:52
 */
@Data
public class Page {

    @InitField(name = "pageNo", value = "1", desc = "当前页码")
    private Integer pageNo;

    @InitField(name = "pageSize", value = "10", desc = "每页大小")
    private Integer pageSize;
}
