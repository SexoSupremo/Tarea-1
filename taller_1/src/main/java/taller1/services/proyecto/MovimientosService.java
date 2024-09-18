package taller1.services.proyecto;


import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import taller1.config.GenericDAO;
import taller1.entities.proyecto.Cliente;
import taller1.repository.proyecto.ClienteRepository;

@ApplicationScoped
public class MovimientosService implements GenericDAO<Cliente, Integer> {

    private final ClienteRepository repository;

    public MovimientosService(ClienteRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Cliente> listar() {
        return this.repository.findAll().list();
    }

    @Override
    public Cliente obtener(Integer id) {
        return this.repository.findById(id);
    }

    @Override
    @Transactional
    public void eliminar(Integer id) {
        this.repository.deleteById(id);
    }

    @Override
    @Transactional
    public Cliente modificar(Cliente param) {

        return this.repository.getEntityManager().merge(param);
    }

    @Override
    @Transactional
    public Cliente agregar(Cliente param) {
        this.repository.persist(param);
        return null;
    }

