package cn.afterturn.gen.modular.code.service.impl;

import cn.afterturn.gen.modular.code.dao.TemplateGroupDao;
import cn.afterturn.gen.modular.code.model.TemplateGroupModel;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import cn.afterturn.gen.modular.code.dao.TemplateDao;
import cn.afterturn.gen.modular.code.dao.TemplateFileDao;
import cn.afterturn.gen.modular.code.model.TemplateFileModel;
import cn.afterturn.gen.modular.code.model.TemplateModel;
import cn.afterturn.gen.modular.code.service.ITemplateService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 模板管理Service
 *
 * @author JueYue
 * @Date 2017-09-11 11:22
 */
@Service
public class TemplateServiceImpl implements ITemplateService {

    @Autowired
    private TemplateDao templateDao;
    @Autowired
    private TemplateFileDao templateFileDao;
    @Autowired
    private TemplateGroupDao templateGroupDao;

    @Override
    @Transactional
    public Integer insert(TemplateModel entity, TemplateFileModel fileModel) {
        templateDao.insert(entity);
        fileModel.setTemplateId(entity.getId());
        return templateFileDao.insert(fileModel);
    }

    @Override
    @Transactional
    public Integer deleteById(Integer id) {
        templateFileDao.deleteByTemplateId(id);
        return templateDao.deleteById(id);
    }

    @Override
    @Transactional
    public Integer updateById(TemplateModel entity, TemplateFileModel fileModel) {
        //保存历史版本
        TemplateModel temp = templateDao.selectById(entity.getId());
        temp.setId(null);
        temp.setVersion(2);
        temp.setOriginalId(entity.getId());
        TemplateFileModel tempFileModel = new TemplateFileModel();
        tempFileModel.setTemplateId(entity.getId());
        tempFileModel = templateFileDao.selectOne(tempFileModel);
        if(tempFileModel != null){
            tempFileModel.setId(null);
            insert(temp, tempFileModel);
        }else{
            templateDao.insert(temp);
        }
        //修改当前版本
        fileModel.setTemplateId(entity.getId());
        int nums = templateFileDao.updateTemplateId(fileModel);
        if (nums == 0) {
            templateFileDao.insert(fileModel);
        }
        return templateDao.updateById(entity);
    }

    @Override
    public TemplateModel selectById(Integer id) {
        return templateDao.selectById(id);
    }

    @Override
    public TemplateModel selectOne(TemplateModel entity) {
        return templateDao.selectOne(entity);
    }

    @Override
    public Integer selectCount(TemplateModel model) {
        return templateDao.selectCount(model);
    }

    @Override
    public List<TemplateModel> selectList(TemplateModel model) {
        return templateDao.selectList(model);
    }

    @Override
    public List<TemplateModel> selectPage(Pagination pagination, TemplateModel model, Wrapper<TemplateModel> wrapper) {
        return templateDao.selectPage(pagination, model, wrapper);
    }

    @Override
    public List<TemplateModel> getTemplateByIds(String[] templates) {
        return templateDao.getTemplateByIds(templates);
    }

    @Override
    public List<TemplateModel> getAllTemplateByGroupId(String groupId) {
        List<TemplateModel> list = templateDao.getAllTemplateByGroupId(groupId);
        for (int i = 0; i < (list == null ? 0 : list.size()); i++) {
            TemplateFileModel fileModel = new TemplateFileModel();
            fileModel.setTemplateId(list.get(i).getId());
            list.get(i).setFileModel(templateFileDao.selectOne(fileModel));
        }
        return list;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cloneGroup(String groupId, Integer userId) {
        TemplateGroupModel groupModel = templateGroupDao.selectById(groupId);
        TemplateGroupModel tempGroupModel = new TemplateGroupModel();
        BeanUtils.copyProperties(groupModel, tempGroupModel);
        tempGroupModel.setId(null);
        tempGroupModel.setUserId(userId);
        templateGroupDao.insert(tempGroupModel);
        List<TemplateModel> list = templateDao.getAllTemplateByGroupId(groupId);
        for (int i = 0; i < (list == null ? 0 : list.size()); i++) {
            TemplateModel templateModel = new TemplateModel();
            BeanUtils.copyProperties(list.get(i), templateModel);
            templateModel.setId(null);
            templateModel.setUserId(userId);
            templateModel.setGroupId(tempGroupModel.getId() + "");

            TemplateFileModel fileModel = new TemplateFileModel();
            fileModel.setTemplateId(list.get(i).getId());
            list.get(i).setFileModel(templateFileDao.selectOne(fileModel));
            fileModel.setTemplateId(list.get(i).getId());
            fileModel.setFile(list.get(i).getFileModel().getFile());
            fileModel.setFileType(list.get(i).getFileModel().getFileType());
            templateModel.setVersion(1);
            insert(templateModel, fileModel);
        }
    }

}
