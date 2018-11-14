package com.microdemo.useredgeserviceclient.filter;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.micro.thrift.user.dto.UserInfoDTO;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 登录拦截
 * author: mSun
 * date: 2018/10/30
 */
public abstract class LoginFilter implements Filter {

    // 真实公网ip
    private static final String publicIP = "116.196.86.59";

    // 用户服务内部地址
    private static final String userEdgeServiceAddr = "user-service:8081";

    // 使用guava定义缓存
    private static Cache<String, UserInfoDTO> cache = CacheBuilder.newBuilder()
            .maximumSize(10000)
            .expireAfterWrite(3, TimeUnit.MINUTES)
            .build();

    protected abstract void login(HttpServletRequest request, HttpServletResponse response, UserInfoDTO userInfoDTO);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 检测参数是否有token并获取
        String token = request.getParameter("token");
        // 如果参数没有，尝试去cookie获取
        if(StringUtils.isBlank(token)) {
            Cookie[] cookies = request.getCookies();
            if(cookies!=null) {
               for(Cookie cookie: cookies) {
                   if(cookie.getName().equals("token")) {
                       token = cookie.getValue();
                   }
               }
            }
        }
        // 如果不为空，根据token获取用户信息
        UserInfoDTO userInfoDTO = null;
        if(StringUtils.isNotBlank(token)) {
            // 本地缓存读取
            userInfoDTO = cache.getIfPresent(token);
            // 远程提取
            if (userInfoDTO == null) {
                RestTemplate restTemplate = new RestTemplate();
                String url = "http://"+ userEdgeServiceAddr +"/user/auth";
                HttpHeaders headers = new HttpHeaders();
                headers.add("token", token);
                userInfoDTO = restTemplate.postForObject(url,new HttpEntity<String>(headers), UserInfoDTO.class);
                if (userInfoDTO != null) {
                    cache.put(token,userInfoDTO);
                }
            }
        }
        if(userInfoDTO == null){
            // 具体时写最外层域名
            response.sendRedirect("http://" + publicIP + "/user/login");
            return ;
        }

        login(request,response,userInfoDTO);

        filterChain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
