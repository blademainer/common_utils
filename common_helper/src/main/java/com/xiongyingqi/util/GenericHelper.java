package com.xiongyingqi.util;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by 瑛琪<a href="http://xiongyingqi.com">xiongyingqi.com</a> on 2014/10/20 0020.
 */
public class GenericHelper<T> {



    public Type[] getMethodReturnGenericTypes(Method method) {
        Assert.notNull(method, "方法为空！");
        Type genericReturnType = method.getGenericReturnType();
        if (genericReturnType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) genericReturnType;
//            Type rawType = parameterizedType.getRawType();
//            for (Type type : parameterizedType.getActualTypeArguments()) {
//                System.out.println(type);
//            }
            return parameterizedType.getActualTypeArguments();
        }
        return null;
    }

    public Map<Integer, Type[]> getMethodArgumentGenericTypes(Method method) {
        int i = 0;
        Map<Integer, Type[]> argumentIndexAndGenericTypeMap = new LinkedHashMap<Integer, Type[]>();
        Type[] types = method.getGenericParameterTypes();
        for (Type type : types) {
            if (type instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) type;
//                Type type1 = parameterizedType.getRawType();

                Type[] genericTypes = parameterizedType.getActualTypeArguments();
                argumentIndexAndGenericTypeMap.put(i, genericTypes);
            } else {
                argumentIndexAndGenericTypeMap.put(i, null);
            }

            i++;
        }
        return argumentIndexAndGenericTypeMap;
    }


}
