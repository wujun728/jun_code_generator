package com.jeasy.base.controller;

import com.jeasy.common.ueditor.UeditorService;
import com.jeasy.common.web.ResponseKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 通用的控制器
 *
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
@Controller
public class CommonsController {

    @Autowired
    private UeditorService ueditorService;

    /**
     * 图标页
     */
    @RequestMapping("icons.html")
    public String icons() {
        return "icons";
    }

    /**
     * ueditor编辑器
     */
    @RequestMapping("ueditor")
    public void ueditor(final HttpServletRequest request, final HttpServletResponse response) {
        response.setContentType("text/html");
        ResponseKit.renderJson(response, ueditorService.exec(request));
    }
}
