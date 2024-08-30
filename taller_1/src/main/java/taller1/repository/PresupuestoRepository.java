package taller1.repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.enterprise.context.ApplicationScoped;
import taller1.entities.apiresponse.Presupuesto;
import taller1.entities.apiresponse.Gastos;

@ApplicationScoped
public class PresupuestoRepository {

    private static final String FILE_PATH = "src/main/resources/data/presupuestos.json";
    private List<Presupuesto> presupuestosList;
    private ObjectMapper objectMapper;

    public PresupuestoRepository() {
        objectMapper = new ObjectMapper();
        presupuestosList = cargarDatos();
    }

    private List<Presupuesto> cargarDatos() {
        try {
            File file = new File(FILE_PATH);
            if (file.exists()) {
                return objectMapper.readValue(file, new TypeReference<List<Presupuesto>>() {});
            } else {
                return new ArrayList<>();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private void guardarDatos() {
        try {
            objectMapper.writeValue(new File(FILE_PATH), presupuestosList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Presupuesto obtenerById(Integer id) {
        return presupuestosList.stream()
                .filter(presupuesto -> presupuesto.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Presupuesto> listar() {
        return new ArrayList<>(presupuestosList);
    }

    public void agregarGasto(Integer presupuestoId, Gastos gasto) {
        Optional<Presupuesto> presupuestoOptional = presupuestosList.stream()
                .filter(presupuesto -> presupuesto.getId().equals(presupuestoId))
                .findFirst();

        if (presupuestoOptional.isPresent()) {
            Presupuesto presupuesto = presupuestoOptional.get();
            if (presupuesto.getGastos() == null) {
                presupuesto.setGastos(new ArrayList<>());
            }
            presupuesto.getGastos().add(gasto);
            guardarDatos();
        }
    }
}
