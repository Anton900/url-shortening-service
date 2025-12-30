package dev.shorten.model.dto;

import java.sql.Timestamp;
import java.util.concurrent.atomic.AtomicLong;

public record ShortCodeStatsResponse(
        long id,
        String url,
        String shortCode,
        Timestamp createdAt,
        Timestamp updatedAt,
        AtomicLong accessCount
) {}
