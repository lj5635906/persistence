package site.luojie.dao;

import site.luojie.pojo.User;

import java.util.List;

/**
 * @Description: 用户DAO
 * @Author jie.luo
 * @Create: 2020-04-22 23
 **/
public interface IUserDao {
    /**
     * 查询所有用户
     *
     * @return User
     */
    List<User> findAll();

    /**
     * 根据条件查询用户
     *
     * @param user 参数
     * @return User
     */
    User findUserByCondition(User user) throws Exception;
}
