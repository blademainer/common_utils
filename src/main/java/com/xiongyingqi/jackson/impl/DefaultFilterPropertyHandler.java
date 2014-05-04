/**
 * YIXUN_1.5_EE
 */
package com.xiongyingqi.jackson.impl;

import com.xiongyingqi.jackson.FilterPropertyHandler;
import com.xiongyingqi.jackson.annotation.IgnoreProperties;
import com.xiongyingqi.jackson.annotation.IgnoreProperty;
import com.xiongyingqi.util.EntityHelper;
import com.xiongyingqi.util.StackTraceHelper;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.Map.Entry;

/**
 * @author 瑛琪 <a href="http://xiongyingqi.com">xiongyingqi.com</a>
 * @version 2013-9-27 下午6:50:14
 */
@Component
public class DefaultFilterPropertyHandler implements FilterPropertyHandler {

    //	private static Map<Class<?>, Collection<IgnoreProperty>> methodAndClassPropertyMap = new HashMap<Class<?>, Collection<IgnoreProperty>>();
    /**
     * 每个被注解的方法上面的所有属性存储表
     */
    private static Map<Method, Map<Class<?>, Collection<IgnoreProperty>>> methodAndIgnorePropertyMap = new HashMap<Method, Map<Class<?>, Collection<IgnoreProperty>>>();
    /**
     * 每个被注解的类上面的所有属性存储表
     */
    private static Map<Class<?>, Map<Class<?>, Collection<IgnoreProperty>>> classAndIgnorePropertyMap = new HashMap<Class<?>, Map<Class<?>, Collection<IgnoreProperty>>>();
    /**
     * 方法和过滤属性名的映射
     */
    private static Map<Method, Collection<String>> methodAndNamesMap = new HashMap<Method, Collection<String>>();

    /**
     * 方法和过滤属性名的映射
     */
    private static Map<Class<?>, Collection<String>> classAndNamesMap = new HashMap<Class<?>, Collection<String>>();

    /**
     * 方法执行层次
     */
    private Map<Object, Map<Method, Collection<Object>>> invokedMethodMap = new HashMap<Object, Map<Method, Collection<Object>>>();

    public static final Logger LOGGER = Logger.getLogger(DefaultFilterPropertyHandler.class);

    private Map<Method, Map<Class<?>, Collection<IgnoreProperty>>> readAnnotations(Method method,
                                                                                   Class<?> clazz) {
        //		Map<Class<?>, Collection<IgnoreProperty>> methodAndClassMap = new HashMap<Class<?>, Collection<IgnoreProperty>>();

        Map<Class<?>, Collection<IgnoreProperty>> map = classAndIgnorePropertyMap.get(clazz);// 获取方法所在的类下的注解
        if (map == null) {
            readMethodAnnotationsByClass(clazz);
        }

        // 获取方法的属性注解
        IgnoreProperties ignoreProperties = method.getAnnotation(IgnoreProperties.class);
        if (ignoreProperties != null) {
            IgnoreProperty[] properties = ignoreProperties.value();
            Map<Class<?>, Collection<IgnoreProperty>> methodMap = new HashMap<Class<?>, Collection<IgnoreProperty>>();
            for (int i = 0; i < properties.length; i++) {
                IgnoreProperty ignoreProperty = properties[i];

                Class<?> pojo = ignoreProperty.pojo();
                if (pojo != null) {
                    Collection<IgnoreProperty> collection = methodMap.get(pojo);
                    if (collection == null) {
                        collection = new HashSet<IgnoreProperty>();
                    }
                    collection.add(ignoreProperty);

                    methodMap.put(pojo, collection);
                }

            }

            Map<Class<?>, Collection<IgnoreProperty>> rs = methodAndIgnorePropertyMap.get(method);
            if (rs != null) {
                rs.putAll(methodMap);
            } else {
                methodAndIgnorePropertyMap.put(method, methodMap);
            }
            //			methodAndIgnorePropertyMap.put(method, methodMap);
            //			methodAndClassPropertyMap.putAll(methodMap);// 放入所有要处理的注解
        }
        return methodAndIgnorePropertyMap;
    }

