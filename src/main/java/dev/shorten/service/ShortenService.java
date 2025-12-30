package dev.shorten.service;

import dev.shorten.model.ShortCodeMessage;
import dev.shorten.util.ShortenUtil;
import dev.shorten.util.ValidateURLUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class ShortenService
{

    private Map<String, ShortCodeMessage> shortCodeDB = new ConcurrentHashMap<>();
    private static final int MAX_ATTEMPTS = 10;

    public Response createShortenURL(String originalURL) {
        if (!ValidateURLUtil.isValid(originalURL))
        {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Invalid URL provided").build();
        }

        // Try to generate a unique short code with limited attempts, to avoid infinite loops
        for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++)
        {
            String shortCode = ShortenUtil.createShortCode(5);

            // create message only when needed to avoid unnecessary allocations
            ShortCodeMessage message = ShortCodeMessage.create(originalURL, shortCode);

            // if null returned then insertion succeeded
            ShortCodeMessage existing = shortCodeDB.putIfAbsent(shortCode, message);
            if (existing == null)
            {
                return Response.ok(message).build();
            }
            // otherwise a collision happened, try again
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Could not generate unique short code, please try again").build();
    }

}

