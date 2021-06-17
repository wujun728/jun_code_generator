package com.jun.plugin.project.modulename.mapper;

import com.jun.plugin.project.modulename.domain.BizNovel;
import org.apache.ibatis.annotations.Param;
import com.github.pagehelper.Page;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jun
 * @since 2021-06-17
 */

@Mapper
public interface BizNovelMapper {

   /**
	* 根据id查询BizNovel
	*/
	BizNovel getBizNovelById(@Param(value = "id") Integer id) throws Exception;

	/**
	* 查询BizNovel列表
	*/
	List<BizNovel> listBizNovel(Map<String, Object> map) throws Exception;

	/**
	* 分页查询BizNovel列表
	*/
	Page<BizNovel> pageBizNovel(Map<String, Object> map) throws Exception;

	/**
	* 新增BizNovel
	*/
	Integer addBizNovel(BizNovel bizNovel) throws Exception;

	/**
	* 更新BizNovel
	*/
	Integer updateBizNovel(BizNovel bizNovel) throws Exception;

	/**
	* 根据id物理删除BizNovel
	*/
	Integer deleteBizNovelById(@Param(value = "id") Integer id)	throws Exception;

	/**
	* 根据id逻辑删除BizNovel
	*/
	Integer removeBizNovelById(@Param(value = "id") Integer id)	throws Exception;
}
