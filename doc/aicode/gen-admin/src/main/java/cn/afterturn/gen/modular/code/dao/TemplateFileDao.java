package cn.afterturn.gen.modular.code.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import cn.afterturn.gen.modular.code.model.TemplateFileModel;

/**
 * TemplateFileDao
 *
 * @author JueYue
 * @Date 2017-09-13 11:26
 */
@Repository
public interface TemplateFileDao extends BaseMapper<TemplateFileModel> {

    /**
     * 统计数量
     */
    Integer selectCount(@Param("e") TemplateFileModel model);

    /**
     * 查询列表
     */
    List<TemplateFileModel> selectList(@Param("e") TemplateFileModel model);

    /**
     * 分页查询信息
     */
    List<TemplateFileModel> selectPage(@Param("p") Pagination pagination, @Param("e") TemplateFileModel model, @Param("w") Wrapper<TemplateFileModel> wrapper);

    /**
     * 删除
     */
    Integer deleteByTemplateId(Integer id);

    /**
     * 更新文件
     */
    Integer updateTemplateId(@Param("e") TemplateFileModel fileModel);
}
