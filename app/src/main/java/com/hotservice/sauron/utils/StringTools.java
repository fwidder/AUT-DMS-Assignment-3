package com.hotservice.sauron.utils;

import java.util.Random;

/**
 * Personal String Utils
 */
public class StringTools {

    /**
     * Creates a random Alphanumeric String
     *
     * @param length length of the String
     * @return a random String where (getRandomString(length).length()==length) is true
     */
    public static String getRandomString(int length) {
        String SALTCHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < length) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();
    }
}
