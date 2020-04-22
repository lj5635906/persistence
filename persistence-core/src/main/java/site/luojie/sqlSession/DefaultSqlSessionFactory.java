package site.luojie.sqlSession;

import site.luojie.Configuration;

/**
 * @Description:
 * @Author jie.luo
 * @Create: 2020-04-22 22
 **/
public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(configuration);
    }
}
