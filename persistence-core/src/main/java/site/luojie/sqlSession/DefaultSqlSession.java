package site.luojie.sqlSession;

import site.luojie.Configuration;

import java.lang.reflect.*;
import java.util.List;

/**
 * @Description:
 * @Author jie.luo
 * @Create: 2020-04-22 22
 **/
public class DefaultSqlSession implements SqlSession {

    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <E> E selectOne(String statementId, Object... params) throws Exception {
        List<Object> objects = selectList(statementId, params);
        if (null == objects || objects.size() != 1) {
            throw new RuntimeException("查询结果集为空或返回结果集过多");
        }
        return (E) objects.get(0);
    }

    @Override
    public <E> List<E> selectList(String statementId, Object... params) throws Exception {
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        List<Object> list = simpleExecutor.query(configuration, configuration.getMappedStatementMap().get(statementId), params);
        return (List<E>) list;
    }

    @Override
    public <E> E getMapper(Class<?> mapperClass) {

        // 使用JDK动态代理来为DAO接口生成代理对象，并返回

        Object o = Proxy.newProxyInstance(DefaultSqlSession.class.getClassLoader(), new Class[]{mapperClass}, new InvocationHandler() {
            /**
             *
             * @param proxy 当前代理对象的引用
             * @param method 当前调用方法的引用
             * @param args 传递的参数
             * @return
             * @throws Throwable
             */
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                // 调用方法名
                String methodName = method.getName();
                // 全限定类名
                String className = method.getDeclaringClass().getName();
                // SQL唯一标识
                String statementId = className + "." + methodName;

                // 获取方法返回值类型
                Type genericReturnType = method.getGenericReturnType();
                // 判断返回值是否进行了 泛型类参数化
                if (genericReturnType instanceof ParameterizedType) {
                    List<Object> objects = selectList(statementId, args);
                    return objects;
                }

                return selectOne(statementId, args);
            }
        });

        return (E) o;
    }
}
