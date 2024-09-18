package taller1.repository.proyecto;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import taller1.entities.proyecto.PresupuestoMensual;

@ApplicationScoped
public class PresupuestoRepository implements PanacheRepositoryBase<PresupuestoMensual, Integer> {

}
