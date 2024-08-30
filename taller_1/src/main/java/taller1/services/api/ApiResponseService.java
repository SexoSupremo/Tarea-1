package taller1.services.api;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ApiResponseService {
    public String responseDummy() {
        return "retorna desde el servicio";
    }
}
