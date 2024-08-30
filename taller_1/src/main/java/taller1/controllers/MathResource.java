package taller1.controllers;

import java.util.HashMap;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import taller1.entities.HolaMundo;

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

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public HashMap<String, Object> respuestaPost(HashMap<String, Object> param) {
        System.out.println(param.get("Supremo"));
        return param;
    }
    
    @POST
    @Path("hola")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public HolaMundo holaMundo(HolaMundo param) {
        System.out.println(param);
        return param;
    }
}
