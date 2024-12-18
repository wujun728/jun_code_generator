package com.hp.test1;
import cn.hutool.core.bean.BeanUtil;
import com.google.common.collect.Maps;
import io.github.wujun728.common.base.Result;
import io.github.wujun728.db.record.Db;
import io.github.wujun728.db.record.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
/**
 * @description 客户信息
 * @author wujun
 * @date 2024-12-18
 */
@RestController
@RequestMapping("/bizTest")
public class BizTestController {

    /**
    * 新增或编辑
    */
    @RequestMapping("/save")
    public Object save(BizTest bizTest){
        BizTest bizTestOne= Db.findBeanById(BizTest.class,bizTest.getId());
        if(bizTestOne!=null){
            Db.updateBean(bizTest);
            return ("编辑成功");
        }else{
            Db.saveBean(bizTest);
            return ("保存成功");
        }
    }

    /**
    * 删除
    */
    @RequestMapping("/delete")
    public Object delete(int id){
        BizTest bizTestOne= Db.findBeanById(BizTest.class,id);
        if(bizTestOne!=null){
            Db.deleteById("biz_test",id);
            return ("删除成功");
        }else{
            return ("没有找到该对象");
        }
    }

    /**
    * 查询
    */
    @RequestMapping("/find/{id}")
    public Object find(@PathVariable(value = "id") int id){
        BizTest bizTestOne= Db.findBeanById(BizTest.class,id);
        if(bizTestOne!=null){
            return (bizTestOne);
        }else{
            return Result.error("没有找到该对象");
        }
    }

    /**
    * 分页查询
    */
    @RequestMapping("/list")
    public Object list(BizTest bizTest) {
        List<BizTest> datas = Db.findBeanList(BizTest.class,"select * from biz_test");
        return datas;
    }

    /**
     * 分页查询
     */
    @RequestMapping("/page")
    public Object page(BizTest bizTest,
                       @RequestParam(required = false, defaultValue = "0") int pageNumber,
                       @RequestParam(required = false, defaultValue = "10") int pageSize) {
        Map params = BeanUtil.beanToMap(bizTest,true,true);
        Page<BizTest> page= Db.findBeanPages(BizTest.class,pageNumber,pageSize, params);
        return (page);
    }


}

