package com.micro.useredge.enums;

import lombok.Getter;

/**
 * 用户相关结果
 * author: mSun
 * date: 2018/10/26
 */
@Getter
public enum  ResultEnum {
    PARAM_ERROR(1, "参数错误"),
    USER_INFO_FETCH_FAIL(2,"提取用户信息失败"),
    USER_NOT_EXIT(3,"用户不存在"),
    PASSWORD_ERROR(4, "用户密码错误"),
    MESSAGE_SEND_FAIL(5, "信息发送失败"),
    VERIFY_CODE_ERROR(6, "验证码错误"),
    OTHER_ERROR(1000, "其他错误"),
    ;

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
