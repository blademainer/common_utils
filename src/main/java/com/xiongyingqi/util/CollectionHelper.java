/**
 * YIXUN_2.0
 */
package com.xiongyingqi.util;

import java.util.*;

/**
 * @author 瑛琪 <a href="http://xiongyingqi.com">xiongyingqi.com</a>
 * @version 2013-8-22 下午3:50:35
 */
public class CollectionHelper {
    public static boolean notNullAndHasSize(Collection<?> collection) {
        return collection != null && collection.size() > 0;
    }

    public static <T> Collection<T> checkOrInitHashSet(Collection<T> collection) {
        if (collection == null) {
            collection = new HashSet();
        }
        return collection;
    }

    public static <T> Collection<T> checkOrInitLinkedHashSet(Collection<T> collection) {
        if (collection == null) {
            collection = new LinkedHashSet();
        }
        return collection;
    }

    public static <K, V> Map<K, V> checkOrInitHashMap(Map<K, V> map) {
        if (map == null) {
            map = new HashMap();
        }
        return map;
    }

    public static <K, V> Map<K, V> checkOrInitLinkedHashMap(Map<K, V> map) {
        if (map == null) {
            map = new LinkedHashMap();
        }
        return map;
    }

    public static <T> Collection<T> checkOrInitArrayList(Collection<T> collection) {
        if (collection == null) {
            collection = new ArrayList();
        }
        return collection;
    }

    public static <T> Collection<T> checkOrInitLinkedList(Collection<T> collection) {
        if (collection == null) {
            collection = new LinkedList();
        }
        return collection;
    }


    public static void main(String[] args) {
        Object a = null;
        a = checkOrInitHashSet((Collection<Object>) a);
        System.out.println(a);
    }
}
