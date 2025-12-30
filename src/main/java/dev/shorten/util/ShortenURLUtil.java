package dev.shorten.util;

public class ShortenURLUtil
{
    public static String createShortCode() {
        // Generate a random 6-character alphanumeric code
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder shortCode = new StringBuilder();
        for (int i = 0; i < 6; i++)
        {
            int index = (int) (Math.random() * characters.length());
            shortCode.append(characters.charAt(index));
        }
        return shortCode.toString();
    }

}
