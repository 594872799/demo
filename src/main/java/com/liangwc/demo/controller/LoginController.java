package com.liangwc.demo.controller;

import com.liangwc.demo.base.lang.Result;
import com.liangwc.demo.base.lang.ViewConstants;
import com.liangwc.demo.base.util.MD5Utils;
import com.liangwc.demo.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
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

    /**
     * 跳转登录页
     *
     * @return
     */
    @GetMapping(value = "/login")
    public String view() {
        return "/classic/auth/login";
    }

    @PostMapping(value = "/login")
    public String login(String username, String password, @RequestParam(value = "remeberMe", defaultValue = "0") Boolean rememberMe, ModelMap model) {
        String view = "/classic/auth/login";

        Result<User> result = executeLogin(username, password, rememberMe);
        if (result.isOk()) {
            view = String.format(ViewConstants.REDIRECT_USER_HOME, result.getData().getId());
        } else {
            model.put("message", result.getMessage());
        }
        return view;
    }

    public Result<User> executeLogin(String username, String password, boolean rememberMe) {
        Result<User> result = Result.failure("登录失败");
        if (StringUtils.isAnyBlank(username, password)) {
            return result;
        }
        UsernamePasswordToken token = new UsernamePasswordToken(username, MD5Utils.md5(password), rememberMe);
        try {
            SecurityUtils.getSubject().login(token);
            result = Result.success(getProfile());
        } catch (UnknownAccountException e) {
            log.error(e.getMessage());
            result = Result.failure("用户不存在");
        } catch (LockedAccountException e) {
            log.error(e.getMessage());
            result = Result.failure("用户被禁用");
        } catch (AuthenticationException e) {
            log.error(e.getMessage());
            result = Result.failure("用户认证失败");
        }
        return result;
    }

    public User getProfile() {
        Subject subject = SecurityUtils.getSubject();
        return (User) subject.getPrincipal();
    }
}
