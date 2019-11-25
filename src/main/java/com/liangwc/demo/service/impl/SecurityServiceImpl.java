package com.liangwc.demo.service.impl;

import com.liangwc.demo.base.dto.UserDto;
import com.liangwc.demo.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author liangweicheng
 * @date 2019/11/25 10:32
 */
@Service
public class SecurityServiceImpl implements SecurityService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean verifyEmailCode(UserDto userDto) {
        String code = userDto.getCode();
        String email = userDto.getEmail();
        if (null == code) {
            return false;
        }
        if (null == email) {
            return false;
        }
        String redisCode = redisTemplate.opsForValue().get(email);
        if (code.equalsIgnoreCase(redisCode)) {
            redisTemplate.delete(email);
            return true;
        }
        return false;
    }
}
