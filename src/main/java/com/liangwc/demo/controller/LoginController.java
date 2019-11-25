package com.liangwc.demo.controller;

import com.liangwc.demo.base.lang.Result;
import com.liangwc.demo.base.lang.ViewConstants;
import com.liangwc.demo.base.util.MD5Utils;
import com.liangwc.demo.domain.User;
import com.liangwc.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author liangweicheng
 * @date 2019/11/19 12:21
 */
@Slf4j
@Controller
public class LoginController {
    @Autowired
    private UserService userService;
    /**
     * 跳转登录页
     *
     * @return view
     */
    @GetMapping(value = "/login")
    public String view() {
        return "/classic/auth/login";
    }

    @PostMapping(value = "/login")
    public String login(String username, String password, @RequestParam(value = "remeberMe", defaultValue = "0") Boolean rememberMe, ModelMap model) {
        String view = "/classic/auth/login";

        Result<User> result = userService.executeLogin(username, password, rememberMe);
        if (result.isOk()) {
            view = String.format(ViewConstants.REDIRECT_USER_HOME, result.getData().getId());
        } else {
            model.put("message", result.getMessage());
        }
        return view;
    }
}
