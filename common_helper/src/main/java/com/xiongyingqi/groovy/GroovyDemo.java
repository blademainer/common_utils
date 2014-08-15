package com.xiongyingqi.groovy;

import groovy.lang.GroovyShell;

/**
 * Created by 瑛琪<a href="http://xiongyingqi.com">xiongyingqi.com</a> on 2014/8/12 0012.
 */
public class GroovyDemo {
    public static void main(String[] args){
        String shell = "static void main(String[] args){ if(args.length != 2) return;println('Hello,I am ' + args[0] + ',age ' + args[1])}";

        GroovyShell groovyShell = new GroovyShell();
        groovyShell.setVariable("args", new String[]{"熊瑛琪", "24"});
        groovyShell.evaluate(shell);
    }
}
