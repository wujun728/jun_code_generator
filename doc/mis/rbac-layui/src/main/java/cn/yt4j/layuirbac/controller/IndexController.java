package cn.yt4j.layuirbac.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author gyv12345@163.com
 */
@Controller
public class IndexController {
    /**
     * 首页
     * @return
     */
    @RequestMapping(value = "/")
    public String index(){
        return "index";
    }

    @RequestMapping("login.html")
    public String login(){
        return "login";
    }

    /**
     * 路由跳转
     * @param path
     * @return
     */
    @GetMapping(value = "to")
    public String to(String path, Integer id, Model model){
        model.addAttribute("id",id);
        return path;
    }
}
