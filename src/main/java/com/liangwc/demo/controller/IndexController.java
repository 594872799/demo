package com.liangwc.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author by liangweicheng 2019/11/18
 */
@Controller
public class IndexController {
    @RequestMapping("/index")
    @ResponseBody
    public String index() {
        return "success";
    }
}
