package com.micrDemo.course.provider;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * author: mSun
 * date: 2018/10/30
 */
@SpringBootApplication
public class CourseServiceApplication {
    public static void main(String[] args) {
//        SpringApplication.run(CourseServiceApplication.class,args);
        new SpringApplicationBuilder(CourseServiceApplication.class)
                .web(WebApplicationType.NONE) // 非Web应用
                .run(args);
    }
}
