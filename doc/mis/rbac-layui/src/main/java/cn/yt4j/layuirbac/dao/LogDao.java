package cn.yt4j.layuirbac.dao;

import cn.yt4j.layuirbac.model.Log;

import java.util.List;

/**
 * (Log)表数据库访问层
 *
 * @author gyv12345@163.com
 */
public interface LogDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Log getById(Integer id);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param log 实例对象
     * @return 对象列表
     */
    List<Log> list(Log log);

    /**
     * 新增数据
     *
     * @param log 实例对象
     * @return 影响行数
     */
    int insert(Log log);

    /**
     * 修改数据
     *
     * @param log 实例对象
     * @return 影响行数
     */
    int update(Log log);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}