package com.micro.user.dao;

import com.micro.thrift.user.UserInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * 用户信息操作
 * author: mSun
 * date: 2018/10/25
 */
@Mapper
@Repository
public interface UserDao {

    @Select("select id,username,password,real_name as realName," +
            "mobile,email from pe_user where id=#{id}")
    UserInfo getUserById(@Param("id") Integer id);

    @Select("select u.id,u.username,u.password,u.real_name as realName," +
            "u.mobile,u.email,t.intro,t.stars from pe_user as u, " +
            "pe_teacher as t where u.id=#{id} and u.id=t.user_id")
    UserInfo getTeacherById(@Param("id") Integer id);

    @Select("select id,username,password,real_name as realName," +
            "mobile,email from pe_user where username=#{username}")
    UserInfo getUserByUserName(@Param("username") String username);

    @Insert("insert into pe_user (username,password,real_name,mobile, email)" +
            "values (#{u.username},#{u.password},#{u.realName},#{u.mobile},#{u.email})")
    void rehisterUser(@Param("u") UserInfo userInfo);

}
