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
public class Person1Page {

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

    // 对象属性 start
    @InitField(desc = "小朋友")
    private Person0 person0;
    // 对象属性 end

    // 对象集合属性 start
    @InitField(desc = "多个小朋友")
    private List<Person0> person0List;

    @InitField(desc = "很多小朋友")
    private Person0[] person0s;
    // 对象集合属性 end

    @InitField(name = "pageNo", value = "1", desc = "当前页码")
    private Integer pageNo;

    @InitField(name = "pageSize", value = "10", desc = "每页大小")
    private Integer pageSize;
}
