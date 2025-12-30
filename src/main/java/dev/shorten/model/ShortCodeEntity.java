package dev.shorten.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.concurrent.atomic.AtomicLong;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShortCodeEntity
{
    private long id;
    private String url;
    private String shortCode;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private AtomicLong accessCount;

    private static final AtomicLong ID_COUNTER = new AtomicLong(0);

    public static ShortCodeEntity create(String url, String shortCode) {
        long id = ID_COUNTER.incrementAndGet();
        Timestamp now = new Timestamp(System.currentTimeMillis());
        return new ShortCodeEntity(id, url, shortCode, now, now, new AtomicLong(0));
    }

    public void incrementAccessCount() {
        this.getAccessCount().incrementAndGet();
    }

}
