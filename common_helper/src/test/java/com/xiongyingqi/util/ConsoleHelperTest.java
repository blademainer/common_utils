package com.xiongyingqi.util;

import java.io.File;

public class ConsoleHelperTest {

//    @org.junit.Test
    public void test() {
        String filePath = getClass().getClassLoader().getResource("").getFile();
        File file = new File(filePath, "console.out");
        ConsoleHelper consoleHelper = new ConsoleHelper(file, ConsoleHelper.FileStrategy.DAILY);

        for (int i = 0; i < 1000; i++) {
            System.out.println("test" + i);
        }

        File[] files = consoleHelper.listFiles();
        for (File file1 : files) {
            System.out.println(file1);
        }
    }

}