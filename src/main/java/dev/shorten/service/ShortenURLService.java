package dev.shorten.service;

import dev.shorten.model.ShortCodeMessage;
import dev.shorten.util.ShortenURLUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class ShortenURLService
{

    private Map<String, ShortCodeMessage> shortCodeDB = new ConcurrentHashMap<>();

    public Response createShortenURL(String originalURL) {
        if (originalURL == null || originalURL.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid URL").build();
        }

        String shortCode = ShortenURLUtil.createShortCode();
        ShortCodeMessage message = ShortCodeMessage.create(originalURL, shortCode);
        shortCodeDB.putIfAbsent(shortCode, message);

        return Response.ok(message).build();
    }

}

