package com.template.domain;

/**
 * 字典类型表 sys_dict_type
 *
 * @author Wujun
 */
public class SysDictType extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long dictId;
    private String dictName;
    private String dictType;
    private String status;

    public Long getDictId() { return dictId; }
    public void setDictId(Long dictId) { this.dictId = dictId; }
    public String getDictName() { return dictName; }
    public void setDictName(String dictName) { this.dictName = dictName; }
    public String getDictType() { return dictType; }
    public void setDictType(String dictType) { this.dictType = dictType; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}