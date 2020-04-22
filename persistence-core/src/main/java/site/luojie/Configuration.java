package site.luojie;

import lombok.Data;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 配置信息类
 * @Author jie.luo
 * @Create: 2020-04-22 21
 **/
@Data
public class Configuration {

    /**
     * 数据源
     */
    private DataSource dataSource;
    /**
     * SQL信息
     * id = namespace+'.'+id
     */
    private Map<String, MappedStatement> mappedStatementMap = new HashMap<>();

}
