package com.liangwc.demo.controller;

import com.liangwc.demo.base.dto.UserDto;
import com.liangwc.demo.base.lang.Constants;
import com.liangwc.demo.base.lang.Result;
import com.liangwc.demo.base.lang.ViewConstants;
import com.liangwc.demo.domain.User;
import com.liangwc.demo.service.SecurityService;
import com.liangwc.demo.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author liangweicheng
 * @date 2019/11/21 14:08
 */
@Controller
public class RegisterController {
    @Autowired
    private SecurityService securityService;
    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String goToRegister() {
//        Subject subject = SecurityUtils.getSubject();
//        User user = (User) subject.getPrincipal();
//        if (null != user) {
//            return String.format(ViewConstants.REDIRECT_USER_HOME, user.getId());
//        }
        return "/classic/auth/register";
    }

    @PostMapping("/register")
    public String register(UserDto userDto, HttpServletRequest request, ModelMap model) {
        String view = "/classic/auth/register";
        if (!securityService.verifyEmailCode(userDto)) {
            throw new IllegalStateException("验证码验证失败,请重新输入");
        }
        userDto.setAvatar(Constants.AVATAR);
        Result registerResult = userService.register(userDto);
        if (registerResult.isOk()) {
            User user = (User) registerResult.getData();
            Result<User> loginResult = userService.executeLogin(user.getUsername(), user.getPassword(), false);
            if (loginResult.isOk()) {
                view = String.format(ViewConstants.REDIRECT_USER_HOME, loginResult.getData().getId());
            } else {
                model.put("data", loginResult.getMessage());
            }
        }
        return view;
    }
}
