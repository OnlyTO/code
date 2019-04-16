package com.example.cc.code_container;

import java.util.Comparator;

public class PinyinComparator implements Comparator<Code> {

    public int compare(Code c1, Code c2) {
        if (c1.getLetter().equals("@")
                || c2.getLetter().equals("#")) {
            return -1;
        } else if (c1.getLetter().equals("#")
                || c2.getLetter().equals("@")) {
            return 1;
        } else {
            return c1.getLetter().compareTo(c2.getLetter());
        }
    }

}