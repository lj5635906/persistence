package site.luojie.sqlSession;

import site.luojie.Configuration;
import site.luojie.MappedStatement;
import site.luojie.utils.GenericTokenParser;
import site.luojie.utils.ParameterMapping;
import site.luojie.utils.ParameterMappingTokenHandler;
import site.luojie.utils.TokenHandler;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: SQL 执行器实现
 * @Author jie.luo
 * @Create: 2020-04-22 22
 **/
public class SimpleExecutor implements Executor {
    @Override
    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception {

        // 1.加载驱动、获取连接
        Connection connection = configuration.getDataSource().getConnection();

        // 2.获取SQL语句 : select * from USERS where USER_ID = #{userId} and USER_NAME = #{userName}
        // 转换SQL select * from USERS where USER_ID = ? and USER_NAME = ?
        String sql = mappedStatement.getSql();
        // 转换后的SQL信息
        BoundSql boundSql = getBoundSql(sql);

        // 3.获取预处理对象
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSqlTest());

        // 4.设置参数
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        // 获取参数的全路径
        String parameterType = mappedStatement.getParameterType();
        // 根据参数全路径加载类
        Class<?> parameterTypeClass = getClassType(parameterType);

        for (int i = 0; i < parameterMappings.size(); i++) {
            ParameterMapping parameterMapping = parameterMappings.get(i);
            // #{id} 中的名称[id]
            String content = parameterMapping.getContent();

            // 根据反射获取参数值信息
            // 获取熟悉对象
            Field declaredField = parameterTypeClass.getDeclaredField(content);
            // 设置暴力访问
            declaredField.setAccessible(true);
            Object o = declaredField.get(params[0]);

            preparedStatement.setObject(i + 1, o);
        }

        // 5.执行SQL
        ResultSet resultSet = preparedStatement.executeQuery();

        // 6.封装返回结果集
        String resultType = mappedStatement.getResultType();
        Class<?> resultTypeClass = getClassType(resultType);
        ArrayList<Object> objects = new ArrayList<>();
        while (resultSet.next()) {
            Object o = resultTypeClass.newInstance();
            // 获取元数据
            ResultSetMetaData metaData = resultSet.getMetaData();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                // 字段名
                String columnName = metaData.getColumnName(i);
                // 字段值
                Object value = resultSet.getObject(columnName);

                // 使用反射获知内省，根据数据库表和实体的对应关系完成封装
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName, resultTypeClass);
                Method writeMethod = propertyDescriptor.getWriteMethod();
                writeMethod.invoke(o, value);
            }
            objects.add(o);
        }
        return (List<E>) objects;
    }

    /**
     * 根据类的全路径加载类
     *
     * @param classPath 全新的类名
     * @return Class<?>
     * @throws ClassNotFoundException
     */
    private Class<?> getClassType(String classPath) throws ClassNotFoundException {
        if (null != classPath) {
            Class<?> aClass = Class.forName(classPath);
            return aClass;
        }
        return null;
    }

    /**
     * 完成对 #{} 的解析工作，将 #{} 使用 ? 进行代替
     * 解析出 #{} 里面的值进行存储
     *
     * @param sql SQL语句 : select * from USERS where USER_ID = #{userId} and USER_NAME = #{userName}
     * @return BoundSql
     */
    private BoundSql getBoundSql(String sql) {

        // 标记处理类：配置标记解析器来完成对占位符的解析处理工作
        ParameterMappingTokenHandler tokenHandler = new ParameterMappingTokenHandler();
        // 获取标记解析器
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", tokenHandler);
        // 解析后的SQL
        String parseSql = genericTokenParser.parse(sql);
        // #{} 里面解析出来的参数名称
        List<ParameterMapping> parameterMappings = tokenHandler.getParameterMappings();

        BoundSql boundSql = new BoundSql(parseSql, parameterMappings);
        return boundSql;
    }
}
