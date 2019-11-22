package com.liangwc.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author by liangweicheng 2019/11/18
 */
@Controller
public class IndexController {
    @RequestMapping(value= {"/", "/index"})
    public String index(ModelMap model, HttpServletRequest request) {
        String order = ServletRequestUtils.getStringParameter(request, "order", "newest");
        int pageNo = ServletRequestUtils.getIntParameter(request, "pageNo", 1);
        model.put("order", order);
        model.put("pageNo", pageNo);
        return "/classic/index";
    }
}
