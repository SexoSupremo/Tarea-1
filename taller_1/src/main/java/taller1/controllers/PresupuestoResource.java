package taller1.controllers;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import taller1.entities.apiresponse.Gastos;
import taller1.entities.apiresponse.Presupuesto;
import taller1.model.response.ApiResponse;
import taller1.repository.PresupuestoRepository;

import java.util.ArrayList;
import java.util.List;

@Path("/presupuestos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PresupuestoResource {

    @Inject
    private PresupuestoRepository presupuestoRepository;

    @POST
    public Response crearPresupuesto(Presupuesto presupuesto) {
        try {
            
            List<Presupuesto> lista = presupuestoRepository.listar();

            int nuevoId = lista.stream()
                           .mapToInt(Presupuesto::getId)
                           .max()
                           .orElse(0) + 1;
        presupuesto.setId(nuevoId);
         if (presupuesto.getGastos() == null) {
            presupuesto.setGastos(new ArrayList<>());
        }
        presupuestoRepository.agregar(presupuesto);

            if (lista.stream().anyMatch(p -> p.getId().equals(presupuesto.getId()))) {
                return Response.status(Response.Status.CONFLICT)
                               .entity(new ApiResponse<>("Presupuesto ya existe", Response.Status.CONFLICT.getStatusCode(), null))
                               .build();
            }
               lista.add(presupuesto);
        presupuestoRepository.guardarDatos();
        return Response.status(Response.Status.CREATED)
                       .entity(new ApiResponse<>("Presupuesto creado con éxito", Response.Status.CREATED.getStatusCode(), presupuesto))
                       .build();
    } catch (Exception e) {
        e.printStackTrace();
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                       .entity(new ApiResponse<>("Error al crear el presupuesto", Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), null))
                       .build();
        }
    }

@POST
@Path("/agregar-gasto/{presupuestoId}")
public Response agregarGasto(@PathParam("presupuestoId") Integer presupuestoId, Gastos nuevoGasto) {
    System.out.println("Intentando agregar gasto al presupuesto con ID: " + presupuestoId);
        // Validar que el gasto no sea nulo y tenga valores válidos
        if (nuevoGasto == null || nuevoGasto.getMonto() == null || nuevoGasto.getMonto() <= 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity(new ApiResponse<>("Gasto inválido", Response.Status.BAD_REQUEST.getStatusCode(), null))
                           .build();
        }

        try {
            System.out.println("Buscando presupuesto con ID: " + presupuestoId);
            Presupuesto presupuesto = presupuestoRepository.obtenerById(presupuestoId);
            
            if (presupuesto == null) {
                System.out.println("Presupuesto no encontrado para ID: " + presupuestoId);
                return Response.status(Response.Status.NOT_FOUND)
                               .entity(new ApiResponse<>("Presupuesto no encontrado", Response.Status.NOT_FOUND.getStatusCode(), null))
                               .build();
            }
            
            System.out.println("Presupuesto encontrado: " + presupuesto);

            // Calcular el total de los gastos existentes
            double totalGastos = presupuesto.getGastos().stream()
                                            .mapToDouble(Gastos::getMonto)
                                            .sum();
            
            // Verificar si el nuevo gasto supera el monto presupuestado
            double montoPresupuestado = presupuesto.getMontoPresupuestado();
            if (totalGastos + nuevoGasto.getMonto() > montoPresupuestado) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity(new ApiResponse<>("El gasto supera el monto presupuestado", Response.Status.BAD_REQUEST.getStatusCode(), null))
                               .build();
            }

            // Agregar el nuevo gasto al presupuesto
            presupuesto.getGastos().add(nuevoGasto);
            presupuestoRepository.guardarDatos();

            return Response.status(Response.Status.CREATED)
                           .entity(new ApiResponse<>("Gasto agregado con éxito", Response.Status.CREATED.getStatusCode(), nuevoGasto))
                           .build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity(new ApiResponse<>("Error al agregar el gasto", Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), null))
                           .build();
        }
    }

    @GET
    public Response listarPresupuestos() {
        try {
            List<Presupuesto> lista = presupuestoRepository.listar();
            return Response.ok(new ApiResponse<>("Listado de presupuestos", Response.Status.OK.getStatusCode(), lista)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity(new ApiResponse<>("Error al listar presupuestos", Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), null))
                           .build();
        }
    }

    @GET
    @Path("/{presupuestoId}")
    public Response obtenerPresupuesto(@PathParam("presupuestoId") Integer presupuestoId) {
        try {
            Presupuesto presupuesto = presupuestoRepository.obtenerById(presupuestoId);
            if (presupuesto != null) {
                return Response.ok(new ApiResponse<>("Presupuesto encontrado", Response.Status.OK.getStatusCode(), presupuesto)).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity(new ApiResponse<>("Presupuesto no encontrado", Response.Status.NOT_FOUND.getStatusCode(), null))
                               .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity(new ApiResponse<>("Error al obtener el presupuesto", Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), null))
                           .build();
        }
    }
    @GET
    @Path("/total-gastos/{presupuestoId}")
    public Response obtenerTotalGastos(@PathParam("presupuestoId") Integer presupuestoId) {
        try {
            Presupuesto presupuesto = presupuestoRepository.obtenerById(presupuestoId);
            if (presupuesto != null) {
                double totalGastos = presupuesto.getGastos().stream()
                                                .mapToDouble(Gastos::getMonto)
                                                .sum();
                return Response.ok(new ApiResponse<>("Total de gastos", Response.Status.OK.getStatusCode(), totalGastos)).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity(new ApiResponse<>("Presupuesto no encontrado", Response.Status.NOT_FOUND.getStatusCode(), null))
                               .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity(new ApiResponse<>("Error al obtener el total de gastos", Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), null))
                           .build();
                        }
                    }           
}
