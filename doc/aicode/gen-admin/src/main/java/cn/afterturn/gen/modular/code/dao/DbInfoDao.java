package cn.afterturn.gen.modular.code.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import cn.afterturn.gen.modular.code.model.DbInfoModel;

/**
 * DbInfoDao
 *
 * @author JueYue
 * @Date 2017-09-11 11:15
 */
@Repository
public interface DbInfoDao extends BaseMapper<DbInfoModel> {

    /**
     * 统计数量
     */
    Integer selectCount(@Param("e") DbInfoModel model);

    /**
     * 查询列表
     */
    List<DbInfoModel> selectList(@Param("e") DbInfoModel model, @Param("w") Wrapper<DbInfoModel> wrapper);

    /**
     * 分页查询信息
     */
    List<DbInfoModel> selectPage(@Param("p") Pagination pagination, @Param("e") DbInfoModel model, @Param("w") Wrapper<DbInfoModel> wrapper);

}
