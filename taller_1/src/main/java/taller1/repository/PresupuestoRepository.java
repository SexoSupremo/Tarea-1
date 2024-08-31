package taller1.repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
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
        if (presupuestosList.isEmpty()) {
            System.out.println("No se cargaron datos de presupuestos.");
        } else {
            System.out.println("Se cargaron " + presupuestosList.size() + " presupuestos.");
        }
    }

private List<Presupuesto> cargarDatos() {
    try {
        File file = new File(FILE_PATH);
        if (file.exists()) {
            JsonNode rootNode = objectMapper.readTree(file);
            if (rootNode.isArray()) {
                return objectMapper.readValue(file, new TypeReference<List<Presupuesto>>() {});
            } else if (rootNode.isObject()) {
                Presupuesto presupuesto = objectMapper.readValue(file, Presupuesto.class);
                List<Presupuesto> lista = new ArrayList<>();
                lista.add(presupuesto);
                return lista;
            } else {
                System.out.println("El archivo JSON no contiene ni un objeto ni un array.");
                return new ArrayList<>();
            }
        } else {
            System.out.println("El archivo " + FILE_PATH + " no existe.");
            return new ArrayList<>();
        }
    } catch (IOException e) {
        System.err.println("Error al cargar presupuestos: " + e.getMessage());
        e.printStackTrace();
        return new ArrayList<>();
    }
}

    public void guardarDatos() {
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
        System.out.println("Listando presupuestos. Número de presupuestos: " + presupuestosList.size());
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
