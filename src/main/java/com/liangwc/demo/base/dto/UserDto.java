package com.liangwc.demo.base.dto;

import com.liangwc.demo.domain.User;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liangweicheng
 * @date 2019/11/25 10:22
 */
@Data
public class UserDto extends User {
//    private List<Role> roles = new ArrayList<>();
    private String code;
}
