package site.luojie.sqlSession;

/**
 * @Description:
 * @Author jie.luo
 * @Create: 2020-04-22 21
 **/
public interface SqlSessionFactory {

    /**
     * 获取 SQLSession
     *
     * @return SQLSession
     */
    SqlSession openSession();

}
