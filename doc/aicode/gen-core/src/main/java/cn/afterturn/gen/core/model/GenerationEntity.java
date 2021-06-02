package cn.afterturn.gen.core.model;

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

import cn.afterturn.gen.core.model.enmus.GenerationType;


/**
 * 主键的参数
 *
 * @author JueYue
 * @date 2014年12月25日
 */
public class GenerationEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Java包名
     */
    private String codePackage;
    /**
     * html包名,如果为空,使用codePackage
     */
    private String htmlPackage;
    /**
     * js包名,如果为空,使用codePackage
     */
    private String jsPackage;
    /**
     * xml包名,如果为空,使用codePackage
     */
    private String xmlPackage;
    /**
     * 功能名称
     */
    private String name;
    /**
     * 数据库表名
     */
    private String tableName;
    /**
     * 主键名称
     */
    private String idName = "id";
    /**
     * 主键类型
     */
    private GenerationType idType = GenerationType.IDENTITY;
    /**
     * 类名,不填使用表名
     */
    private String entityName;
    /**
     * 首字母小写类名
     */
    private String lowerEntityName;
    /**
     * 生成时间
     **/
    private String date;
    /**
     * 作者
     **/
    private String author;
    /**
     * 作者
     **/
    private String copyright;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getIdName() {
        return idName;
    }

    public void setIdName(String idName) {
        this.idName = idName;
    }

    public GenerationType getIdType() {
        return idType;
    }

    public void setIdType(GenerationType idType) {
        this.idType = idType;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getLowerEntityName() {
        if (StringUtils.isEmpty(lowerEntityName)
                || lowerEntityName.equals(entityName)) {
            lowerEntityName = entityName.substring(0, 1).toLowerCase() + entityName.substring(1);
        }
        return lowerEntityName;
    }

    public void setLowerEntityName(String lowerEntityName) {
        this.lowerEntityName = lowerEntityName;
    }

    public String getCodePackage() {
        return codePackage;
    }

    public void setCodePackage(String codePackage) {
        this.codePackage = codePackage;
    }

    public String getHtmlPackage() {
        if (StringUtils.isEmpty(htmlPackage)) {
            return codePackage;
        }
        return htmlPackage;
    }

    public void setHtmlPackage(String htmlPackage) {
        this.htmlPackage = htmlPackage;
    }

    public String getJsPackage() {
        if (StringUtils.isEmpty(jsPackage)) {
            return codePackage;
        }
        return jsPackage;
    }

    public void setJsPackage(String jsPackage) {
        this.jsPackage = jsPackage;
    }

    public String getXmlPackage() {
        if (StringUtils.isEmpty(xmlPackage)) {
            return codePackage;
        }
        return xmlPackage;
    }

    public void setXmlPackage(String xmlPackage) {
        this.xmlPackage = xmlPackage;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }
}
