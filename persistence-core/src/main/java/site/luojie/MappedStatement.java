package site.luojie;

import lombok.Data;

/**
 * @Description: SQL语句信息
 * @Author jie.luo
 * @Create: 2020-04-22 21
 **/
@Data
public class MappedStatement {

    /**
     * id标识
     */
    private String id;
    /**
     * 输出类型
     */
    private String resultType;
    /**
     * 参数类型
     */
    private String parameterType;
    /**
     * SQL语句
     */
    private String sql;

}
