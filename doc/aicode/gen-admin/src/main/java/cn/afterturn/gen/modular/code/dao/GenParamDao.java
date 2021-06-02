package cn.afterturn.gen.modular.code.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import cn.afterturn.gen.modular.code.model.GenParamModel;

/**
 * GenParamDao
 *
 * @author JueYue
 * @Date 2017-09-13 09:12
 */
@Repository
public interface GenParamDao extends BaseMapper<GenParamModel>{

    /**
     * 统计数量
     * @param model
     * @return
     */
    Integer selectCount(@Param("e") GenParamModel model);

    /**
     * 查询列表
     * @param model
     * @return
     */
    List<GenParamModel> selectList(@Param("e") GenParamModel model);

    /**
     * 分页查询信息
     * @param pagination
     * @param model
     * @param wrapper
     * @return
     */
    List<GenParamModel> selectPage(@Param("p") Pagination pagination, @Param("e") GenParamModel model, @Param("w") Wrapper<GenParamModel> wrapper);

}
