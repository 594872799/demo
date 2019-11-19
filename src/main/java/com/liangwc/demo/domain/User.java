package com.liangwc.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author liangweicheng
 * @date 2019/11/19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;

    private String username;

    private String name;

    private String avatar;

    private String email;

    private String password;

    private Integer status;

    private Date created;

    private Date updated;

    private Date lastLogin;

    private Integer gender;

    private Integer roleId;

    private Integer comments;

    private Integer posts;

    private String signature;
}