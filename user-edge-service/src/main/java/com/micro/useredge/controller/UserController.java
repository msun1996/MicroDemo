package com.micro.useredge.controller;

import com.micro.thrift.user.UserInfo;
import com.micro.thrift.user.dto.UserInfoDTO;
import com.micro.useredge.VO.ResultVO;
import com.micro.useredge.enums.ResultEnum;
import com.micro.useredge.form.LoginForm;
import com.micro.useredge.form.RegisterForm;
import com.micro.useredge.thrift.ServiceProvider;
import com.micro.useredge.utils.KeysUtil;
import com.micro.useredge.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.concurrent.TimeUnit;

/**
 * author: mSun
 * date: 2018/10/26
 */
@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ServiceProvider serviceProvider;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 注册验证信息
     */
    @PostMapping("/sendVerifyCode")
    @ResponseBody
    public ResultVO sendVerifyCode(String mobile, String email) {
        if (StringUtils.isBlank(mobile) && StringUtils.isBlank(email)) {
            log.error("【验证码发送失败】邮箱或手机号不能全为空");
            return ResultVOUtil.error(ResultEnum.PARAM_ERROR,
                    "邮箱或手机号不能全为空");
        }
        // 验证码
        String code = KeysUtil.getRandomCode(6);
        try {
            boolean sendResult = false;
            if(StringUtils.isNotBlank(mobile)) {
                sendResult = serviceProvider.getMessageService().sendMobileMessage(mobile,code );
                // 代用redis存储验证码
                stringRedisTemplate.opsForValue().set(mobile, code, 3000, TimeUnit.SECONDS);
            } else if (StringUtils.isNotBlank(email)) {
                sendResult = serviceProvider.getMessageService().sendEmailMessage(email, code);
                // 代用redis存储验证码
                stringRedisTemplate.opsForValue().set(email, code, 3000, TimeUnit.SECONDS);
            }
            if (!sendResult) {
                return ResultVOUtil.error(ResultEnum.MESSAGE_SEND_FAIL);
            }
        } catch (TException e) {
            e.printStackTrace();
            log.error("【信息服务】调用失败");
            return ResultVOUtil.error(ResultEnum.OTHER_ERROR, e.getMessage());
        }
        return ResultVOUtil.success();
    }

    /**
     * 用户注册
     */
    @PostMapping("/register")
    @ResponseBody
    public ResultVO register(@RequestBody @Valid RegisterForm registerForm,
                             BindingResult bindingResult) {
        // 1.参数校验
        if (bindingResult.hasErrors()) {
            log.error("【注册失败】参数不正确，loginForm={}",registerForm);
            return ResultVOUtil.error(ResultEnum.PARAM_ERROR,
                    bindingResult.getFieldError().getDefaultMessage());
        }
        // 2.校验注册验证码
        String verifyCode = null;
        if (StringUtils.isNotBlank(registerForm.getEmail())) {
            verifyCode = stringRedisTemplate.opsForValue().get(registerForm.getEmail());
        } else if(StringUtils.isNotBlank(registerForm.getMobile())) {
            verifyCode = stringRedisTemplate.opsForValue().get(registerForm.getMobile());
        }
        if (verifyCode != null && !verifyCode.equals(registerForm.getVerifyCode())) {
            return ResultVOUtil.error(ResultEnum.VERIFY_CODE_ERROR);
        }
        // 3.用户注册
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(registerForm.getUsername());
        userInfo.setPassword(registerForm.getPassword());
        userInfo.setRealName(registerForm.getRealName());
        userInfo.setMobile(registerForm.getMobile());
        userInfo.setEmail(registerForm.getEmail());
        try {
            serviceProvider.getUserService().regiserUser(userInfo);
        } catch (TException e) {
            e.printStackTrace();
            log.error("【用户服务】调用失败");
            return ResultVOUtil.error(ResultEnum.OTHER_ERROR, e.getMessage());
        }
        return ResultVOUtil.success();
    }


    /**
     * 用户登录页面
     */
    @GetMapping("login")
    public String login() {
        return "login";
    }

    /**
     * 用户登录
     */
    @PostMapping("/login")
    @ResponseBody
    public ResultVO login(@Valid LoginForm loginForm,
                          BindingResult bindingResult) {

        // 1.参数校验
        if (bindingResult.hasErrors()) {
            log.error("【登录失败】 参数不正确，loginForm={}",loginForm);
            return ResultVOUtil.error(ResultEnum.PARAM_ERROR,
                    bindingResult.getFieldError().getDefaultMessage());
        }
        // 2.验证用户名和密码
        UserInfo userInfo = null;
        try {
            userInfo = serviceProvider.getUserService().getUserByName(loginForm.getUsername());
        } catch (TException e) {
            e.printStackTrace();
            log.error("【登录失败】用户信息数据提取失败");
            return ResultVOUtil.error(ResultEnum.USER_INFO_FETCH_FAIL);
        }
        if(userInfo == null) {
            return ResultVOUtil.error(ResultEnum.USER_NOT_EXIT);
        }
        if(!userInfo.getPassword().equals(loginForm.getPassword())) {
            return ResultVOUtil.error(ResultEnum.PASSWORD_ERROR);
        }
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        BeanUtils.copyProperties(userInfo, userInfoDTO);
        // 3.生成token
        String token = KeysUtil.getRandomCode(32);

        // 4.redis缓存用户
        redisTemplate.opsForValue().set(token, userInfoDTO, 3600, TimeUnit.SECONDS);
        return ResultVOUtil.success(token);
    }

    /**
     * 根据token获取用户信息
     */
    @PostMapping("/auth")
    @ResponseBody
    public UserInfoDTO authentication(@RequestHeader("token") String token) {
        return (UserInfoDTO) redisTemplate.opsForValue().get(token);
    }
}
