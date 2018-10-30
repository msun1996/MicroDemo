package com.micro.useredge.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * author: mSun
 * date: 2018/10/29
 */
@Data
public class RegisterForm {

    @NotEmpty(message="用户名不能为空")
    private String username;

    @NotEmpty(message = "密码不能为空")
    private String password;

    private String realName;

    private String mobile;

    private String email;

    @NotEmpty(message="验证码不能为空")
    private String verifyCode;


}
