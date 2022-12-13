package com.jeasy.base.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
@Slf4j
@Controller
public class IndexController {

    @RequestMapping(value = "/")
    public String index() {
        return "index";
    }
}
