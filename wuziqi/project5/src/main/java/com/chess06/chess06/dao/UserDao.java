package com.chess06.chess06.dao;
import com.chess06.chess06.entity.User;

import java.util.List;

public interface UserDao {

    /**
     * add a new user
     * @param user include user's name and password
     * @return 1 success 0 fail
     */
    int insertUser(User user);

    /**
     * select the user by id
     * @param id
     * @return the user who userid equals id
     */
    User queryUserById(int id);

    /**
     * alter user's name and password by id
     * @param user the information that user want to alter
     * @return 1 success 0 fail
     */
    int updateUser(User user);

    /**
     * delete User by id
     * @param id
     * @return 1 success 0 fail
     */
    int deleteUser(int id);

    int addPoint(int id);

    /**
     * descending sort user by point
     * @return list<user>
     */
    List<User> descSortByPoint();
}
