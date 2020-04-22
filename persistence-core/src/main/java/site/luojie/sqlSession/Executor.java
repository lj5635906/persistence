package site.luojie.sqlSession;

import site.luojie.Configuration;
import site.luojie.MappedStatement;

import java.util.List;

/**
 * @Description: 查询处理器
 * @Author jie.luo
 * @Create: 2020-04-22 22
 **/
public interface Executor {

    /**
     * 执行 SQL
     *
     * @param configuration   Configuration
     * @param mappedStatement MappedStatement
     * @param params          参数
     * @param <E>             返回类型
     * @return List<E>
     */
    <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception;

}
