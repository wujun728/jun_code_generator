package [package].service;

import [path_1].[path_2].pojo.[Table2];
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface [Table2]Service {

    /***
     * 查询所有品牌
     * @return
     */
    List<[Table2]> findAll();

    /**
     * 根据ID查询
     * @param [key2]
     * @return
     */
    [Table2] findById([keyType] [key2]);

    /***
     * 新增品牌
     * @param [table2]
     */
    void add([Table2] [table2]);

    /***
     * 修改品牌数据
     * @param [table2]
     */
    void update([Table2] [table2]);

    /***
     * 删除品牌
     * @param id
     */
    void delete([keyType] [key2]);

    /***
     * 多条件搜索品牌方法
     * @param searchMap
     * @return
     */
    List<[Table2]> findList(Map<String, Object> searchMap);

    /***
     * 分页查询
     * @param page
     * @param size
     * @return
     */
    Page<[Table2]> findPage(int page, int size);

    /***
     * 多条件分页查询
     * @param searchMap
     * @param page
     * @param size
     * @return
     */
    Page<[Table2]> findPage(Map<String, Object> searchMap, int page, int size);




}
