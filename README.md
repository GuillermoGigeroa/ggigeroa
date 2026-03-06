# Sistema de Impresora - Leandro

Sistema Spring Boot para manejo de impresoras con ejemplos de patrones y utilidades.

## Características

- Spring Boot 3.5.10
- Java 17
- MySQL (Opcional - inicia sin BD disponible)
- HTTP Basic Auth
- Swagger/OpenAPI
- Logging con SLF4J

## Estructura del Proyecto

```
ggigeroa/
├── src/main/java/ggigeroa/impresora/runner/
│   ├── Application.java
│   ├── controller/
│   │   ├── AppController.java (MVC)
│   │   ├── AppRestController.java (REST API)
│   │   └── AppAdvancedController.java (REST API v1)
│   ├── service/
│   │   └── AppService.java (Lógica de negocio)
│   ├── utils/
│   │   ├── AppLogger.java (Logger personalizado)
│   │   └── AppUtils.java (Utilidades)
│   ├── Imagen.java (Entity)
│   ├── Registro.java (Entity)
│   ├── ImagenRepository.java (JPA)
│   ├── RegistroRepository.java (JPA)
│   ├── SecurityConfig.java
│   ├── SwaggerConfig.java
│   └── DatabaseHealthCheck.java
├── src/main/resources/
│   └── application.properties
├── EJEMPLOS.md (Ejemplos de uso)
├── ENDPOINTS.md (Documentación de endpoints)
├── agent.md (Contexto del proyecto)
└── pom.xml
```

## Inicio Rápido

### Compilar

```bash
mvnw.cmd clean package -DskipTests
```

### Ejecutar

```bash
mvnw.cmd spring-boot:run
```

O con JAR:
```bash
java -jar target/impresora.ggigeroa-0.0.1-SNAPSHOT.jar
```

## Acceso a Endpoints

Todos los endpoints requieren autenticación HTTP Basic:
- Usuario: `leandro`
- Contraseña: `impresora123`

### Health Check
```bash
curl -u leandro:impresora123 http://localhost:8080/api/
```

### Ver Configuración
```bash
curl -u leandro:impresora123 http://localhost:8080/admin/configuracion
```

### Swagger UI
```
http://localhost:8080/swagger-ui.html
```

## Lectura Recomendada

1. **README.md** ← Estás aquí
2. **EJEMPLOS.md** - Cómo usar las utilidades
3. **ENDPOINTS.md** - Referencia de API completa

## Documentación

- **EJEMPLOS.md** - Ejemplos de uso de Spring Boot y utilidades
- **ENDPOINTS.md** - Documentación completa de endpoints
- **agent.md** - Contexto técnico del proyecto

## Base de Datos

La aplicación está configurada para MySQL pero puede iniciar sin BD disponible.

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/impresora_db
spring.datasource.username=root
spring.datasource.password=
```

Si la BD no está disponible:
1. Se logea: "No se pudo detectar base de datos..."
2. Se deshabilita JPA automáticamente
3. La aplicación continúa funcionando sin persistencia

## Utilidades Disponibles

### AppLogger
Logger personalizado que wrappea SLF4J.
```java
private final AppLogger log = new AppLogger(MiClase.class);
log.operacionIniciada("operacion");
log.operacionCompletada("operacion");
```

### AppUtils
Utilidades estáticas globales.
```java
AppUtils.generarIdUnico();
AppUtils.crearRespuestaExitosa(mensaje, datos);
AppUtils.procesarEnThread(nombre, runnable);
```

### AppService
Servicio de negocio inyectable.
```java
@Autowired
private AppService appService;
appService.procesarInformacion(datos);
```

## Controladores

### AppController (MVC)
- `/admin/info` - Información
- `/admin/procesar` - Procesar en background
- `/admin/health` - Health check
- `/admin/configuracion` - Configuración

### AppRestController (REST)
- `GET /api/{path}/{id}` - Obtener
- `POST /api/{path}/{id}` - Procesar
- `PUT /api/{path}/{id}` - Actualizar
- `DELETE /api/{path}/{id}` - Eliminar

### AppAdvancedController (REST v1)
- `POST /api/v1/procesar` - Procesar con servicio
- `GET /api/v1/estadisticas/{tipo}` - Estadísticas
- `POST /api/v1/tarea-larga` - Tarea larga
- `GET /api/v1/echo` - Echo

## Seguridad

HTTP Basic Auth con credenciales hardcodeadas (desarrollo).

Para producción, usar OAuth2/JWT y variables de entorno.

## Características de Spring Boot

El proyecto incluye ejemplos de:

- **@RestController** - Controladores REST
- **@Service** - Servicios anotados
- **@Autowired** - Inyección de dependencias
- **@Value** - Inyección de propiedades
- **@RequestMapping/@GetMapping/@PostMapping** - Mapeo de rutas
- **@PathVariable/@RequestParam/@RequestBody** - Parámetros
- **ResponseEntity** - Control de respuestas HTTP
- **@ExceptionHandler** - Manejo de excepciones
- **Environment** - Acceso a propiedades
- **Spring Data JPA** - Persistencia ORM
- **Conditional Beans** - Beans condicionales
- **ApplicationContextInitializer** - Inicializadores
- **System.setProperty** - Propiedades dinámicas

## Logging

Todos los componentes usan SLF4J a través de AppLogger.

Niveles configurados:
- root: INFO
- ggigeroa.impresora.runner: DEBUG
- org.springframework.security: DEBUG

## Base de Datos (Opcional)

Entidades JPA:
- `Imagen` - almacena imágenes en base64
- `Registro` - almacena registros genéricos

Repositorios:
- `ImagenRepository` - extends JpaRepository<Imagen, Long>
- `RegistroRepository` - extends JpaRepository<Registro, Long>

## Notas Importantes

1. **Sin Lombok** - Constructores y getters/setters manuales
2. **Inicia sin BD** - JPA se deshabilita automáticamente si falla la conexión
3. **Responses estándar** - Todas las respuestas JSON siguen un patrón consistente
4. **Threads** - Las tareas largas se procesan en threads separados
5. **Logging** - Usar AppLogger, nunca System.out.println()

## Contacto

Sistema desarrollado para Leandro.
Documento actualizado: 06/03/2026
