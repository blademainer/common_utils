package com.xiongyingqi.util;

public class DomainHelper {
    /**
     * 移除主机字符，例如传入admin@xiongyingqi.com，那么该方法将截取@之前的字符返回admin
     *
     * @param str
     * @return
     */
    public static String removeDomain(String str) {
        if (str != null && !"".equals(str)) {
            int index = str.indexOf("@");
            if (index >= 0) {
                str = str.substring(0, index);
            }
        }
        return str;
    }
}
