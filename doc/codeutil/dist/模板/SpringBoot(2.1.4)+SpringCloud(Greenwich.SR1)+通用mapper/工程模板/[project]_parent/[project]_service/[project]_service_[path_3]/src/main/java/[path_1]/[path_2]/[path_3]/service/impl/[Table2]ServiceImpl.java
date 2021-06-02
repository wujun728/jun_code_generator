package [package].service.impl;

import [package].dao.[Table2]Mapper;
import [package].service.[Table2]Service;
import [path_1].[path_2].pojo.[Table2];
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

@Service
public class [Table2]ServiceImpl implements [Table2]Service {

    @Autowired
    private [Table2]Mapper [table2]Mapper;

    /**
     * 查询全部列表
     * @return
     */
    @Override
    public List<[Table2]> findAll() {
        return [table2]Mapper.selectAll();
    }

    /**
     * 根据ID查询
     * @param [key2]
     * @return
     */
    @Override
    public [Table2] findById([keyType] [key2]){
        return  [table2]Mapper.selectByPrimaryKey([key2]);
    }


    /**
     * 增加
     * @param [table2]
     */
    @Override
    public void add([Table2] [table2]){
        [table2]Mapper.insert([table2]);
    }


    /**
     * 修改
     * @param [table2]
     */
    @Override
    public void update([Table2] [table2]){
        [table2]Mapper.updateByPrimaryKey([table2]);
    }

    /**
     * 删除
     * @param [key2]
     */
    @Override
    public void delete([keyType] [key2]){
        [table2]Mapper.deleteByPrimaryKey([key2]);
    }


    /**
     * 条件查询
     * @param searchMap
     * @return
     */
    @Override
    public List<[Table2]> findList(Map<String, Object> searchMap){
        Example example = createExample(searchMap);
        return [table2]Mapper.selectByExample(example);
    }

    /**
     * 分页查询
     * @param page
     * @param size
     * @return
     */
    @Override
    public Page<[Table2]> findPage(int page, int size){
        PageHelper.startPage(page,size);
        return (Page<[Table2]>)[table2]Mapper.selectAll();
    }

    /**
     * 条件+分页查询
     * @param searchMap 查询条件
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @Override
    public Page<[Table2]> findPage(Map<String,Object> searchMap, int page, int size){
        PageHelper.startPage(page,size);
        Example example = createExample(searchMap);
        return (Page<[Table2]>)[table2]Mapper.selectByExample(example);
    }

    /**
     * 构建查询对象
     * @param searchMap
     * @return
     */
    private Example createExample(Map<String, Object> searchMap){
        Example example=new Example([Table2].class);
        Example.Criteria criteria = example.createCriteria();
        if(searchMap!=null){
<条件查询.String.txt>
<条件查询.Integer.txt>
        }
        return example;
    }

}
