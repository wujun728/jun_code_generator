package com.template.mapper;

import java.util.List;
import com.template.domain.SysDictType;
import org.apache.ibatis.annotations.Mapper;

/**
 * 字典类型 数据层
 *
 * @author Wujun
 */
@Mapper
public interface SysDictTypeMapper
{
    /**
     * 查询所有字典类型
     */
    List<SysDictType> selectDictTypeAll();
}