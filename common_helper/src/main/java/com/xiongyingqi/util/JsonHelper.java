package com.xiongyingqi.util;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by 瑛琪<a href="http://xiongyingqi.com">xiongyingqi.com</a> on 2014/3/25 0025.
 */
public class JsonHelper {
    /**
     * 获取json字符串
     *
     * @param object
     * @return
     */
    public static String getJsonString(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        try {
            json = objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 获取格式化的json数组
     *
     * @param object
     * @return
     */
    public static String getPrettyJsonString(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        try {
            json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    public static void printJson(Object object) {
        System.out.println(getJsonString(object));
    }

    public static void printPrettyJson(Object object) {
        System.out.println(getPrettyJsonString(object));
    }
}
