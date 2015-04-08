/**
 * spark_src
 */
package com.xiongyingqi.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author XiongYingqi
 * @version 2013-6-25 下午9:41:57
 */
public class ComparatorHelper<T> {

    @SuppressWarnings("unchecked")
    public List sortByNumber(Collection<? extends T> collection, final boolean asc) {
        List list = new ArrayList();
        list.addAll(collection);
        Collections.sort(list, new Comparator<T>() {
            @Override
            public int compare(T o1, T o2) {
                int result = 0;
                if (o1 instanceof Number && o1 instanceof Number) {
                    Number number1 = (Number) o1;
                    Number number2 = (Number) o2;
                    if (number1.hashCode() < number2.hashCode()) {
                        result = -1;
                    } else if (number1.hashCode() > number2.hashCode()) {
                        result = -1;
                    }
                    if (!asc) {
                        result = -result;
                    }
                }
                return result;
            }
        });
        return list;
    }

    /**
     * 按照日期进行排序 <br>
     * 2013-6-25 下午9:46:45
     *
     * @param collection 要排序的集合
     * @param field      指定排序的时间字段
     * @param asc        是否按正序排序
     * @return List
     */
    @SuppressWarnings("unchecked")
    public List sortByDateTime(Collection<? extends T> collection, final Field field,
                               final boolean asc) {
        if (collection == null) {
            return null;
        }
        List list = new ArrayList();
        list.addAll(collection);
        Collections.sort(list, new Comparator<T>() {
            @Override
            public int compare(T o1, T o2) {
                Object object = invokeMethod(o1, field);
                Object object2 = invokeMethod(o2, field);
                if (object == null || object2 == null) {
                    return 0;
                }
                int value = 0;
                if (object instanceof Date) {
                    Date v1 = (Date) object;
                    Date v2 = (Date) object2;

                    if (v1.getTime() < v2.getTime()) {
                        value = -1;
                    } else if (v1.getTime() > v2.getTime()) {
                        value = 1;
                    }
                    if (!asc) {
                        value = -value;
                    }
                }
                return value;
            }

        });
        return list;
    }

    /**
     * 按照字符串进行排序 <br>
     * 2013-6-25 下午9:46:45
     *
     * @param collection 要排序的集合
     * @param field      指定排序的时间字段
     * @param asc        是否按正序排序
     * @return List
     */
    @SuppressWarnings("unchecked")
    public Collection sortByString(Collection<? extends T> collection, final Field field,
                                   final boolean asc) {
        if (collection == null) {
            return null;
        }
        List list = new ArrayList();
        list.addAll(collection);
        Collections.sort(list, new Comparator<T>() {
            @Override
            public int compare(T o1, T o2) {
                Object object = invokeMethod(o1, field);
                Object object2 = invokeMethod(o2, field);
                if (object == null || object2 == null) {
                    return 0;
                }
                int value = 0;
                if (object instanceof String) {
                    String v1 = (String) object;
                    String v2 = (String) object2;
                    if (v1.hashCode() < v2.hashCode()) {
                        value = -1;
                    } else {
                        value = 1;
                    }
                    if (!asc) {
                        value = -value;
                    }
                }
                return value;
            }

        });
        return list;
    }

    private Object invokeMethod(Object target, Field filed) {
        Object returnObject = null;
        Class clazz = target.getClass();
        String fieldName = filed.getName();
        String methodName = "get" + fieldName.substring(0, 1).toUpperCase()
                + fieldName.substring(1);
        try {
            Method method = clazz.getMethod(methodName);
            returnObject = method.invoke(target);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return returnObject;
    }

    static class Test {
        String i;

        /**
         * String
         *
         * @return the i
         */
        public String getI() {
            return i;
        }

        /**
         * String
         *
         * @param i the i to set
         */
        public void setI(String i) {
            this.i = i;
        }

        /**
         * <br>
         * 2013-9-11 下午12:01:52
         *
         * @see Object#hashCode()
         */
        @Override
        public int hashCode() {
            return i.hashCode();
        }

    }

    public static void main(String[] args) {
        Random random = new Random();
        Collection<Test> collection = new ArrayList<Test>();
        for (int i = 0; i < 100; i++) {
            String string = random.nextInt(1000) + "";
            Test test = new Test();
            test.setI(string);
            collection.add(test);
        }
        ComparatorHelper comparatorHelper = new ComparatorHelper<Test>();
        try {
            collection = comparatorHelper.sortByString(collection,
                    Test.class.getDeclaredField("i"), true);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        for (Iterator iterator = collection.iterator(); iterator.hasNext(); ) {
            Test test = (Test) iterator.next();
            System.out.println(test.getI());
        }
    }
}
