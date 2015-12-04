package citic.gack.web.sso.utils;

import java.util.HashMap;
import java.util.Map;
/**
 * 文件封装工具类
 * 
 * @author jlz
 * 
 * @date 2015-4-24 17:52:21
 *
 */
public class Constants {

    private static Map<String, String> constants;

    @SuppressWarnings({ "static-access", "unchecked", "rawtypes" })
	public Constants(String... resourcesPaths) {
        PropertiesLoader propertiesLoader = new PropertiesLoader(resourcesPaths);
        if(propertiesLoader.getProperties() != null) {
            this.constants = ((Map)propertiesLoader.getProperties());
        }
        else {
            this.constants = new HashMap<String, String>();
        }
    }

    public static boolean containsKey(String key) {
        return constants.containsKey(key);
    }

    public static String valueOf(String key) {
        return constants.get(key);
    }
}