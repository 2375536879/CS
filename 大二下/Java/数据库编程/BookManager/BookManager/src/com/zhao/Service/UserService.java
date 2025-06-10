package com.zhao.Service;

import com.zhao.dao.UserDao;
import com.zhao.po.User;

public class UserService {

    public boolean login(User user) {
        UserDao userDao = new UserDao();
        if (userDao.login(user.getUserName(), user.getPassword()) != null) {//查到了

            return true;
        }
        return false;
    }
}
