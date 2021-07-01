package com.chess06.chess06.dao;

import com.chess06.chess06.entity.Administrator;

import java.util.List;

public interface AdministratorDao {
    /**
     * add a new administrator
     * @param administrator's name and password
     * @return 1 success 0 fail
     */
    int insertAdministrator(Administrator administrator);


    /**
     * select administrator by id
     * @param id
     * @return administrator that administratorId equals id
     */
    Administrator queryAdministratorById(int id);

    /**
     * delete administrator by id
     * @param id
     * @return 1 success 0 fail
     */
    int deleteAdministrator(int id);

    List<Administrator> selectAllAdministrator();
}
