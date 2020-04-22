package site.luojie.sqlSession;

import java.util.List;

/**
 * @Description:
 * @Author jie.luo
 * @Create: 2020-04-22 22
 **/
public interface SqlSession {

    /**
     * 获取单个数据
     *
     * @param <E>    数据类型
     * @param params 参数
     * @return E
     */
    <E> E selectOne(String statementId, Object... params) throws Exception;

    /**
     * 获取多行数据
     *
     * @param <E>    数据类型
     * @param params 参数
     * @return List<E>
     */
    <E> List<E> selectList(String statementId, Object... params) throws Exception;

    /**
     * 为Dao接口生成代理实现类
     *
     * @param mapperClass
     * @param <E>
     * @return
     */
    <E> E getMapper(Class<?> mapperClass);
}
