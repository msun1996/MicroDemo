
package com.microDemo.course.edge.filter;

import com.micro.thrift.user.dto.UserInfoDTO;
import com.microdemo.useredgeserviceclient.filter.LoginFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * author: mSun
 * date: 2018/10/31
 */
@Component
public class CourseFilter extends LoginFilter {

    @Override
    protected void login(HttpServletRequest request, HttpServletResponse response, UserInfoDTO userInfoDTO) {
        request.setAttribute("user", userInfoDTO);
    }
}
