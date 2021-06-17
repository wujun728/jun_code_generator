package com.jun.plugin.project.modulename.service;

import com.jun.plugin.project.modulename.domain.BizNovel;
import com.github.pagehelper.Page;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jun
 * @since 2021-06-17
 */

public interface BizNovelService {

    /**
	* 根据id查询BizNovel
	*/
    BizNovel getBizNovelById(Integer id) throws Exception;

    /**
	* 查询BizNovel列表
	*/
    List<BizNovel> listBizNovel(Map<String, Object> map) throws Exception;

	/**
	* 分页查询BizNovel列表
	*/
	Page<BizNovel> pageBizNovel(Map<String, Object> map, Integer pageNo, Integer pageSize) throws Exception;

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
	Integer deleteBizNovelById(Integer id)	throws Exception;

	/**
	* 根据id逻辑删除BizNovel
	*/
	Integer removeBizNovelById(Integer id)	throws Exception;
}
