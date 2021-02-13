package mao.bishe.api.service;

import mao.bishe.api.model.User;

public interface UserService {
    User findUserByStudentId(String id);
}
