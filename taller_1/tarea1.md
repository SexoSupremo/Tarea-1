## A. Conceptos Fundamentales
### 1.¿Qué es un servicio REST?
 Un servicio REST (Representational State Transfer) es un estilo de arquitectura de software para sistemas distribuidos, especialmente para la World Wide Web. Se basa en el protocolo HTTP y utiliza sus métodos para realizar operaciones sobre recursos.
### 2.¿Cuáles son los principios del diseño RESTful?
 Los principios del diseño RESTful son:

- Interfaz uniforme
- Sin estado
- Cacheable
- Sistema en capas
- Código bajo demanda (opcional)
- Cliente-servidor
### 3.¿Qué es HTTP y cuáles son los métodos HTTP más comunes?
 HTTP (Hypertext Transfer Protocol) es el protocolo de comunicación que permite las transferencias de información en la World Wide Web. Los métodos HTTP más comunes son:

- GET: Obtener un recurso
- POST: Crear un nuevo recurso
- PUT: Actualizar un recurso existente
- DELETE: Eliminar un recurso
- ATCH: Actualizar parcialmente un recurso
### 4.¿Qué es un recurso en el contexto de un servicio REST?
 Un recurso en REST es cualquier tipo de información que puede ser nombrada y representada. Puede ser un documento, una imagen, un servicio temporal, una colección de otros recursos, un objeto no virtual (e.g., una persona), etc.
### 5.¿Qué es un endpoint?
 Un endpoint es una URL específica en una API que representa un recurso o una colección de recursos. Es el punto final de una comunicación donde se puede acceder a los servicios de la API.

## B. Estructura de un Servicio REST
### 1.¿Qué es un URI y cómo se define?
 URI (Uniform Resource Identifier) es una cadena de caracteres que identifica un recurso de forma unívoca. Se define siguiendo un formato estándar que incluye el esquema, la autoridad, la ruta, la consulta y el fragmento.
### 2.¿Qué es una API RESTful?
 Una API RESTful es una interfaz de programación de aplicaciones que sigue los principios de la arquitectura REST. Permite la comunicación entre sistemas utilizando los métodos HTTP estándar y siguiendo las convenciones de REST.
### 3.¿Qué son los códigos de estado HTTP y cómo se usan en REST?
Los códigos de estado HTTP son números de tres dígitos que indican el resultado de una solicitud HTTP. En REST, se utilizan para informar al cliente sobre el resultado de su solicitud, ya sea éxito, error del cliente, error del servidor, etc.
### 4.Agregar una tabla con los códigos HTTP de respuesta más comunes, y su significado.
| Código | Significado                                  |
|--------|----------------------------------------------|
| 200    | OK - La solicitud se ha completado con éxito |
| 201    | Created - Se ha creado un nuevo recurso      |
| 204    | No Content - La solicitud se ha completado pero no hay contenido para enviar |
| 400    | Bad Request - La solicitud no pudo ser entendida o estaba mal formada |
| 401    | Unauthorized - Se requiere autenticación     |
| 403    | Forbidden - El servidor entendió la solicitud pero se niega a autorizarla |
| 404    | Not Found - El recurso solicitado no se encontró |
| 500    | Internal Server Error - El servidor encontró una situación inesperada |

### 5.¿Qué es JSON y por qué se usa comúnmente en APIs REST?
 JSON (JavaScript Object Notation) es un formato ligero de intercambio de datos. Se usa comúnmente en APIs REST porque es fácil de leer y escribir para los humanos, fácil de analizar y generar para las máquinas, y es independiente del lenguaje de programación.