package cn.afterturn.gen.modular.code.service.impl;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import cn.afterturn.gen.modular.code.dao.TemplateFileDao;
import cn.afterturn.gen.modular.code.model.TemplateFileModel;
import cn.afterturn.gen.modular.code.service.ITemplateFileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
/**
 * 模板内容Service
 *
 * @author JueYue
 * @Date 2017-09-13 11:26
 */
@Service
public class TemplateFileServiceImpl implements ITemplateFileService {

    @Autowired
    private TemplateFileDao templateFileDao;

    @Override
    public Integer insert(TemplateFileModel entity) {
        return templateFileDao.insert(entity);
    }

    @Override
    public Integer deleteById(Integer id) {
        return templateFileDao.deleteById(id);
    }

    @Override
    public Integer updateById(TemplateFileModel entity) {
        return templateFileDao.updateById(entity);
    }

    @Override
    public TemplateFileModel selectById(Integer id) {
        return templateFileDao.selectById(id);
    }

    @Override
    public TemplateFileModel selectOne(TemplateFileModel entity) {
        return templateFileDao.selectOne(entity);
    }

    @Override
    public Integer selectCount(TemplateFileModel model) {
        return templateFileDao.selectCount(model);
    }

    @Override
    public List<TemplateFileModel> selectList(TemplateFileModel model) {
        return templateFileDao.selectList(model);
    }

    @Override
    public List<TemplateFileModel> selectPage(Pagination pagination, TemplateFileModel model, Wrapper<TemplateFileModel> wrapper) {
        return templateFileDao.selectPage(pagination,model,wrapper);
    }

}
