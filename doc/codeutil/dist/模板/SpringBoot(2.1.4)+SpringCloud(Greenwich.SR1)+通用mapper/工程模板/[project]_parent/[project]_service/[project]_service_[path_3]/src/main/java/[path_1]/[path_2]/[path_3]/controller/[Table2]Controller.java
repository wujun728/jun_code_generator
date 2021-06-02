package [package].controller;
import [path_1].[path_2].entity.PageResult;
import [path_1].[path_2].entity.Result;
import [path_1].[path_2].entity.StatusCode;
import [package].service.[Table2]Service;
import [path_1].[path_2].pojo.[Table2];
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
@RestController
@CrossOrigin
@RequestMapping("/[table2]")
public class [Table2]Controller {


    @Autowired
    private [Table2]Service [table2]Service;

    /**
     * 查询全部数据
     * @return
     */
    @GetMapping
    public Result findAll(){
        List<[Table2]> [table2]List = [table2]Service.findAll();
        return new Result(true, StatusCode.OK,"查询成功",[table2]List) ;
    }

    /***
     * 根据ID查询数据
     * @param [key2]
     * @return
     */
    @GetMapping("/{[key2]}")
    public Result findById(@PathVariable [keyType] [key2]){
        [Table2] [table2] = [table2]Service.findById([key2]);
        return new Result(true,StatusCode.OK,"查询成功",[table2]);
    }


    /***
     * 新增数据
     * @param [table2]
     * @return
     */
    @PostMapping
    public Result add(@RequestBody [Table2] [table2]){
        [table2]Service.add([table2]);
        return new Result(true,StatusCode.OK,"添加成功");
    }


    /***
     * 修改数据
     * @param [table2]
     * @param [key2]
     * @return
     */
    @PutMapping(value="/{[key2]}")
    public Result update(@RequestBody [Table2] [table2],@PathVariable [keyType] [key2]){
        [table2].set[Key2]([key2]);
        [table2]Service.update([table2]);
        return new Result(true,StatusCode.OK,"修改成功");
    }


    /***
     * 根据ID删除品牌数据
     * @param [key2]
     * @return
     */
    @DeleteMapping(value = "/{[key2]}" )
    public Result delete(@PathVariable [keyType] [key2]){
        [table2]Service.delete([key2]);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /***
     * 多条件搜索品牌数据
     * @param searchMap
     * @return
     */
    @GetMapping(value = "/search" )
    public Result findList(@RequestParam Map searchMap){
        List<[Table2]> list = [table2]Service.findList(searchMap);
        return new Result(true,StatusCode.OK,"查询成功",list);
    }


    /***
     * 分页搜索实现
     * @param searchMap
     * @param page
     * @param size
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}" )
    public Result findPage(@RequestParam Map searchMap, @PathVariable  int page, @PathVariable  int size){
        Page<[Table2]> pageList = [table2]Service.findPage(searchMap, page, size);
        PageResult pageResult=new PageResult(pageList.getTotal(),pageList.getResult());
        return new Result(true,StatusCode.OK,"查询成功",pageResult);
    }


}
