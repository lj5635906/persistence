package site.luojie.sqlSession;

import org.dom4j.DocumentException;
import site.luojie.Configuration;
import site.luojie.paser.XMLConfigBuilder;

import java.beans.PropertyVetoException;
import java.io.InputStream;

/**
 * @Description: SqlSessionFactory构建类
 * @Author jie.luo
 * @Create: 2020-04-22 21
 **/
public class SqlSessionFactoryBuilder {

    public SqlSessionFactory build(InputStream inputStream) throws DocumentException, PropertyVetoException {

        // 1.解析配置文件
        XMLConfigBuilder configBuilder = new XMLConfigBuilder();
        Configuration configuration = configBuilder.parseConfig(inputStream);

        // 2.创建SqlSessionFactory
        SqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);

        return sqlSessionFactory;
    }

}
