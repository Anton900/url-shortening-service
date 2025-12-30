package dev.shorten.resource;

import dev.shorten.service.ShortenURLService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/shorten")
public class ShortenURLResource {

    @Inject
    ShortenURLService shortenURLService;

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public Response createShortenURL(String originalURL) {
        return shortenURLService.createShortenURL(originalURL);
    }
}
