package com.yonyou.util;


import java.io.InputStream;
import java.util.Properties;

/**
 * @ClassName: ProUtils
 * @Description: TODO(类简要描述，必须以句号为结束)
 * @author caozq
 * @date 2018年3月7日
 */
public class ProUtils {

    private static Properties pro = new Properties();
    static {
        try {
            InputStream inputStream = ProUtils.class
                    .getClassLoader()
                    .getResourceAsStream("config.properties");
            pro.load(inputStream);
        } catch (Exception e) {

        }
    }

    public static String getProperty(String key) {
        return pro.getProperty(key);
    }
    
    
    public static String getProperty(String key,String defaultVal) {
        if(null == getProperty(key)){
            return defaultVal;
        }
        return pro.getProperty(key);
    }

    public static Integer getInteger(String key) {
        return Integer.valueOf(pro.getProperty(key));
    }

    public static Boolean getBoolean(String key) {
        return Boolean.valueOf(pro.getProperty(key));
    }

}
