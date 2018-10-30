package com.micro.useredge.exception;

import com.micro.useredge.enums.ResultEnum;

/**
 * 登录异常抛错
 * author: mSun
 * date: 2018/10/26
 */
public class LoginException extends RuntimeException {

    private Integer code;

    public LoginException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public LoginException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }
}
