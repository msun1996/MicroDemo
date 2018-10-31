package com.micro.thrift.user.dto;

import lombok.Data;

/**
 * author: mSun
 * date: 2018/10/30
 */
@Data
public class TeacherDTO extends UserInfoDTO {
    private String intro;
    private int stars;
}
