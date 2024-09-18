package taller1.services.proyecto;


import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import taller1.config.GenericDAO;
import taller1.entities.proyecto.Movimientos;
import taller1.repository.proyecto.MovimientosRepository;

@ApplicationScoped
public class MovimientosService implements GenericDAO<Movimientos, Integer> {

    private final MovimientosRepository repository;

    public MovimientosService(MovimientosRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Movimientos> listar() {
        return this.repository.findAll().list();
    }

    @Override
    public Movimientos obtener(Integer id) {
        return this.repository.findById(id);
    }
 
    @Override
    @Transactional
    public void eliminar(Integer id) {
        this.repository.deleteById(id);
    }

    @Override
    @Transactional
    public Movimientos modificar(Movimientos param) {

        return this.repository.getEntityManager().merge(param);
    }

    @Override
    @Transactional
    public Movimientos agregar(Movimientos param) {
        this.repository.persist(param);
        return null;
    }

   
}
