package com.adilzhan.firsttask.util;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;

public class UsernamePasswordGenerator {
    private static final String CHAR_POOL = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom random = new SecureRandom();

    public static String generateUsername(String firstName, String lastName, List<String> existingUsernames) {
        String base = firstName + "." + lastName;
        String username = base;
        int counter = 1;

        while (existingUsernames.contains(username)) {
            username = base + "." + counter;
            counter++;
        }
        return username;
    }

    public static String generatePassword(int length) {
        return random.ints(length, 0, CHAR_POOL.length())
                .mapToObj(CHAR_POOL::charAt)
                .map(String::valueOf)
                .collect(Collectors.joining());
    }

    public static String generatePassword() {
        return generatePassword(10);
    }
}
