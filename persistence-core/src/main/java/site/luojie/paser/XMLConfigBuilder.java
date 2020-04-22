package site.luojie.paser;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import site.luojie.Configuration;
import site.luojie.io.Resources;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * @Description: SqlMapConfig配置文件解析类
 * @Author jie.luo
 * @Create: 2020-04-22 21
 **/
public class XMLConfigBuilder {

    private Configuration configuration;

    public XMLConfigBuilder() {
        this.configuration = new Configuration();
    }

    /**
     * 解析 SqlMapConfig.xml 配置文件
     *
     * @param inputStream SqlMapConfig.xml 配置文件字节码
     * @return Configuration
     */
    public Configuration parseConfig(InputStream inputStream) throws DocumentException, PropertyVetoException {

        Document document = new SAXReader().read(inputStream);

        Element rootElement = document.getRootElement();

        List<Element> list = rootElement.selectNodes("//property");

        Properties properties = new Properties();
        for (Element element : list) {
            String name = element.attributeValue("name");
            String value = element.attributeValue("value");
            properties.setProperty(name, value);
        }

        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(properties.getProperty("driverClass"));
        dataSource.setJdbcUrl(properties.getProperty("jdbcUrl"));
        dataSource.setUser(properties.getProperty("username"));
        dataSource.setPassword(properties.getProperty("password"));

        this.configuration.setDataSource(dataSource);

        List<Element> mapperElements = rootElement.selectNodes("//mapper");
        for (Element mapperElement : mapperElements) {
            // mapper.xml 路径
            String mapperPath = mapperElement.attributeValue("resource");
            InputStream mapperInputStream = Resources.getResourceAsStream(mapperPath);

            new XMLMapperBuilder(this.configuration).parseMapper(mapperInputStream);
        }

        return this.configuration;
    }

}
