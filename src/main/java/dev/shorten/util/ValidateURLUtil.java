package dev.shorten.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Pattern;

public class ValidateURLUtil
{
    private static final Pattern HOSTNAME_PATTERN = Pattern.compile(
        "^([a-zA-Z0-9]([a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?\\.)*[a-zA-Z0-9]([a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?$"
    );

    private static final Pattern IPV4_PATTERN = Pattern.compile(
        "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$"
    );

    private static final Pattern IPV6_PATTERN = Pattern.compile(
        "^\\[([0-9a-fA-F:]+)\\]$"
    );

    /**
     * Validates if the input string is a valid HTTP or HTTPS URL.
     *
     * @param url the URL string to validate
     * @return ValidationResult containing validity status and optional error message
     */
    public static boolean isValid(String url) {
        if (url == null || url.trim().isEmpty()) {
            return false;
        }

        URI uri;
        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            return false;
        }

        String scheme = uri.getScheme();
        if (scheme == null || (!scheme.equalsIgnoreCase("http") && !scheme.equalsIgnoreCase("https"))) {
            return false;
        }

        String host = uri.getHost();
        if (host == null || host.isEmpty()) {
            return false;
        }

        // Validate hostname
        if (!isValidHost(host)) {
            return false;
        }

        // Validate port if present
        int port = uri.getPort();
        if (port != -1 && (port < 1 || port > 65535)) {
            return false;
        }

        return true;
    }

    private static boolean isValidHost(String host) {
        if (host.equalsIgnoreCase("localhost")) {
            return true;
        }

        // Check if it's an IPv4 address
        if (IPV4_PATTERN.matcher(host).matches()) {
            return true;
        }

        // Check if it's an IPv6 address (enclosed in brackets)
        if (IPV6_PATTERN.matcher(host).matches()) {
            return true;
        }

        // Validate as hostname (domain name)
        if (host.length() > 255) {
            return false;
        }

        return HOSTNAME_PATTERN.matcher(host).matches();
    }
}
