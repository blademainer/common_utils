package com.xiongyingqi.utils.baidu;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiongyingqi.util.PropertiesHelper;

import java.util.Map;

/**
 * <br>
 * Created by 瑛琪<a href="http://xiongyingqi.com">xiongyingqi.com</a> on 2014/7/9 0009.
 */
public class CommonUtil {
    public static String ipLocationApiUrl;
    public static String ak;
    public static String sk;
    public static boolean sn;


    public static ThreadLocal<ObjectMapper> _mapper = new ThreadLocal<ObjectMapper>() {
        @Override
        protected ObjectMapper initialValue() {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
            objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
            return objectMapper;
        }
    };

    static {
        Map<String, String> propertiesMap = PropertiesHelper.readProperties(CommonUtil.class.getClassLoader().getResource("baidu_api.properties").getFile());
        ipLocationApiUrl = propertiesMap.get("ip-location-api");
        ak = propertiesMap.get("ak");
        sk = propertiesMap.get("sk");
        String snStr = propertiesMap.get("sn");
        if (snStr != null && "true".equals(snStr)) {
            sn = true;
        }
    }
}
