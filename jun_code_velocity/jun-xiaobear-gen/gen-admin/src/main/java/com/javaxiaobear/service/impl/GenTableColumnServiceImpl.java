package com.javaxiaobear.service.impl;

import com.javaxiaobear.domain.GenTableColumn;
import com.javaxiaobear.mapper.GenTableColumnMapper;
import com.javaxiaobear.service.IGenTableColumnService;
import com.javaxiaobear.text.Convert;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 业务字段 服务层实现
 * 
 * @author javaxiaobear
 */
@Service
public class GenTableColumnServiceImpl implements IGenTableColumnService
{
	@Resource
	private GenTableColumnMapper genTableColumnMapper;

	/**
     * 查询业务字段列表
     * 
     * @param tableId 业务字段编号
     * @return 业务字段集合
     */
	@Override
	public List<GenTableColumn> selectGenTableColumnListByTableId(Long tableId)
	{
	    return genTableColumnMapper.selectGenTableColumnListByTableId(tableId);
	}
	
    /**
     * 新增业务字段
     * 
     * @param genTableColumn 业务字段信息
     * @return 结果
     */
	@Override
	public int insertGenTableColumn(GenTableColumn genTableColumn)
	{
	    return genTableColumnMapper.insertGenTableColumn(genTableColumn);
	}
	
	/**
     * 修改业务字段
     * 
     * @param genTableColumn 业务字段信息
     * @return 结果
     */
	@Override
	public int updateGenTableColumn(GenTableColumn genTableColumn)
	{
	    return genTableColumnMapper.updateGenTableColumn(genTableColumn);
	}

	/**
     * 删除业务字段对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteGenTableColumnByIds(String ids)
	{
		return genTableColumnMapper.deleteGenTableColumnByIds(Convert.toLongArray(ids));
	}
}
