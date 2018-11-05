package com.microDemo.course.edge.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.microDemo.course.api.dto.CourseDTO;
import com.microDemo.course.api.service.ICourseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * author: mSun
 * date: 2018/10/30
 */
@RestController
@RequestMapping("/course")
public class CourseController {

    @Reference(
            version = "${course.service.version}",
            application = "${dubbo.application.id}",
            url = "dubbo://course-dubbo-service:12345"
    )
    private ICourseService courseService;

    @GetMapping(value = "/courseList")
    public List<CourseDTO> courseDTOList() {
        return courseService.courseList();
    }

}
