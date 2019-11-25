package com.liangwc.demo.service.impl;

import com.liangwc.demo.base.dto.UserDto;
import com.liangwc.demo.base.lang.EntityStatus;
import com.liangwc.demo.base.lang.Result;
import com.liangwc.demo.base.util.MD5Utils;
import com.liangwc.demo.dao.UserMapper;
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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;

/**
 * @author liangweicheng
 * @date 2019/11/25 10:49
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional
    public Result register(UserDto userDto) {
        Result result = Result.failure();
        if (null == userDto.getUsername()) {
            result.setMessage("用户名不能为空!");
        }
        if (null == userDto.getPassword()) {
            result.setMessage("密码不能为空");
        }
        if (null == userDto.getEmail()) {
            result.setMessage("邮箱不能为空");
        }
        User checkUser = userMapper.selectByUserName(userDto.getUsername());
        if (checkUser != null) {
            result.setMessage("用户名已存在!");
        }
        User checkEmail = userMapper.selectByEmail(userDto.getEmail());
        if (checkEmail != null) {
            result.setMessage("邮箱已存在!");
        }
        User po = new User();

        BeanUtils.copyProperties(userDto, po);

        if (StringUtils.isBlank(po.getName())) {
            po.setName(userDto.getUsername());
        }
        Date now = Calendar.getInstance().getTime();
        po.setPassword(MD5Utils.md5(userDto.getPassword()));
        po.setStatus(EntityStatus.ENABLED);
        po.setCreated(now);
        userMapper.insert(po);
        result = Result.success("注册成功!", po);
        return result;
    }

    @Override
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
