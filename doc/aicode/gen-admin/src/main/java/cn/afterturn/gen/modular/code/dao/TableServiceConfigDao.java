package cn.afterturn.gen.modular.code.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import cn.afterturn.gen.modular.code.model.TableServiceConfigModel;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * TableServiceConfigDao
 *
 * @author JueYue
 * @Date 2017-09-20 09:21
 */
@Repository
public interface TableServiceConfigDao extends BaseMapper<TableServiceConfigModel>{

    /**
     * 统计数量
     * @param model
     * @return
     */
    Integer selectCount(@Param("e")TableServiceConfigModel model);

    /**
     * 查询列表
     * @param model
     * @return
     */
    List<TableServiceConfigModel> selectList(@Param("e")TableServiceConfigModel model);

    /**
     * 分页查询信息
     * @param pagination
     * @param model
     * @param wrapper
     * @return
     */
    List<TableServiceConfigModel> selectPage(@Param("p")Pagination pagination,@Param("e") TableServiceConfigModel model,@Param("w") Wrapper<TableServiceConfigModel> wrapper);

    /**
     * 批量插入
     * @param serviceConfig
     */
    void batchInsert(List<TableServiceConfigModel> serviceConfig);

}
