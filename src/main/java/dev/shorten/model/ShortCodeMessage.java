package dev.shorten.model;

import java.sql.Timestamp;
import java.util.concurrent.atomic.AtomicLong;

public record ShortCodeMessage(
    long id,
    String url,
    String shortCode,
    Timestamp createdAt,
    Timestamp updatedAt,
    AtomicLong accessCount
) {
    private static final AtomicLong ID_COUNTER = new AtomicLong(0);

    public static ShortCodeMessage create(String url, String shortCode) {
        long id = ID_COUNTER.incrementAndGet();
        Timestamp now = new Timestamp(System.currentTimeMillis());
        return new ShortCodeMessage(id, url, shortCode, now, now, new AtomicLong(0));
    }

}
