package cn.afterturn.gen.modular.code.service.impl;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import cn.afterturn.gen.modular.code.dao.TemplateGroupDao;
import cn.afterturn.gen.modular.code.model.TemplateGroupModel;
import cn.afterturn.gen.modular.code.service.ITemplateGroupService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Service
 *
 * @author JueYue
 * @Date 2017-09-12 13:42
 */
@Service
public class TemplateGroupServiceImpl implements ITemplateGroupService {

    @Autowired
    private TemplateGroupDao templateGroupDao;

    @Override
    public Integer insert(TemplateGroupModel entity) {
        return templateGroupDao.insert(entity);
    }

    @Override
    public Integer deleteById(Integer id) {
        return templateGroupDao.deleteById(id);
    }

    @Override
    public Integer updateById(TemplateGroupModel entity) {
        return templateGroupDao.updateById(entity);
    }

    @Override
    public TemplateGroupModel selectById(Integer id) {
        return templateGroupDao.selectById(id);
    }

    @Override
    public TemplateGroupModel selectOne(TemplateGroupModel entity) {
        return templateGroupDao.selectOne(entity);
    }

    @Override
    public Integer selectCount(TemplateGroupModel model) {
        return templateGroupDao.selectCount(model);
    }

    @Override
    public List<TemplateGroupModel> selectList(TemplateGroupModel model) {
        return templateGroupDao.selectList(model);
    }

    @Override
    public List<TemplateGroupModel> selectPage(Pagination pagination, TemplateGroupModel model, Wrapper<TemplateGroupModel> wrapper) {
        return templateGroupDao.selectPage(pagination, model, wrapper);
    }

    @Override
    public void share(Integer id) {
        templateGroupDao.share(id);
    }

}
