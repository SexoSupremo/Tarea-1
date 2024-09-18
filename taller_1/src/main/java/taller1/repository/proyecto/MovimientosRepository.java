package taller1.repository.proyecto;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import taller1.entities.proyecto.Movimientos;

@ApplicationScoped
public class MovimientosRepository implements PanacheRepositoryBase<Movimientos, Integer> {

}