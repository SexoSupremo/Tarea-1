package taller1.controllers;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/math")
public class MathResource {

    @GET
    @Path("/suma")
    @Produces(MediaType.TEXT_PLAIN)
    public int suma(@QueryParam("a") int a, @QueryParam("b") int b) {
        return a + b;
    }

    @GET
    @Path("/multiplicacion")
    @Produces(MediaType.TEXT_PLAIN)
    public int multiplicacion(@QueryParam("a") int a, @QueryParam("b") int b) {
        return a * b;
    }

    @GET
    @Path("/resta")
    @Produces(MediaType.TEXT_PLAIN)
    public int resta(@QueryParam("a") int a, @QueryParam("b") int b) {
        return a - b;
    }

    @GET
    @Path("/division")
    @Produces(MediaType.TEXT_PLAIN)
    public double division(@QueryParam("a") double a, @QueryParam("b") double b) {
        if (b == 0) {
            throw new IllegalArgumentException("el cero no existe");
        }
        return a / b;
    }
}
