package dev.shorten.util.mapper;

import dev.shorten.model.ShortCodeEntity;
import dev.shorten.model.dto.ShortCodeResponse;
import dev.shorten.model.dto.ShortCodeStatsResponse;

public class ShortCodeMapper
{

    public static ShortCodeResponse toShortCodeResponse(ShortCodeEntity shortCodeEntity)
    {
        return new ShortCodeResponse(
                shortCodeEntity.getId(),
                shortCodeEntity.getUrl(),
                shortCodeEntity.getShortCode(),
                shortCodeEntity.getCreatedAt(),
                shortCodeEntity.getUpdatedAt()
        );
    }

    public static ShortCodeStatsResponse toShortCodeStatsResponse(ShortCodeEntity shortCodeEntity)
    {
        return new ShortCodeStatsResponse(
                shortCodeEntity.getId(),
                shortCodeEntity.getUrl(),
                shortCodeEntity.getShortCode(),
                shortCodeEntity.getCreatedAt(),
                shortCodeEntity.getUpdatedAt(),
                shortCodeEntity.getAccessCount()
        );
    }
}
