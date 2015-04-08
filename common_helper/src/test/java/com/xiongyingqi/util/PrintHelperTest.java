package com.xiongyingqi.util;

import java.text.MessageFormat;

public class PrintHelperTest {

    @org.junit.Test
    public void testPrintC() throws Exception {
        String rs = MessageFormat.format("{0}({1})", "a", "b");
        System.out.println(rs);
        A a = new A();
        a.setId(1);
        a.setName("blademainer");
        a.setPassword("123");
        PrintHelper.needComment()
                .addComment("id", "编号")
                .addComment("name", "名字")
                .ignoreProperty("password")
                .printC(a);
    }

    class A extends EntityHelper{
        private int id;
        private String name;
        private String password;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}