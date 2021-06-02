package cn.afterturn.gen.modular.code.service.impl;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import cn.afterturn.gen.modular.code.dao.TableFieldDbinfoDao;
import cn.afterturn.gen.modular.code.model.TableFieldDbinfoModel;
import cn.afterturn.gen.modular.code.service.ITableFieldDbinfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
/**
 * Service
 *
 * @author JueYue
 * @Date 2017-09-27 20:46
 */
@Service
public class TableFieldDbinfoServiceImpl implements ITableFieldDbinfoService {

    @Autowired
    private TableFieldDbinfoDao tableFieldDbinfoDao;

    @Override
    public Integer insert(TableFieldDbinfoModel entity) {
        return tableFieldDbinfoDao.insert(entity);
    }

    @Override
    public Integer deleteById(Integer id) {
        return tableFieldDbinfoDao.deleteById(id);
    }

    @Override
    public Integer updateById(TableFieldDbinfoModel entity) {
        return tableFieldDbinfoDao.updateById(entity);
    }

    @Override
    public TableFieldDbinfoModel selectById(Integer id) {
        return tableFieldDbinfoDao.selectById(id);
    }

    @Override
    public TableFieldDbinfoModel selectOne(TableFieldDbinfoModel entity) {
        return tableFieldDbinfoDao.selectOne(entity);
    }

    @Override
    public Integer selectCount(TableFieldDbinfoModel model) {
        return tableFieldDbinfoDao.selectCount(model);
    }

    @Override
    public List<TableFieldDbinfoModel> selectList(TableFieldDbinfoModel model) {
        return tableFieldDbinfoDao.selectList(model);
    }

    @Override
    public List<TableFieldDbinfoModel> selectPage(Pagination pagination, TableFieldDbinfoModel model, Wrapper<TableFieldDbinfoModel> wrapper) {
        return tableFieldDbinfoDao.selectPage(pagination,model,wrapper);
    }
    @Override
    public Integer deleteByFieldIds(List<Integer> fieldIds) {
        return tableFieldDbinfoDao.deleteByFieldIds(fieldIds);
    }

    @Override
    public Integer batchInsert(List<TableFieldDbinfoModel> dbInfoModelList) {
        return tableFieldDbinfoDao.batchInsert(dbInfoModelList);
    }
}