    private Map<Class<?>, Map<Class<?>, Collection<IgnoreProperty>>> readMethodAnnotationsByClass(
            Class<?> clazz) {
        IgnoreProperties ignorePropertiesForClass = clazz.getAnnotation(IgnoreProperties.class);
        if (ignorePropertiesForClass != null) {
            IgnoreProperty[] propertiesForClass = ignorePropertiesForClass.value(); // 单个过滤属性
            Map<Class<?>, Collection<IgnoreProperty>> classMap = new HashMap<Class<?>, Collection<IgnoreProperty>>(); // 类的过滤属性
            for (int i = 0; i < propertiesForClass.length; i++) {
                IgnoreProperty ignoreProperty = propertiesForClass[i];

                Class<?> pojo = ignoreProperty.pojo();
                if (pojo != null) {
                    Collection<IgnoreProperty> collection = classMap.get(pojo);
                    if (collection == null) {
                        collection = new HashSet<IgnoreProperty>();
                    }
                    collection.add(ignoreProperty);

                    classMap.put(pojo, collection);
                }

            }

            Method[] methods = clazz.getDeclaredMethods();
            for (int j = 0; j < methods.length; j++) {// 给每个方法都放入该注解
                Method method = methods[j];
                Map<Class<?>, Collection<IgnoreProperty>> methodIgnorePropertyVar = methodAndIgnorePropertyMap
                        .get(method);
                if (methodIgnorePropertyVar == null) {
                    methodIgnorePropertyVar = new HashMap<Class<?>, Collection<IgnoreProperty>>();
                }
                methodIgnorePropertyVar.putAll(classMap);
                methodAndIgnorePropertyMap.put(method, methodIgnorePropertyVar);
            }

            classAndIgnorePropertyMap.put(clazz, classMap);

            //			methodAndClassPropertyMap.putAll(classMap);// 放入所有要处理的注解
        }
        return classAndIgnorePropertyMap;
    }

