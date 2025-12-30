package dev.shorten.model;

import java.sql.Timestamp;
import java.util.concurrent.atomic.AtomicLong;

public record ShortCodeMessage(
    long id,
    String url,
    String shortCode,
    Timestamp createdAt,
    Timestamp updatedAt,
    AtomicLong accessCount,
    int port
) {
    private static final AtomicLong ID_COUNTER = new AtomicLong(0);

    public static ShortCodeMessage create(String url, String shortCode) {
        long id = ID_COUNTER.incrementAndGet();
        Timestamp now = new Timestamp(System.currentTimeMillis());
        return new ShortCodeMessage(id, url, shortCode, now, now, new AtomicLong(0), 0);
    }

    public static ShortCodeMessage create(String url, String shortCode, int port) {
        long id = ID_COUNTER.incrementAndGet();
        Timestamp now = new Timestamp(System.currentTimeMillis());
        return new ShortCodeMessage(id, url, shortCode, now, now, new AtomicLong(0), port);
    }

    public ShortCodeMessage {
        if (accessCount == null) {
            accessCount = new AtomicLong(0);
        }
        if (port <= 0) {
            port = determineDefaultPort();
        }
    }

    private static int determineDefaultPort() {
        String prop = System.getProperty("local.port");
        if (prop != null) {
            try { return Integer.parseInt(prop); } catch (NumberFormatException ignored) {}
        }
        String env = System.getenv("PORT");
        if (env != null) {
            try { return Integer.parseInt(env); } catch (NumberFormatException ignored) {}
        }
        return 8080;
    }

}
