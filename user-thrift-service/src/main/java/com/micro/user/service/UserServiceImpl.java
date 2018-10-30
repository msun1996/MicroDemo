package com.micro.user.service;

import com.micro.thrift.user.UserInfo;
import com.micro.thrift.user.UserService;
import com.micro.user.dao.UserDao;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author: mSun
 * date: 2018/10/25
 */
@Service
public class UserServiceImpl implements UserService.Iface {

    @Autowired
    private UserDao userDao;

    @Override
    public UserInfo getUserById(int id) throws TException {
        return userDao.getUserById(id);
    }

    @Override
    public UserInfo getUserByName(String username) throws TException {
        return userDao.getUserByUserName(username);
    }

    @Override
    public void regiserUser(UserInfo userInfo) throws TException {
        userDao.rehisterUser(userInfo);
    }
}
