package site.luojie.io;

import java.io.InputStream;

/**
 * @Description: 配置加载类
 * @Author jie.luo
 * @Create: 2020-04-22 21
 **/
public class Resources {

    /**
     * 将配置文件读取为字节码，并存储到内存中
     *
     * @param path 配置文件地址
     * @return 配置文件字节码
     */
    public static InputStream getResourceAsStream(String path) {
        return Resources.class.getClassLoader().getResourceAsStream(path);
    }

}
