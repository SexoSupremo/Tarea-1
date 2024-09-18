package taller1.controllers.proyecto;


import taller1.entities.proyecto.PresupuestoMensual;
import taller1.entities.proyecto.Cliente;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/final")
public class FinalResource {

    @Inject
    EntityManager em;

    @GET
    @Path("/presupuestos/count")
    @Produces(MediaType.TEXT_PLAIN)
    public Long countPresupuestos() {
        return em.createQuery("SELECT COUNT(pm) FROM PresupuestoMensual pm", Long.class)
                 .getSingleResult();
    }

    @GET
    @Path("/presupuestos-mensual/{rangoInicial}/{rangoFinal}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response filtrarPresupuestosPorRango(
            @PathParam("rangoInicial") double rangoInicial,
            @PathParam("rangoFinal") double rangoFinal) {
        try {
            List<PresupuestoMensual> presupuestos = em.createQuery(
                "SELECT pm FROM PresupuestoMensual pm WHERE pm.montoPresupuestado BETWEEN :rangoInicial AND :rangoFinal", 
                PresupuestoMensual.class)
                .setParameter("rangoInicial", rangoInicial)
                .setParameter("rangoFinal", rangoFinal)
                .getResultList();

            if (presupuestos.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                    .entity("No se encontraron presupuestos en el rango especificado.")
                    .build();
            }

            return Response.ok(presupuestos).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Error al procesar la solicitud: " + e.getMessage())
                .build();
        }
    }

    @GET
    @Path("/presupuestos-mensual/mayor-presupuesto")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerPresupuestoMasAlto() {
        try {
            Double maxMonto = em.createQuery("SELECT MAX(pm.montoPresupuestado) FROM PresupuestoMensual pm", Double.class)
                                .getSingleResult();
            
            if (maxMonto == null) {
                return Response.status(Response.Status.NOT_FOUND)
                    .entity("No se encontraron presupuestos.")
                    .build();
            }

            List<PresupuestoMensual> presupuestos = em.createQuery(
                "SELECT pm FROM PresupuestoMensual pm WHERE pm.montoPresupuestado = :maxMonto", 
                PresupuestoMensual.class)
                .setParameter("maxMonto", maxMonto)
                .getResultList();

            return Response.ok(presupuestos).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Error al procesar la solicitud: " + e.getMessage())
                .build();
        }
    }

    @GET
    @Path("/clientes/buscar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarClientes(
            @QueryParam("nombres") String nombres,
            @QueryParam("apellidos") String apellidos) {
        try {
            String jpql = "SELECT c FROM Cliente c WHERE 1=1";
            if (nombres != null && !nombres.isEmpty()) {
                jpql += " AND LOWER(c.nombres) LIKE LOWER(:nombres)";
            }
            if (apellidos != null && !apellidos.isEmpty()) {
                jpql += " AND LOWER(c.apellidos) LIKE LOWER(:apellidos)";
            }

            jakarta.persistence.TypedQuery<Cliente> query = em.createQuery(jpql, Cliente.class);

            if (nombres != null && !nombres.isEmpty()) {
                query.setParameter("nombres", "%" + nombres + "%");
            }
            if (apellidos != null && !apellidos.isEmpty()) {
                query.setParameter("apellidos", "%" + apellidos + "%");
            }

            List<Cliente> clientes = query.getResultList();

            if (clientes.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                    .entity("No se encontraron clientes con los criterios especificados.")
                    .build();
            }

            return Response.ok(clientes).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Error al procesar la solicitud: " + e.getMessage())
                .build();
        }
    }
}
