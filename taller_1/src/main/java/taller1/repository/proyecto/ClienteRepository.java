package taller1.repository.proyecto;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import taller1.entities.proyecto.Cliente;

@ApplicationScoped
public class ClienteRepository implements PanacheRepositoryBase<Cliente, Integer> {

}
