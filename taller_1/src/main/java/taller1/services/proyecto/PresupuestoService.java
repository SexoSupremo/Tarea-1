package taller1.services.proyecto;

import java.util.List;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.persistence.EntityManager;
import jakarta.inject.Inject;
import taller1.config.GenericDAO;
import taller1.entities.proyecto.PresupuestoMensual;
import taller1.repository.proyecto.PresupuestoRepository;

@ApplicationScoped
public class PresupuestoService implements GenericDAO<PresupuestoMensual, Integer> {

    private final PresupuestoRepository repository;
    
    @Inject
    EntityManager em;

    public PresupuestoService(PresupuestoRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<PresupuestoMensual> listar() {
        return this.repository.findAll().list();
    }

    @Override
    public PresupuestoMensual obtener(Integer id) {
        return this.repository.findById(id);
    }

    @Override
    @Transactional
    public void eliminar(Integer id) {
        this.repository.deleteById(id);
    }

    @Override
    @Transactional
    public PresupuestoMensual modificar(PresupuestoMensual param) {
        return this.repository.getEntityManager().merge(param);
    }

    @Override
    @Transactional
    public PresupuestoMensual agregar(PresupuestoMensual param) {
        this.repository.persist(param);
        return param;
    }

    public Long contarPresupuestos() {
        return em.createQuery("SELECT COUNT(pm) FROM PresupuestoMensual pm", Long.class)
                 .getSingleResult();
    }

    public List<PresupuestoMensual> filtrarPorRango(double rangoInicial, double rangoFinal) {
        return em.createQuery(
            "SELECT pm FROM PresupuestoMensual pm WHERE pm.montoPresupuestado BETWEEN :rangoInicial AND :rangoFinal", 
            PresupuestoMensual.class)
            .setParameter("rangoInicial", rangoInicial)
            .setParameter("rangoFinal", rangoFinal)
            .getResultList();
    }

    public List<PresupuestoMensual> obtenerPresupuestoMasAlto() {
        Double maxMonto = em.createQuery("SELECT MAX(pm.montoPresupuestado) FROM PresupuestoMensual pm", Double.class)
                            .getSingleResult();
        
        if (maxMonto == null) {
            return List.of();
        }

        return em.createQuery(
            "SELECT pm FROM PresupuestoMensual pm WHERE pm.montoPresupuestado = :maxMonto", 
            PresupuestoMensual.class)
            .setParameter("maxMonto", maxMonto)
            .getResultList();
    }
}