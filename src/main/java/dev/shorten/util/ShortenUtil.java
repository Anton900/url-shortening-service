package dev.shorten.util;

import dev.shorten.model.ShortCodeMessage;

import java.util.Map;

public class ShortenUtil
{
    public static String createShortCode(int size) {
        // Generate a random 6-character alphanumeric code
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder shortCode = new StringBuilder();
        for (int i = 0; i < size; i++)
        {
            int index = (int) (Math.random() * characters.length());
            shortCode.append(characters.charAt(index));
        }
        return shortCode.toString();
    }

    public static boolean shortCodeAlreadyExists(String shortCode, Map<String, ShortCodeMessage> db) {
        return db.containsKey(shortCode);
    }

}
