package com.example.iminsik.findmyway2;

public class MyApp {
    static UserDao _userDao;

    public UserDao getDao()
    {
        return _userDao;
    }

    public void setDao(UserDao userDao) {
        if (_userDao != null) {
            _userDao = userDao;
        }
    }
}
