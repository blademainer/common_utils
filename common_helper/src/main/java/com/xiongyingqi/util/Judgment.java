package com.xiongyingqi.util;

import java.util.Collection;
import java.util.Map;

/**
 * Created by 瑛琪<a href="http://xiongyingqi.com">xiongyingqi.com</a> on 2014/8/11 0011.
 */
public class Judgment {
    /**
     * 判断String对象是否包含字符（如果全部为空格也返回false）
     *
     * @param string
     * @return
     */
    public static boolean hasText(String... string) {
        if (string == null) {
            return false;
        }

        boolean flag = true;
        for (String s : string) {
            flag = flag && (s != null && !"".equals(s.trim()));
            if (!flag) {
                return false;
            }
        }
        return flag;
    }

    public static boolean isNull(Object... o) {
        if (o != null) {
            return false;
        }

        boolean flag = true;
        for (Object o1 : o) {
            flag = flag && (o1 == null);
            if (!flag) {
                return false;
            }
        }
        return flag;
    }

    public static boolean notNull(Object... o) {
        if (o == null) {
            return false;
        }

        boolean flag = true;
        for (Object o1 : o) {
            flag = flag && (o1 != null);
            if (!flag) {
                return false;
            }
        }
        return flag;
    }


    public static boolean notEmpty(Collection... collection) {
        if (collection == null) {
            return false;
        }

        boolean flag = true;
        for (Collection o1 : collection) {
            flag = flag && (o1 != null && o1.size() > 0);
            if (!flag) {
                return false;
            }
        }
        return flag;
    }

    public static boolean hasEntry(Map... map) {
        if (map == null) {
            return false;
        }

        boolean flag = true;
        for (Map o1 : map) {
            flag = flag && (o1 != null && o1.size() > 0);
            if (!flag) {
                return false;
            }
        }
        return flag;
    }

    public static boolean isTrue(boolean... flag) {
        boolean _flag = true;
        for (boolean b : flag) {
            _flag = _flag && b;
            if (!_flag) {
                return false;
            }
        }
        return _flag;
    }


}
