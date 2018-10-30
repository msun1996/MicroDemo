package com.micro.useredge.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 用户登录表单
 * author: mSun
 * date: 2018/10/26
 */
@Data
public class LoginForm {

    @NotEmpty(message = "用户名必填")
    private String username;

    @NotEmpty(message = "密码必填")
    private String password;

}
