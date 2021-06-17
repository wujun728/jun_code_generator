package com.jun.plugin.project.modulename.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jun.plugin.project.modulename.domain.BizNovel;
import com.jun.plugin.project.modulename.mapper.BizNovelMapper;
import com.jun.plugin.project.modulename.service.BizNovelService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jun
 * @since 2021-06-17
 */
@Service
public class BizNovelServiceImpl implements BizNovelService {

    @Autowired
    private BizNovelMapper bizNovelMapper;

    /**
	* 根据id查询BizNovel
	*/
    @Override
    public BizNovel getBizNovelById(Integer id) throws Exception {
        return bizNovelMapper.getBizNovelById(id);
    }

    /**
	* 查询BizNovel列表
	*/
    @Override
    public List<BizNovel> listBizNovel(Map<String, Object> map) throws Exception {
        return bizNovelMapper.listBizNovel(map);
    }

    /**
	* 分页查询BizNovel列表
	*/
    @Override
	public Page<BizNovel> pageBizNovel(Map<String, Object> map, Integer pageNo, Integer pageSize) throws Exception {
        PageHelper.startPage(pageNo,pageSize);
        return bizNovelMapper.pageBizNovel(map);
    }

    /**
	* 新增BizNovel
	*/
    @Override
    @Transactional
    public Integer addBizNovel(BizNovel bizNovel) throws Exception {
        return bizNovelMapper.addBizNovel(bizNovel);
    }

    /**
	* 更新BizNovel
	*/
    @Override
    @Transactional
    public Integer updateBizNovel(BizNovel bizNovel) throws Exception {
        return bizNovelMapper.updateBizNovel(bizNovel);
    }

   	/**
	* 根据id删除BizNovel
	*/
    @Override
    @Transactional
	public Integer deleteBizNovelById(Integer id) throws Exception {
        return bizNovelMapper.deleteBizNovelById(id);
    }

	/**
	* 根据id逻辑删除BizNovel
	*/
    @Override
    @Transactional
	public Integer removeBizNovelById(Integer id) throws Exception {
        return bizNovelMapper.removeBizNovelById(id);
    }
}
