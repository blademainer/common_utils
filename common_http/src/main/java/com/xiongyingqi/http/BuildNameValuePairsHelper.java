package com.xiongyingqi.http;

import com.xiongyingqi.util.EntityHelper;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by 瑛琪<a href="http://xiongyingqi.com">xiongyingqi.com</a> on 2014/5/1 0001.
 */
public class BuildNameValuePairsHelper {

    public static String buildParameters(Collection<NameValuePair> nameValuePairs){
        StringBuilder stringBuilder = new StringBuilder();
        for (Iterator<NameValuePair> iterator = nameValuePairs.iterator(); iterator.hasNext(); ) {
            NameValuePair nameValuePair = iterator.next();
            String name = nameValuePair.getName();
            String value = nameValuePair.getValue();
            stringBuilder.append(name);
            stringBuilder.append("=");
            stringBuilder.append(value);
            if (iterator.hasNext()) {
                stringBuilder.append("&");
            }
        }
        return stringBuilder.toString();
    }
    public static List<NameValuePair> build(Object object) {
        if (object == null) {
            return null;
        }
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            field.setAccessible(true);
            try {
                Object value = field.get(object);
                if (value != null) {
                    NameValuePair nameValuePair = new BasicNameValuePair(field.getName(), value.toString());
                    nameValuePairs.add(nameValuePair);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        EntityHelper.print(nameValuePairs);
        return nameValuePairs;
    }

    public static String buildObjectToGet(Object object) {
        if (object == null) {
            return null;
        }

        StringBuilder stringBuilder = new StringBuilder();
        List<NameValuePair> nameValuePairs = build(object);
        NameValuePair nameValuePair = null;
        for (Iterator<NameValuePair> iterator = nameValuePairs.iterator(); iterator.hasNext(); ) {
            nameValuePair = iterator.next();
            String name = nameValuePair.getName();
            String value = nameValuePair.getValue();
            stringBuilder.append(name);
            stringBuilder.append("=");
            stringBuilder.append(value);
            if (iterator.hasNext()) {
                stringBuilder.append("&");
            }
        }
        EntityHelper.print(stringBuilder);
        return stringBuilder.toString();
    }


}
