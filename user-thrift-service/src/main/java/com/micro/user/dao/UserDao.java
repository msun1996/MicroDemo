package com.micro.user.dao;

import com.micro.thrift.user.UserInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * author: mSun
 * date: 2018/10/25
 */
@Mapper
@Repository
public interface UserDao {

    @Select("select id,username,password,real_name as realName," +
            "mobile,email from pe_user where id=#{id}")
    UserInfo getUserById(@Param("id") Integer id);

    @Select("select id,username,password,real_name as realName," +
            "mobile,email from pe_user where username=#{username}")
    UserInfo getUserByUserName(@Param("username") String username);

    @Insert("insert into pe_user (username,password,real_name,mobile, email)" +
            "values (#{u.username},#{u.password},#{u.realName},#{u.mobile},#{u.email})")
    void rehisterUser(@Param("u") UserInfo userInfo);

}
