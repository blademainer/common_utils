package com.xiongyingqi.util;

import java.net.URL;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * 资源加载类
 *
 * @author XiongYingqi
 */
public class KingrayResource {
    private static PropertyResourceBundle prb;

    // private static PropertyResourceBundle programsBundle;

    private KingrayResource() {
    }

    static {
        prb = (PropertyResourceBundle) ResourceBundle.getBundle("i18n/kingrayplugin_i18n");
    }

    public static final String getString(String propertyName) {
        return prb.getString(propertyName);
    }

}