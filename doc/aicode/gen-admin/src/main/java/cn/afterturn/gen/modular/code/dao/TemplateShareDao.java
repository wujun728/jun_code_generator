package cn.afterturn.gen.modular.code.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import cn.afterturn.gen.modular.code.model.TemplateShareModel;

/**
 * TemplateShareDao
 *
 * @author JueYue
 * @Date 2017-09-11 11:26
 */
@Repository
public interface TemplateShareDao extends BaseMapper<TemplateShareModel> {

    /**
     * 统计数量
     */
    Integer selectCount(@Param("e") TemplateShareModel model);

    /**
     * 查询列表
     */
    List<TemplateShareModel> selectList(@Param("e") TemplateShareModel model);

    /**
     * 分页查询信息
     */
    List<TemplateShareModel> selectPage(@Param("p") Pagination pagination, @Param("e") TemplateShareModel model, @Param("w") Wrapper<TemplateShareModel> wrapper);

}
