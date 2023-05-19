package com.pe.EcoPunto.Util;
import java.util.Random;

public class RandomText {
    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            sb.append(randomChar);
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        int length = 10;
        String randomString = generateRandomString(length);
        System.out.println(randomString);
    }
}
