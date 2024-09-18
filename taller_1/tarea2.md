# Sección 1: Introducción a Servicios en Quarkus

## ¿Que es @ApplicationScoped en Quarkus?
@ApplicationScoped en Quarkus:
Es una anotación que indica que una instancia de la clase se crea por aplicación y se comparte entre todas las solicitudes

## ¿Cómo funciona la inyección de dependencias en Quarkus?
Inyección de dependencias en Quarkus:
Quarkus utiliza CDI (Contexts and Dependency Injection) para manejar la inyección de dependencias. Permite inyectar beans gestionados en otros beans usando anotaciones como @Inject.


## ¿Cuál es la diferencia entre @ApplicationScoped, @RequestScoped, y @Singleton en Quarkus?
Diferencia entre @ApplicationScoped, @RequestScoped, y @Singleton:


 - @ApplicationScoped: Una instancia por aplicación, compartida entre solicitudes.
 - @RequestScoped: Nueva instancia para cada solicitud HTTP.
 - @Singleton: Una única instancia durante toda la vida de la aplicación.

## ¿Cómo se define un servicio en Quarkus utilizando @ApplicationScoped?

- Crea una clase Java.
- Anotar la clase con @ApplicationScoped.
- Definir los métodos del servicio dentro de la clase.


import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SaludoService {

    public String saludar(String nombre) {
        return "Hola, " + nombre + "!";
    }

    public String despedir(String nombre) {
        return "Adiós, " + nombre + "!";
    }
}

## ¿Por qué es importante manejar correctamente los alcances (scopes) en Quarkus al crear servicios?

El manejo adecuado de los alcances asegura el uso eficiente de recursos, evita problemas de concurrencia y garantiza el comportamiento correcto de la aplicación en diferentes contextos.

# Seccion 2: Creación de un ApiResponse Genérico

## ¿Qué es un ApiResponse genérico y cuál es su propósito en un servicio REST?
Es una estructura estandarizada para encapsular respuestas de API REST. Su propósito es proporcionar consistencia en el formato de respuesta, incluyendo datos, mensajes de estado y códigos de error.

## ¿Cómo se implementa una clase ApiResponse genérica en Quarkus?
    public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;

    public ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    // Getters
    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    // Setters
    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(T data) {
        this.data = data;
    }
}


La clase se define como genérica con <T>, permitiendo flexibilidad en el tipo de datos que puede contener.
Tiene tres campos principales:

- success: un booleano que indica si la operación fue exitosa.
- message: un String que proporciona información adicional sobre la respuesta.
- data: de tipo T, que contiene los datos de la respuesta.


Se incluye un constructor que inicializa todos los campos.
Se proporcionan getters y setters para cada campo, permitiendo acceso y modificación.

## ¿Cómo se modifica un recurso REST en Quarkus para que utilice un ApiResponse genérico?
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/productos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductoResource {

    @Inject
    ProductoService productoService;

    @GET
    @Path("/{id}")
    public Response obtenerProducto(@PathParam("id") Long id) {
        Producto producto = productoService.obtenerProductoPorId(id);
        if (producto != null) {
            ApiResponse<Producto> response = new ApiResponse<>(true, "Producto encontrado", producto);
            return Response.ok(response).build();
        } else {
            ApiResponse<Void> response = new ApiResponse<>(false, "Producto no encontrado", null);
            return Response.status(Response.Status.NOT_FOUND).entity(response).build();
        }
    }

    @POST
    public Response crearProducto(Producto producto) {
        try {
            Producto nuevoProducto = productoService.crearProducto(producto);
            ApiResponse<Producto> response = new ApiResponse<>(true, "Producto creado con éxito", nuevoProducto);
            return Response.status(Response.Status.CREATED).entity(response).build();
        } catch (Exception e) {
            ApiResponse<Void> response = new ApiResponse<>(false, "Error al crear el producto: " + e.getMessage(), null);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(response).build();
        }
    }
}
Cada método ahora devuelve un objeto Response en lugar del objeto de dominio directamente.
Se utiliza ApiResponse para encapsular la respuesta:

