package cn.afterturn.gen.modular.code.service.impl;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import cn.afterturn.gen.modular.code.dao.GenParamDao;
import cn.afterturn.gen.modular.code.model.GenParamModel;
import cn.afterturn.gen.modular.code.service.IGenParamService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
/**
 * 生成参数Service
 *
 * @author JueYue
 * @Date 2017-09-13 09:14
 */
@Service
public class GenParamServiceImpl implements IGenParamService {

    @Autowired
    private GenParamDao genParamDao;

    @Override
    public Integer insert(GenParamModel entity) {
        return genParamDao.insert(entity);
    }

    @Override
    public Integer deleteById(Integer id) {
        return genParamDao.deleteById(id);
    }

    @Override
    public Integer updateById(GenParamModel entity) {
        return genParamDao.updateById(entity);
    }

    @Override
    public GenParamModel selectById(Integer id) {
        return genParamDao.selectById(id);
    }

    @Override
    public GenParamModel selectOne(GenParamModel entity) {
        return genParamDao.selectOne(entity);
    }

    @Override
    public Integer selectCount(GenParamModel model) {
        return genParamDao.selectCount(model);
    }

    @Override
    public List<GenParamModel> selectList(GenParamModel model) {
        return genParamDao.selectList(model);
    }

    @Override
    public List<GenParamModel> selectPage(Pagination pagination, GenParamModel model, Wrapper<GenParamModel> wrapper) {
        return genParamDao.selectPage(pagination,model,wrapper);
    }

}
