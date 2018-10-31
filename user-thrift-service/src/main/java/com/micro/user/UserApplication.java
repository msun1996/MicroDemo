package com.micro.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * author: mSun
 * date: 2018/10/25
 */
@SpringBootApplication
public class UserApplication {
    public static void main(String[] args) {
//        SpringApplication.run(UserApplication.class,args);
        new SpringApplicationBuilder(UserApplication.class).web(WebApplicationType.NONE).run(args);
    }
}
