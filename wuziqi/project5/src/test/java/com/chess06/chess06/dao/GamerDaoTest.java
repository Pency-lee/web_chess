package com.chess06.chess06.dao;

import com.chess06.chess06.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class GamerDaoTest {
    @Autowired
    private UserDao userDao;
    User user =new User();


    @Test
    void testUserDao(){
        user.setName("li");
        user.setPassword("li123");
        // 测试insertUser方法，同时会将数据库中自增的id带入出来
        userDao.insertUser(user);
        //测试queryUserById方法，利用insert User方法中带出的id查询对应的用户
        System.out.println(userDao.queryUserById(user.getId()).getId());
    }

    @Test
    void testUserDaoUpdatePassword(){
        user.setId(2);
        user.setPassword("123Li");
        System.out.println(userDao.queryUserById(user.getId()).getPassword());
        //只改密码
        userDao.updateUser(user);
        System.out.println(userDao.queryUserById(user.getId()).getPassword());
    }

    @Test
    void testUserDaoUpdateName(){
        user.setId(18);
        user.setName("Wan na");
        user.setPassword("123456");
        //只改名字
        userDao.updateUser(user);
    }

    @Test
    void testUserDaoUpdatePoint(){
        user.setId(2);
        //只改分数
        userDao.updateUser(user);
        System.out.println(userDao.queryUserById(user.getId()).getPoint());
    }
}
