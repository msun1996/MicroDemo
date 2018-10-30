package com.micro.useredge.exception;

import com.micro.useredge.enums.ResultEnum;

/**
 * author: mSun
 * date: 2018/10/29
 */
public class RegisterException extends RuntimeException {

    private int code;

    public RegisterException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public RegisterException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }
}
