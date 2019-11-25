package com.liangwc.demo.service;

import com.liangwc.demo.base.dto.UserDto;

public interface SecurityService {
    boolean verifyEmailCode(UserDto userDto);
}
