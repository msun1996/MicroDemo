package com.microDemo.course.api.dto;

import com.micro.thrift.user.dto.TeacherDTO;
import lombok.Data;

import java.io.Serializable;

/**
 * author: mSun
 * date: 2018/10/30
 */
@Data
public class CourseDTO  implements Serializable {

    private int id;
    private String title;
    private String description;
    private TeacherDTO teacherDTO;

}
