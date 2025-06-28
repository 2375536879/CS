package com.szz.service;

import com.szz.dao.UserDao;
import com.szz.model.User;

public class UserService {
    private UserDao userDao;

    public UserService() {
        this.userDao = new UserDao();
    }

    public int login(User user) {
        User foundUser = userDao.login(user.getUsername(), user.getPwd());
        if (foundUser != null) {
            return foundUser.getId();
        }
        return -1;
    }

    public boolean register(User user) {
        // TODO: 实现注册功能
       if(userDao.register(user)) {
           return true;
       }
        return false;
    }
}
