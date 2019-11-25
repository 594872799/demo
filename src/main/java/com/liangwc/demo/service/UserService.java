package com.liangwc.demo.service;

import com.liangwc.demo.base.dto.UserDto;
import com.liangwc.demo.base.lang.Result;
import com.liangwc.demo.domain.User;

/**
 * @author liangweicheng
 * @date 2019/11/25 10:49
 */
public interface UserService {
    /**
     * 注册
     *
     * @param userDto
     * @return
     */
    Result register(UserDto userDto);

    /**
     * 执行登录
     *
     * @param username
     * @param password
     * @param rememberMe
     * @return
     */
    Result<User> executeLogin(String username, String password, boolean rememberMe);
}
