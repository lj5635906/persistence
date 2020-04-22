package site.luojie;

import site.luojie.dao.IUserDao;
import site.luojie.io.Resources;
import site.luojie.pojo.User;
import site.luojie.sqlSession.SqlSession;
import site.luojie.sqlSession.SqlSessionFactory;
import site.luojie.sqlSession.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.List;

/**
 * @Description: 测试类
 * @Author jie.luo
 * @Create: 2020-04-22 21
 **/
public class Main {

    public static void main(String[] args) throws Exception {
        InputStream inputStream = Resources.getResourceAsStream("SqlMapConfig.xml");

        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        SqlSession sqlSession = sqlSessionFactory.openSession();

        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        List<User> users = userDao.findAll();
        users.forEach(user1 -> System.out.println(user1));

        System.out.println();

        User user = userDao.findUserByCondition(new User() {{
            this.setUSER_ID(1);
            this.setUSER_NAME("HBase");
        }});
        System.out.println(user);

    }
}
