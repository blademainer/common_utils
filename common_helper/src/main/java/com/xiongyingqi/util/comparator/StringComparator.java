package com.xiongyingqi.util.comparator;

import java.util.Comparator;


public class StringComparator implements Comparator<String> {
    
    @Override
    public int compare(String o1, String o2) {
        if (o1 == null || o2 == null) {
            throw new NullPointerException();
        }

        if (o1.getClass() != o2.getClass()) {
            throw new ClassCastException();
        }

        if (o1.length() == o2.length()) {
            if (o1.hashCode() < o2.hashCode()) {
                return -1;
            } else if (o1.hashCode() > o2.hashCode()) {
                return 1;
            }
            if (o1.hashCode() == o2.hashCode()) {
                return 0;
            }
        }
        char[] chars1 = o1.toCharArray();
        char[] chars2 = o2.toCharArray();
        int c = 0;

        int minLength = (chars1.length < chars2.length) ? chars1.length : chars2.length;
        for (int i = 0; i < minLength; i++) {
            if (chars1[i] != chars2[i]) {
                if (chars1[i] < chars2[i]) {
                    c = -1;
                } else {
                    c = 1;
                }
                break;
            }
        }

        if (c == 0) {
            c = (chars1.length < chars2.length) ? -1 : 1;
        }
        return c;
    }

    
    @Override
    public boolean equals(Object obj) {
        return false;
    }
}
