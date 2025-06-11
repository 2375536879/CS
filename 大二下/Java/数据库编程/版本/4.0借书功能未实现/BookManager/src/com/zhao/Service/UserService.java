package com.zhao.Service;

import com.zhao.dao.UserDao;
import com.zhao.po.User;

public class UserService {
    private UserDao userDao;

    public UserService() {
        this.userDao = new UserDao();
    }

    public int login(User user) {
        User foundUser = userDao.login(user.getUserName(), user.getPassword());
        if (foundUser != null) {
            return foundUser.getId();
        }
        return -1;
    }

    public boolean register(User user) {
        // TODO: 实现注册功能
        return false;
    }
}
