package mao.bishe.api.service;

import mao.bishe.api.model.User;
import mao.bishe.api.model.form.UpdatePwd;
import mao.bishe.api.model.query.UserQuery;

public interface UserService {
    //个人信息页面
    User findUserByStudentId(String id);
    UserQuery findUser(String student_id);
    void update(UpdatePwd updatePwd);
    void setPicture(byte[] data,String id);
    void save(User user);
}
