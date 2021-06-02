package cn.afterturn.gen.modular.code.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import cn.afterturn.gen.modular.code.dao.DbInfoDao;
import cn.afterturn.gen.modular.code.model.DbInfoModel;
import cn.afterturn.gen.modular.code.service.IDbInfoService;

/**
 * 数据库管理Service
 *
 * @author JueYue
 * @Date 2017-09-11 11:15
 */
@Service
public class DbInfoServiceImpl extends ServiceImpl<DbInfoDao, DbInfoModel> implements IDbInfoService {

    @Autowired
    private DbInfoDao dbInfoDao;

    @Override
    public DbInfoModel selectOne(DbInfoModel model) {
        return dbInfoDao.selectOne(model);
    }

    @Override
    public List<DbInfoModel> selectList(DbInfoModel model) {
        return dbInfoDao.selectList(model, new EntityWrapper<DbInfoModel>());
    }

    @Override
    public List<DbInfoModel> selectList(DbInfoModel model, Wrapper<DbInfoModel> wrapper) {
        return dbInfoDao.selectList(model, wrapper);
    }

    @Override
    public List<DbInfoModel> selectPage(Pagination pagination, DbInfoModel model, Wrapper<DbInfoModel> wrapper) {
        return dbInfoDao.selectPage(pagination, model, wrapper);
    }

}
