package com.jeasy.rabbitmq.entity;

import java.io.Serializable;
import java.util.Random;

/**
 * @author taomk
 * @version 1.0
 * @since 2014/9/22 14:33
 */
public class UserEntity implements Serializable {

    private static final long serialVersionUID = 4645527652929432522L;
    /**
     * id
     */
    private Long id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 年龄
     */
    private int age;
    /**
     * 性别
     */
    private String sex;

    public static UserEntity newInstance() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(System.currentTimeMillis());
        userEntity.setName("倪" + System.currentTimeMillis());
        userEntity.setAge(1);
        userEntity.setSex("男");
        return userEntity;
    }

    /**
     * get 姓名
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * set 姓名
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * get 年龄
     *
     * @return
     */
    public int getAge() {
        return age;
    }

    /**
     * set 年龄
     *
     * @param age
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * get 性别
     *
     * @return
     */
    public String getSex() {
        return sex;
    }

    /**
     * set 性别
     *
     * @param sex
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * get id
     *
     * @return
     */
    public Long getId() {
        return id;
    }

    /**
     * set id
     *
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

}
