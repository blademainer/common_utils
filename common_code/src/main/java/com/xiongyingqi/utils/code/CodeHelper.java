package com.xiongyingqi.utils.code;

import com.xiongyingqi.util.EntityHelper;

import java.io.File;

/**
 * 代码帮助类
 *
 * @author 瑛琪 <a href="http://xiongyingqi.com">xiongyingqi.com</a>
 * @version 2014/3/20 0020 17:59
 */
public class CodeHelper {

    public static CodeBuilder newCodeBuilder(String folderPath) {
        return newCodeBuilder(new File(folderPath));
    }


    public static CodeBuilder newCodeBuilder(File baseFolder) {
        return new CodeBuilder(baseFolder);
    }

    public static void main(String[] args) {
        newCodeBuilder("D:\\workspace_JavaEE\\YIXUN_1.5\\YIXUN_1.5_ENTITY\\src\\main\\java\\com\\kingray\\domain\\b\\bk").superClass(EntityHelper.class).build();
    }
}
