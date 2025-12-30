package dev.shorten.resource;

import dev.shorten.model.dto.CreateShortenRequest;
import dev.shorten.service.ShortenService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/shorten")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ShortenResource
{

    @Inject
    ShortenService shortenService;

    @POST
    public Response createShortenURL(CreateShortenRequest request) {
        String url = request.url();
        return shortenService.createShortenURL(url);
    }

    @PUT
    @Path("/{shortCode}")
    public Response updateShortenURL(@PathParam("shortCode") String shortCode, CreateShortenRequest request) {
        String url = request.url();
        return shortenService.updateOriginalURL(shortCode, url);
    }

    @GET
    @Path("/{shortCode}")
    public Response getOriginalURL(@PathParam("shortCode") String shortCode) {
        return shortenService.getOriginalURL(shortCode);
    }

    @GET
    @Path("/test")
    public Response test() {
        return Response.ok("hi").build();
    }
}
