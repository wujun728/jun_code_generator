package com.jeasy.mongodb.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 搜索建议词条
 */
@Document(collection = "word")
public class WordModel {

    public static final String ID_FIELD = "id";
    public static final String TEXT_FIELD = "text";
    public static final String USECOUNT_FIELD = "useCount";
    public static final String RESULTCOUNT_FIELD = "resultCount";

    @Id
    private String id;
    /**
     * 文本
     */
    private String text;
    /**
     * 拼音
     */
    private String pinyin;
    /**
     * 拼音首字母缩写
     */
    private String initials;
    /**
     * 使用次数
     */
    private Integer useCount;
    /**
     * 结果集大小
     */
    private Integer resultCount;

    /**
     * get id
     *
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * set id
     *
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * get 文本
     *
     * @return
     */
    public String getText() {
        return text;
    }

    /**
     * set 文本
     *
     * @param text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * get 拼音
     *
     * @return
     */
    public String getPinyin() {
        return pinyin;
    }

    /**
     * set 拼音
     *
     * @param pinyin
     */
    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    /**
     * get 拼音首字母缩写
     *
     * @return
     */
    public String getInitials() {
        return initials;
    }

    /**
     * set 拼音首字母缩写
     *
     * @param initials
     */
    public void setInitials(String initials) {
        this.initials = initials;
    }

    /**
     * get 使用次数
     *
     * @return
     */
    public Integer getUseCount() {
        return useCount;
    }

    /**
     * set 使用次数
     *
     * @param useCount
     */
    public void setUseCount(Integer useCount) {
        this.useCount = useCount;
    }

    /**
     * get 结果集大小
     *
     * @return
     */
    public Integer getResultCount() {
        return resultCount;
    }

    /**
     * set 结果集大小
     *
     * @param resultCount
     */
    public void setResultCount(Integer resultCount) {
        this.resultCount = resultCount;
    }

}
