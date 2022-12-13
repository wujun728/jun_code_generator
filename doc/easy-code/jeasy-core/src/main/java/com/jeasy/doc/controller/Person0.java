package com.jeasy.doc.controller;

import com.jeasy.doc.annotation.InitField;
import lombok.Data;

import java.util.List;

/**
 * @author taomk
 * @version 1.0
 * @since 15-9-8 下午3:43
 */
@Data
public class Person0 {

    // 基本类型属性 start
    @InitField(value = "10", desc = "主键")
    private Long id;

    @InitField(value = "小明", desc = "姓名")
    private String name;

    @InitField(value = "12", desc = "年龄")
    private Integer age;

    @InitField(value = "40.2", desc = "体重")
    private Double weight;
    // 基本类型属性 end

    // 基本类型集合属性 start
    @InitField(value = "[\"小红\",\"小亮\"]", desc = "朋友")
    private List<String> friends;

    @InitField(value = "[\"爸爸\",\"妈妈\"]", desc = "家人")
    private String[] family;
    // 基本类型集合属性 end
}
