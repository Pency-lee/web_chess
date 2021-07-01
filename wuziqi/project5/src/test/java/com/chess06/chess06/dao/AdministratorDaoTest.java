package com.chess06.chess06.dao;

import com.chess06.chess06.entity.Administrator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class AdministratorDaoTest {
    @Autowired
    private AdministratorDao administratorDao;
    private Administrator administrator =new Administrator();

    @Test
    public void insertAdministrator() {
        administrator.setName("jun");
        administrator.setPassword("666666");
        administratorDao.insertAdministrator(administrator);
    }

    @Test
    public void queryAdministratorById() {
        administrator.setName("yuan");
        administrator.setPassword("88888888");
        administratorDao.insertAdministrator(administrator);
        System.out.println(administrator.getId());
        System.out.println(administratorDao.queryAdministratorById(administrator.getId()).getName()+"："+administratorDao.queryAdministratorById(administrator.getId()).getPassword());
    }

    @Test
    public void deleteAdministrator() {
        administrator.setId(2);
        administratorDao.deleteAdministrator(administrator.getId());
    }

    @Test
    public void test02(){
        System.out.println(administratorDao.selectAllAdministrator().get(0).getId());
    }
}