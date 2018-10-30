package com.micro.thrift.user.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * author: mSun
 * date: 2018/10/26
 */
@Data
public class UserInfoDTO implements Serializable {

    private int id;
    private String username;
    private String password;
    private String realName;
    private String mobile;
    private String email;
}
