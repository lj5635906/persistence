package site.luojie.sqlSession;

import lombok.AllArgsConstructor;
import lombok.Data;
import site.luojie.utils.ParameterMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: SQL转换
 * @Author jie.luo
 * @Create: 2020-04-22 22
 **/
@Data
@AllArgsConstructor
public class BoundSql {

    /**
     * 解析过后的SQL
     */
    private String sqlTest;
    /**
     * #{} 里面解析出来的参数名称
     */
    private List<ParameterMapping> parameterMappings = new ArrayList<>();

}
