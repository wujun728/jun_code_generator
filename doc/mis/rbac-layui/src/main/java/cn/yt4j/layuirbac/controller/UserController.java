package cn.yt4j.layuirbac.controller;

import cn.yt4j.layuirbac.model.User;
import cn.yt4j.layuirbac.service.UserService;
import cn.yt4j.layuirbac.vo.LayuiResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author gyv12345@163.com
 */
@AllArgsConstructor
@RestController
@RequestMapping("user")
public class UserController {
    private final UserService userService;

    /**
     * 分页查询用户
     *
     * @param page
     * @param limit
     * @param user
     * @return
     */
    @GetMapping("list")
    public LayuiResponse list(Integer page, Integer limit, User user) {
        return LayuiResponse.ok(this.userService.list(page, limit, user));
    }

    /**
     * 配合前端form.value来使用
     *
     * @param id
     * @return
     */
    @GetMapping("get/{id}")
    public LayuiResponse get(@PathVariable Integer id) {
        return LayuiResponse.ok(this.userService.get(id));
    }

    /**
     * 添加
     * @param user
     * @return
     */
    @PostMapping("add")
    public LayuiResponse add(User user){
        return LayuiResponse.ok(this.userService.add(user));
    }

    /**
     * 修改
     * @param user
     * @return
     */
    @PostMapping("update")
    public LayuiResponse update(User user){
        return LayuiResponse.ok(this.userService.update(user));
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @PostMapping("delete/{id}")
    public LayuiResponse delete(@PathVariable Integer id){
        return LayuiResponse.ok(this.userService.delete(id));
    }
}
