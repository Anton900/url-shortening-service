package dev.shorten.service;

import dev.shorten.model.dto.ShortCodeResponse;
import dev.shorten.util.ShortenUtil;
import dev.shorten.util.ValidateURLUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class ShortenService
{

    private Map<String, ShortCodeResponse> shortCodeDB = new ConcurrentHashMap<>();
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
            ShortCodeResponse message = ShortCodeResponse.create(url, shortCode);

            // if null returned then insertion succeeded
            ShortCodeResponse existing = shortCodeDB.putIfAbsent(shortCode, message);
            if (existing == null)
            {
                return Response.ok(message).build();
            }
            // otherwise a collision happened, try again
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Could not generate unique short code, please try again").build();
    }

    public Response getOriginalURL(String shortCode) {
        ShortCodeResponse message = shortCodeDB.get(shortCode);
        if (message == null)
        {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Short code not found").build();
        }

        message.incrementAccessCount();

        return Response.ok(message).build();
    }

    public Response updateOriginalURL(String shortCode, String updatedUrl) {
        ShortCodeResponse message = shortCodeDB.get(shortCode);
        if (message == null)
        {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Short code not found").build();
        }

        message.setUrl(updatedUrl);
        message.incrementAccessCount();

        return Response.ok(message).build();
    }

}

