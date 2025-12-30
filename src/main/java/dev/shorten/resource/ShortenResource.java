package dev.shorten.resource;

import dev.shorten.service.ShortenService;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/shorten")
public class ShortenResource
{

    @Inject
    ShortenService shortenService;

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public Response createShortenURL(String originalURL) {
        return shortenService.createShortenURL(originalURL);
    }
}
