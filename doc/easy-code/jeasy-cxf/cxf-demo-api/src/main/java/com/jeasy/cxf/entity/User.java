package com.jeasy.cxf.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author TaoBangren
 * @version 1.0
 * @since 2017/5/25 下午4:00
 */
@Data
public class User implements Serializable {
    private String name;
}
