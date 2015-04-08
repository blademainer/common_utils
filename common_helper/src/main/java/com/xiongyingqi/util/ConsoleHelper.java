package com.xiongyingqi.util;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * java控制台帮助类
 * Created by blademainer<a href="http://xiongyingqi.com">xiongyingqi.com</a> on 2014/11/6 0006.
 */
public class ConsoleHelper {
    private File file;
    private OutputStream outputStream;
    private PipedInputStream pipedInputStream;
    private boolean isSetPipedInputStream;
    private boolean running;
    private FileStrategy fileStrategy;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HHmmss");
    private long nextCheckTime = new Date().getTime();

    private long maxFileSize = 30 * FileHelper.MB;

    public enum FileStrategy {
        DAILY, MAX_SIZE
    }

    public ConsoleHelper(File file, FileStrategy fileStrategy){
        Assert.notNull(file);
        this.file = file;
        if (fileStrategy == null) {
            fileStrategy = FileStrategy.DAILY;
        }
        this.fileStrategy = fileStrategy;
        FileHelper.initFile(file);
        try {
            this.outputStream = new FileOutputStream(file, true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        init();
    }

    public ConsoleHelper(OutputStream outputStream) {
        this.outputStream = outputStream;
        init();
    }

    public ConsoleHelper(PipedInputStream pipedInputStream) {
        this.pipedInputStream = pipedInputStream;
        isSetPipedInputStream = true;
        init();
    }

    public long getMaxFileSize() {
        return maxFileSize;
    }

    public void setMaxFileSize(long maxFileSize) {
        this.maxFileSize = maxFileSize;
    }

    public void init() {
        if (pipedInputStream == null) {
            isSetPipedInputStream = false;
            pipedInputStream = new PipedInputStream();
        }
        PipedOutputStream pipedOS = new PipedOutputStream();
        try {
            pipedOS.connect(pipedInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintStream ps = new PrintStream(pipedOS);
        System.setOut(ps);
        System.setErr(ps);

        if (!isSetPipedInputStream) {
            running = true;
            ReadThread readThread = new ReadThread();
            readThread.start();
        }
    }

    public void stop() {
        running = false;
    }

    private void append(byte[] bts) {
//        final File file = new File("out.txt");
//        FileHelper.appendBytesToFile(file, bts);
        checkFile();
        try {
            outputStream.write(bts);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkFile() {
        if (fileStrategy != null) {
            if (fileStrategy == FileStrategy.DAILY && System.currentTimeMillis() >= nextCheckTime) {
                long l = file.lastModified();
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(l);
                int i = DateHelper.diffOfDay(calendar.getTime(), new Date());
                if (i != 0) {
                    renameFile();
                }
                Date day = DateHelper.getDay(new Date());// 今天0时
                Calendar calendarTomorrow = Calendar.getInstance();
                calendarTomorrow.setTimeInMillis(day.getTime());
                calendarTomorrow.set(Calendar.DAY_OF_YEAR, calendarTomorrow.get(Calendar.DAY_OF_YEAR) + 1);
                nextCheckTime = calendarTomorrow.getTimeInMillis();
            } else if (fileStrategy == FileStrategy.MAX_SIZE) {
                long length = file.length();
                if (length > maxFileSize) {
                    renameFile();
                }
            }
        }
    }

    private void renameFile() {
        long l = file.lastModified();
        Date date = new Date(l);
        String dateToStr = dateFormat.format(date);
        String fileName = file.getName();
        String parent = file.getParent();
        File newFile = new File(fileName + "." + dateToStr);
        try {
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        boolean b = file.renameTo(newFile);
        if (b) {
            file = new File(parent, fileName);
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public File[] listFiles() {
        final String fileName = file.getName();
        File parentFile = file.getParentFile();
        File[] files = parentFile.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.startsWith(fileName + ".");
            }
        });
        return files;
    }

    class ReadThread extends Thread {
        @Override
        public void run() {
            int bufferLength = 1024;
            byte[] bts = new byte[bufferLength];
            try {
                int length;
                while (running) {
                    if ((length = pipedInputStream.read(bts, 0, bufferLength)) != -1) {
                        byte[] data = new byte[length];
                        System.arraycopy(bts, 0, data, 0, length);
                        append(data);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) throws FileNotFoundException {
        final PipedInputStream pipedIS = new PipedInputStream();
        PipedOutputStream pipedOS = new PipedOutputStream();

        Thread thread = new Thread() {
            @Override
            public void run() {
                int bufferLength = 1024;
                byte[] bts = new byte[bufferLength];
                try {
                    int length;
                    while (true) {
                        if ((length = pipedIS.read(bts, 0, bufferLength)) != -1) {
                            byte[] data = new byte[length];
                            System.arraycopy(bts, 0, data, 0, length);
//                            append(data);
//                            String s = new String(data);
//                            stringBuffer.append(s);
//                            FileHelper.appendStringToFile(file, stringBuffer.toString());
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        thread.start();
        try {
            pipedOS.connect(pipedIS);
        } catch (IOException e) {
            System.err.println("连接失败");
            System.exit(1);
        }

//        PrintStream ps = new PrintStream(pipedOS);
        PrintStream ps = new PrintStream(new FileOutputStream(new File("out.txt")));
        System.setOut(ps);
        System.setErr(ps);

        Thread thread2 = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    System.out.println("呵呵" + i);
                }
            }
        };
        thread2.start();

    }


}
