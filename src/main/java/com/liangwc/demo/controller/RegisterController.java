package com.liangwc.demo.controller;

import com.liangwc.demo.base.lang.ViewConstants;
import com.liangwc.demo.domain.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author liangweicheng
 * @date 2019/11/21 14:08
 */
@Controller
public class RegisterController {
    @GetMapping("/register")
    public String goToRegister() {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        if (null != user) {
            return String.format(ViewConstants.REDIRECT_USER_HOME, user.getId());
        }
        return "/classic/auth/register";
    }
}
