package dev.shorten.service;

import dev.shorten.model.ShortCodeEntity;
import dev.shorten.model.dto.ShortCodeResponse;
import dev.shorten.model.dto.ShortCodeStatsResponse;
import dev.shorten.util.ShortenUtil;
import dev.shorten.util.ValidateURLUtil;
import dev.shorten.util.mapper.ShortCodeMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class ShortenService
{

    private Map<String, ShortCodeEntity> shortCodeDB = new ConcurrentHashMap<>();
    private static final int MAX_ATTEMPTS = 10;

    public Response createShortenURL(String url) {
        if (!ValidateURLUtil.isValid(url))
        {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Invalid URL provided").build();
        }

        // Try to generate a unique short code with limited attempts, to avoid infinite loops
        for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++)
        {
             String shortCode = ShortenUtil.createShortCode(5);

            // create message only when needed to avoid unnecessary allocations
            ShortCodeEntity shortCodeEntity = ShortCodeEntity.create(url, shortCode);

            // if null returned then insertion succeeded
            ShortCodeEntity existing = shortCodeDB.putIfAbsent(shortCode, shortCodeEntity);
            if (existing == null)
            {
                ShortCodeResponse response = ShortCodeMapper.toShortCodeResponse(shortCodeEntity);
                return Response.status(Response.Status.CREATED).entity(response).build();
            }
            // otherwise a collision happened, try again
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Could not generate unique short code, please try again").build();
    }

    public Response getOriginalURL(String shortCode) {
        ShortCodeEntity shortCodeEntity = shortCodeDB.get(shortCode);
        if (shortCodeEntity == null)
        {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Short code not found").build();
        }

        shortCodeEntity.incrementAccessCount();

        ShortCodeResponse response = ShortCodeMapper.toShortCodeResponse(shortCodeEntity);

        return Response.status(Response.Status.OK).entity(response).build();
    }

    public Response updateOriginalURL(String shortCode, String updatedUrl) {
        ShortCodeEntity shortCodeEntity = shortCodeDB.get(shortCode);
        if (shortCodeEntity == null)
        {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Short code not found").build();
        }

        shortCodeEntity.setUrl(updatedUrl);
        shortCodeEntity.incrementAccessCount();

        ShortCodeResponse response = ShortCodeMapper.toShortCodeResponse(shortCodeEntity);

        return Response.status(Response.Status.OK).entity(response).build();
    }

    public Response deleteShortCodeURL(String shortCode) {
        ShortCodeEntity shortCodeEntity = shortCodeDB.remove(shortCode);
        if (shortCodeEntity == null)
        {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Short code not found").build();
        }

        return Response.status(Response.Status.NO_CONTENT).build();
    }

    public Response getShortCodeStats(String shortCode) {
        ShortCodeEntity shortCodeEntity = shortCodeDB.get(shortCode);
        if (shortCodeEntity == null)
        {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Short code not found").build();
        }

        shortCodeEntity.incrementAccessCount();
        ShortCodeStatsResponse response = ShortCodeMapper.toShortCodeStatsResponse(shortCodeEntity);

        return Response.status(Response.Status.OK).entity(response).build();
    }

}

