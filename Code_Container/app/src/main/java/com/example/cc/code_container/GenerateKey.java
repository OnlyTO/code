package com.example.cc.code_container;

import java.util.Random;

public class GenerateKey {
    private static final String res = "abcdefghigklmnopqrstuvwxyz" +
            "ABCDEFGHIGKLMNOPQRSTUVWXYZ" +
            "~@#$%^&*()_+|`.," +
            "1234567890";
    public static String getKey() {
        StringBuilder stringBuilder = new StringBuilder();

        Random random = new Random();
        for (int i = 0; i<8; i++) {
            int cursor = random.nextInt(res.length());
            stringBuilder.append(res.charAt(cursor));
        }

        return stringBuilder.toString();

    }


}