    public Object filterObject(Method method, Object object) {
        if (object == null) {
            return object;
        }
        Object proxy = object;
        try {
            Class<?> methodInClass = method.getDeclaringClass();// 注解的方法所在的类
            if (methodInClass.isAnnotationPresent(IgnoreProperties.class)
                    || method.isAnnotationPresent(IgnoreProperties.class)) {
                Class<? extends Object> clazz = object.getClass();

                Map<Class<?>, Collection<IgnoreProperty>> map = methodAndIgnorePropertyMap
                        .get(method);
                Collection<IgnoreProperty> propertiesVar = null;
                if (map == null) {
                    readAnnotations(method, methodInClass);
                    map = methodAndIgnorePropertyMap.get(method);
                    propertiesVar = map.get(clazz);
                }
                //				Map<Class<?>, Collection<IgnoreProperty>> map = methodAndIgnorePropertyMap
                //						.get(method);//new HashMap<Class<?>, Collection<String>>()
                //				if(map != null && classAndIgnorePropertyMap != null){
                //					map.putAll(classAndIgnorePropertyMap.get(clazz));
                //				} else {// 如果该方法未读取其注解值，则读取放入缓存
                //					map = readAnnotations(method, object);
                //					if (map == null) {
                //						if(methodAndIgnorePropertyMap != null){
                //							map = methodAndIgnorePropertyMap.get(method);
                //						}
                //						if(classAndIgnorePropertyMap != null){
                //							map.putAll(classAndIgnorePropertyMap.get(clazz));
                //						}
                //					}
                //				}
                Collection<String> ignorePropertyNames = new HashSet<String>();

                Collection<String> ignoreMethodPropertyNames = methodAndNamesMap.get(method);
                if (ignorePropertyNames != null && ignoreMethodPropertyNames != null) {
                    ignorePropertyNames.addAll(ignoreMethodPropertyNames);
                }
                Collection<String> ignoreMethodPropertyNamesByClass = classAndNamesMap
                        .get(methodInClass);
                if (ignorePropertyNames != null && ignoreMethodPropertyNamesByClass != null) {
                    ignorePropertyNames.addAll(ignoreMethodPropertyNamesByClass);
                }

                try {
                    if ((propertiesVar != null && propertiesVar.size() > 0)
                            || (ignorePropertyNames != null && ignorePropertyNames.size() > 0)) {
                        try {
                            proxy = clazz.newInstance();// 创建代理对象
                        } catch (Exception e) {
                        }

                        Field[] fields = clazz.getDeclaredFields();
                        for (int i = 0; i < fields.length; i++) {
                            Field field = fields[i];
                            int modifiers = field.getModifiers();
                            if (Modifier.isStatic(modifiers) || Modifier.isFinal(modifiers)) {
                                continue;
                            }

                            String fieldName = field.getName();
                            boolean isIgnoreField = false;
                            if (propertiesVar != null) {
                                for (Iterator<IgnoreProperty> iterator = propertiesVar.iterator(); iterator
                                        .hasNext(); ) {// 查找是否在该类上进行了注解
                                    IgnoreProperty ignoreProperty = iterator.next();

                                    Class<?> clazzOfPojo = ignoreProperty.pojo();
                                    String[] ignoreNames = ignoreProperty.name();// 过滤的字段名
                                    //String value = ignoreProperty.value();
                                    if (clazzOfPojo != null && ignoreNames.length > 0) {
                                        for (int j = 0; j < ignoreNames.length; j++) {
                                            String ignoreName = ignoreNames[j];
                                            if (fieldName.equals(ignoreName)) {
                                                isIgnoreField = true;
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                            if (ignoreMethodPropertyNames != null) {
                                for (Iterator<String> iterator2 = ignoreMethodPropertyNames
                                        .iterator(); iterator2.hasNext(); ) {
                                    String globalIgnoreString = iterator2.next();
                                    if (fieldName.equals(globalIgnoreString)) {
                                        isIgnoreField = true;
                                        break;
                                    }
                                }
                            }
                            //							if (ignoreNames == null) {
                            //								isIgnoreField = false;
                            //							}

                            LOGGER.debug("clazz: " + clazz + ", fieldName: " + fieldName
                                    + ", isIgnoreField: " + isIgnoreField);
                            if (!isIgnoreField) { // 如果该字段不是忽略的字段名，则获取该值
                                // 取值方法
                                Method getValueMethod = EntityHelper.getMethodOfBeanByField(clazz,
                                        field);
                                // 设置方法
                                Method setValueMethod = EntityHelper.setMethodOfBeanByField(clazz,
                                        field);
                                if (getValueMethod == null || getValueMethod == null) {
                                    continue;
                                }
                                try {
                                    Object returnValue = getValueMethod.invoke(object);

                                    //									boolean isFlag = false;
                                    //									try {
                                    //										isFlag = checkForInvokeMethod(object, getValueMethod,
                                    //												returnValue);
                                    //									} catch (Exception e) {
                                    //										e.printStackTrace();
                                    //									}

                                    // ----------------------------------- 检查迭代是否超过次数  -----------------------------------
                                    if (!EntityHelper.isSimpleType(field)) {
                                        //										returnValue = checkForIterationLevel(getValueMethod,
                                        //												propertiesVar, clazz, returnValue);
                                        //										if (isFlag) {
                                        if (checkForStackOver(returnValue)) {
                                            returnValue = filterProperties(method, returnValue);
                                        }
                                        //										} else {
                                        //											returnValue = null;
                                        //										}
                                    }
                                    // ----------------------------------- 检查迭代是否超过次数结束  -----------------------------------

                                    setValueMethod.invoke(proxy, returnValue);// 将值放入代理类

                                } catch (IllegalArgumentException e) {
                                    e.printStackTrace();
                                } catch (InvocationTargetException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return proxy;
    }

    private Map<Object, Map<String, Integer>> map = new HashMap<Object, Map<String, Integer>>();

    private boolean checkForStackOver(Object instance) {
        boolean isFlag = false;

        //------------------ 获取堆栈 ------------------
        Map<String, Integer> invokeLevelMap = new HashMap<String, Integer>();
        StackTraceElement[] stackTraceElements = StackTraceHelper.getStackTrace();
        int length = stackTraceElements.length;
        if (length > 100) {
            StackTraceHelper.printStackTrace();
            return false;
        }
        for (int i = 0; i < length; i++) {
            StackTraceElement stackTraceElement = stackTraceElements[i];
            String className = stackTraceElement.getClassName();
            if (className.equals(getClass().getName())) {
                // stackTraceElements[2].getMethodName()： 获取调用checkForIterationLevel()所在的方法名
                // stackTraceElement.getMethodName().equals(stackTraceElements[2].getMethodName()): 根据此值判断调用本方法checkForIterationLevel()的源头已经是第几层
                if (stackTraceElement.getMethodName().equals(stackTraceElements[2].getMethodName())) {
                    //					int level = 0;
                    //					try {
                    //						level = map.get(instance);
                    //					} catch (Exception e) {
                    //					}
                    //					level++;
                    //					invokeLevelMap.put(, level);
                }
            }
        }
        //------------------ 获取堆栈结束 ------------------
        return isFlag;
    }

    /**
     * 检查同一个对象是否调用了同一个方法 <br>
     * 2013-10-22 上午10:13:34
     *
     * @param instance
     * @param method
     * @param returnValue
     * @return
     */
    private boolean checkForInvokeMethod(Object instance, Method method, Object returnValue) {
        boolean isFlag = false;
        Map<Method, Collection<Object>> map = invokedMethodMap.get(instance);
        if (map == null) {
            map = new HashMap<Method, Collection<Object>>();
            invokedMethodMap.put(instance, map);
        }
        Collection<Object> collection = map.get(method);
        if (collection == null) {
            collection = new HashSet<Object>();
            map.put(method, collection);
        }
        try {
            if (!collection.contains(returnValue)) {
                isFlag = true;
                LOGGER.debug("被过滤属性：" + returnValue);
            }
            collection.add(returnValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isFlag;
    }

	/*
    public Object checkForIterationLevel(Method method,
			Collection<IgnoreProperty> ignoreProperties, Class<?> clazz, Object returnValue) {
		//		boolean isPass = false;

		//		StackTraceHelper.printStackTrace();

		//		Map<Method, Collection<Object>> iterationLevel = null;
		//
		//		Map<Class<?>, Method> map = new HashMap<Class<?>, Method>();
		//		map.put(clazz, method);
		//		try {
		//			iterationLevel = objectIterationLevelMap.get(map);
		//		} catch (Exception e) {
		//		}
		if (ignoreProperties == null) {
			return returnValue;
		}
		for (Iterator iterator = ignoreProperties.iterator(); iterator.hasNext();) {
			IgnoreProperty ignoreProperty = (IgnoreProperty) iterator.next();
			if (ignoreProperty.maxIterationLevel() != 0
					&& ignoreProperty.pojo().equals(ignoreProperty.pojo())) {

				//------------------ 获取堆栈 ------------------
				Map<String, Integer> invokeLevelMap = new HashMap<String, Integer>();
				StackTraceElement[] stackTraceElements = StackTraceHelper.getStackTrace();
				int length = stackTraceElements.length;
				for (int i = 0; i < length; i++) {
					StackTraceElement stackTraceElement = stackTraceElements[i];
					String className = stackTraceElement.getClassName();
					if (className.equals(getClass().getName())) {
						// stackTraceElements[2].getMethodName()： 获取调用checkForIterationLevel()所在的方法名
						// stackTraceElement.getMethodName().equals(stackTraceElements[2].getMethodName()): 根据此值判断调用本方法checkForIterationLevel()的源头已经是第几层
						if (stackTraceElement.getMethodName().equals(
								stackTraceElements[2].getMethodName())) {
							int level = 0;
							try {
								level = invokeLevelMap.get(stackTraceElements[2].getMethodName());
							} catch (Exception e) {
							}
							level++;
							invokeLevelMap.put(stackTraceElements[2].getMethodName(), level);
						}
					}
				}
				//------------------ 获取堆栈结束 ------------------

				if (invokeLevelMap.get(stackTraceElements[2].getMethodName()) >= ignoreProperty
						.maxIterationLevel()) {// 如果迭代的次数小于注解的最大次数，则进行设值操作
					returnValue = null;
				}
			}
		}
		return returnValue;
		//		EntityHelper.print(isPass);
		//		return isPass;
	}
	*/

    /**
     * <br>
     * 2013-9-27 下午6:54:34
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public Object filterProperties(Method method, Object object) {
        Object target = object;
        if (Collection.class.isInstance(object)) {
            Class<?> collectionClass = object.getClass();
            if (List.class.isInstance(object)) {
                collectionClass = ArrayList.class;
            } else if (Set.class.isInstance(object)) {
                collectionClass = LinkedHashSet.class;
            }
            try {
                Collection targetCollection = (Collection) collectionClass.newInstance();
                Collection originCollection = (Collection) object;

                try {
                    Object object2 = null;
                    for (Iterator<? extends Object> iterator = originCollection.iterator(); iterator
                            .hasNext(); ) {
                        object2 = (Object) iterator.next();
                        targetCollection.add(filterProperties(method, object2));
                    }
                    target = targetCollection;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else if (Map.class.isInstance(object)) {
            try {
                Map targetMap = (Map) Object.class.newInstance();
                Map originMap = (Map) object;
                Set<Entry> entries = originMap.entrySet();
                for (Iterator iterator = entries.iterator(); iterator.hasNext(); ) {
                    Entry entry = (Entry) iterator.next();
                    Object key = entry.getKey();
                    Object value = entry.getValue();
                    targetMap.put(filterProperties(method, key), filterProperties(method, value));
                }

                target = targetMap;

            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            target = filterObject(method, object);
        }
        return target;

    }

    public static void main(String[] args) {
        Package package1 = DefaultFilterPropertyHandler.class.getPackage();
        //		String path = System.getProperty("java.class.path");
        EntityHelper.print(package1);
    }

}