Para respuestas exitosas, se crea un ApiResponse con success = true, un mensaje apropiado y los datos.
Para errores, se crea un ApiResponse con success = false, un mensaje de error y datos nulos.


Se usa Response.ok(), Response.status(), etc., para construir la respuesta HTTP adecuada.
El tipo genérico de ApiResponse se ajusta según el contenido: ApiResponse<Producto> para un producto único, ApiResponse<Void> para respuestas sin datos.
Los códigos de estado HTTP se establecen explícitamente (por ejemplo, NOT_FOUND, CREATED, INTERNAL_SERVER_ERROR).

## ¿Qué beneficios ofrece el uso de un ApiResponse genérico en términos de mantenimiento y consistencia de código?
- Consistencia en la estructura de respuestas.
- Facilita el manejo de errores y éxitos de manera uniforme.
- Mejora la mantenibilidad del código.
- Simplifica la documentación de la API.
## ¿Cómo manejarías diferentes tipos de respuestas (éxito, error, etc.) utilizando la clase ApiResponse?

public static <T> ApiResponse<T> success(T data) {
    return new ApiResponse<>(true, "Operación exitosa", data);
}

public static <T> ApiResponse<T> error(String message) {
    return new ApiResponse<>(false, message, null);
}

# Sección 3: Integración y Buenas Prácticas
## ¿Qué consideraciones se deben tener al inyectar servicios en un recurso REST en Quarkus?
Al inyectar servicios en un recurso REST en Quarkus, hay varias consideraciones importantes a tener en cuenta:

Alcance (Scope) de los servicios:

usar el alcance apropiado para tus servicios (por ejemplo, @ApplicationScoped, @RequestScoped).
El alcance afecta el ciclo de vida y la concurrencia del servicio.


Inyección de dependencias:

Utilizar la anotación @Inject para inyectar servicios en tu recurso REST.
Considerar usar constructor injection en lugar de field injection para mejorar la testabilidad.


Transacciones:

Si los servicios manejan transacciones, asegúrate de que estén correctamente configurados y gestionados.


Manejo de excepciones:

Implementar un manejo adecuado de excepciones tanto en el recurso como en los servicios inyectados.


Configuración:

Utilizar la inyección de configuración de Quarkus para inyectar valores de configuración en tus servicios.


Lazy loading:

Considerar usar @Lazy para servicios pesados que no siempre se necesitan.


Interceptores:

Puedes usar interceptores para agregar comportamiento transversal a tus servicios.


Testing:

Diseñar servicios y recursos de manera que sean fácilmente testeables, posiblemente usando interfaces para facilitar el mocking.
## ¿Cómo se pueden manejar excepciones en un servicio REST utilizando ApiResponse?
Primero, definiría mis propias excepciones personalizadas. Por ejemplo:
public class RecursoNoEncontradoException extends RuntimeException {
    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
Luego, crearía un ExceptionMapper para cada tipo de excepción:
@Provider
public class RecursoNoEncontradoExceptionMapper implements ExceptionMapper<RecursoNoEncontradoException> {
    @Override
    public Response toResponse(RecursoNoEncontradoException exception) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity(new ApiResponse(false, exception.getMessage()))
                .build();
    }
}
Después, definiría una clase ApiResponse para estandarizar mis respuestas:
    public class ApiResponse {
    private boolean exito;
    private String mensaje;

    // Constructor, getters y setters
}
En mi recurso REST, usaría ApiResponse y lanzaría excepciones cuando fuera necesario:
@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    @GET
    @Path("/{id}")
    public Response obtenerUsuario(@PathParam("id") Long id) {
        Usuario usuario = servicioUsuario.buscarPorId(id);
        if (usuario == null) {
            throw new RecursoNoEncontradoException("Usuario no encontrado con id: " + id);
        }
        return Response.ok(new ApiResponse(true, "Usuario encontrado")).entity(usuario).build();
    }

    // Etc..
}
Este enfoque me permitiría manejar las excepciones de forma centralizada, proporcionar respuestas consistentes y separar la lógica de manejo de errores del código principal.