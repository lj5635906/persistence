package site.luojie.paser;

import lombok.val;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import site.luojie.Configuration;
import site.luojie.MappedStatement;

import java.io.InputStream;
import java.util.List;

/**
 * @Description: *Mapper.xml SQL文件解析类
 * @Author jie.luo
 * @Create: 2020-04-22 21
 **/
public class XMLMapperBuilder {

    private Configuration configuration;

    public XMLMapperBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    /**
     * 解析 *Mapper.xml SQL 文件
     *
     * @param inputStream *Mapper.xml SQL 文件字节码
     */
    public void parseMapper(InputStream inputStream) throws DocumentException {

        Document document = new SAXReader().read(inputStream);

        Element rootElement = document.getRootElement();
        String namespace = rootElement.attributeValue("namespace");

        List<Element> list = rootElement.selectNodes("//select");
        for (Element element : list) {
            String id = element.attributeValue("id");
            String resultType = element.attributeValue("resultType");
            String parameterType = element.attributeValue("parameterType");
            String sql = element.getTextTrim();

            MappedStatement mappedStatement = new MappedStatement();
            mappedStatement.setId(id);
            mappedStatement.setResultType(resultType);
            mappedStatement.setParameterType(parameterType);
            mappedStatement.setSql(sql);

            configuration.getMappedStatementMap().put(namespace + "." + id, mappedStatement);
        }
    }

}
