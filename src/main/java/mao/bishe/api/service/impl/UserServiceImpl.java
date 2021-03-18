package mao.bishe.api.service.impl;

import mao.bishe.api.dao.UserDao;
import mao.bishe.api.model.User;
import mao.bishe.api.model.form.UpdatePwd;
import mao.bishe.api.model.query.UserQuery;
import mao.bishe.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public User findUserByStudentId(String id) {
        return userDao.findUserByStudentId(id);
    }

    @Override
    public UserQuery findUser(String student_id) {
        String sql = "select * from student1 where student_id = '"+student_id+"';";
        UserQuery query = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<UserQuery>(UserQuery.class));
        return query;
    }

    @Override
    public void update(UpdatePwd updatePwd) {
        String sql = "update test_student set password = '"+updatePwd.getNewPass()+"' where student_id = '"+updatePwd.getStudentId()+"';";
        int update = jdbcTemplate.update(sql);
    }

    @Override
    public void setPicture(byte[] data, String id) {
        String sql = "update test_student set pciture = "+data+" where student_id = '"+id+"';";
        jdbcTemplate.update(sql);
        System.out.println(11);
    }

    @Override
    public void save(User user) {
        userDao.save(user);
    }
}
