package mao.bishe.api.service.impl;

import mao.bishe.api.dao.UserDao;
import mao.bishe.api.model.User;
import mao.bishe.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Override
    public User findUserByStudentId(String id) {
        return userDao.findUserByStudentId(id);
    }
}
