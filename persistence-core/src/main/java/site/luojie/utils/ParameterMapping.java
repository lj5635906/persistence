package site.luojie.utils;

import lombok.Data;

@Data
public class ParameterMapping {

    /**
     * 解析出来的参数名称
     */
    private String content;

    public ParameterMapping(String content) {
        this.content = content;
    }

}
