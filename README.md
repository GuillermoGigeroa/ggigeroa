# Sistema de Impresora - Leandro

Sistema Spring Boot para manejo de impresoras con ejemplos de patrones y utilidades de desarrollo profesional.

## 📋 Descripción General

Este proyecto es una aplicación **Spring Boot 3.5.10** completa que demuestra buenas prácticas en el desarrollo de aplicaciones Java empresariales. Incluye arquitectura MVC y REST API, manejo de bases de datos opcional, autenticación HTTP Basic, logging centralizado y documentación con Swagger/OpenAPI.

## Características

## ⚙️ Requisitos Previos

- **Java 17** o superior
- **Maven 3.8+** (se incluye Maven Wrapper `mvnw`)
- **MySQL 5.7+** (opcional - la aplicación inicia sin BD)
- **Git** (recomendado)

## 🛠️ Stack Tecnológico

### Core
- **Spring Boot 3.5.10** - Framework principal
- **Java 17** - Lenguaje de programación
- **Maven** - Gestor de dependencias y build

### Bases de Datos
- **MySQL** (opcional)
- **Spring Data JPA** - ORM

### API & Documentación
- **Spring Web** - REST Controllers
- **Springdoc OpenAPI 2.x** - Swagger UI
- **Jackson** - JSON serialization

### Seguridad
- **Spring Security** - Autenticación HTTP Basic
- **Spring Boot Actuator** - Health checks

### Logging
- **SLF4J** - Logging facade
- **Logback** - Implementación de logging

## 📦 Características

- ✅ REST API completa con endpoints CRUD
- ✅ Arquitectura MVC con templates HTML
- ✅ Autenticación HTTP Basic integrada
- ✅ Swagger UI para documentación interactiva
- ✅ Logging centralizado con AppLogger
- ✅ Manejo graceful de BD no disponible
- ✅ Processing de tareas largas en background
- ✅ Validación de salud de aplicación
- ✅ Respuestas JSON estandarizadas
- ✅ Manejo de excepciones global
- ✅ Ejemplos de patrones de diseño Spring

## 📁 Estructura del Proyecto

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

## 🚀 Inicio Rápido

### 1️⃣ Clonar o Descargar Proyecto

```bash
# Clonar desde repositorio
git clone <url-del-repositorio>
cd ggigeroa
```

### 2️⃣ Compilar

```bash
# Con Maven Wrapper (recomendado)
mvnw.cmd clean package -DskipTests

# O con Maven instalado globalmente
maven clean package -DskipTests
```

### 3️⃣ Ejecutar

```bash
# Opción 1: Con Maven Wrapper
mvnw.cmd spring-boot:run

# Opción 2: Ejecutar JAR directamente
java -jar target/impresora.ggigeroa-0.0.1-SNAPSHOT.jar

# Opción 3: Con Maven
maven spring-boot:run
```

### 4️⃣ Verificar Instalación

```bash
# Acceder a Swagger UI (requiere autenticación)
http://localhost:8080/swagger-ui.html

# Usuario: leandro
# Contraseña: impresora123

# O ejecutar health check
curl -u leandro:impresora123 http://localhost:8080/api/
```

## 🔐 Configuración de Seguridad

La aplicación utiliza **HTTP Basic Authentication** con credenciales hardcodeadas (desarrollo):

```
Usuario: leandro
Contraseña: impresora123
```

**⚠️ Para Producción:**
- Cambiar a OAuth2/JWT
- Usar variables de entorno
- Implementar base de datos de usuarios
- Usar HTTPS/TLS

## 🗄️ Configuración de Base de Datos

### Configuración Predeterminada (MySQL)

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/impresora_db
spring.datasource.username=root
spring.datasource.password=
```

### Sin Base de Datos

La aplicación está diseñada para funcionar **sin BD disponible**:

1. Al iniciar, intenta conectar a MySQL
2. Si falla la conexión, se deshabilita JPA automáticamente
3. Log: `"No se pudo detectar base de datos..."`
4. La aplicación continúa funcionando con endpoints funcionales

Para crear la BD manualmente:

```sql
CREATE DATABASE impresora_db;
USE impresora_db;

CREATE TABLE imagen (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(255),
    datos LONGBLOB
);

CREATE TABLE registro (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    tipo VARCHAR(50),
    contenido TEXT,
    fecha_creacion TIMESTAMP
);
```

## 📚 Inicio Rápido

## 📚 Endpoints Disponibles

### Health & Admin
- `GET /api/` - Health check (requiere autenticación)
- `GET /admin/info` - Información del sistema
- `GET /admin/health` - Estado de salud
- `GET /admin/configuracion` - Configuración actual
- `POST /admin/procesar` - Procesar en background

### REST API v1
- `GET /api/v1/echo` - Echo endpoint
- `GET /api/v1/estadisticas/{tipo}` - Obtener estadísticas
- `POST /api/v1/procesar` - Procesar con servicio
- `POST /api/v1/tarea-larga` - Ejecutar tarea larga

### REST API Genérica
- `GET /api/{path}/{id}` - Obtener recurso
- `POST /api/{path}/{id}` - Procesar recurso
- `PUT /api/{path}/{id}` - Actualizar recurso
- `DELETE /api/{path}/{id}` - Eliminar recurso

### MVC (HTML)
- `GET /` - Página de inicio
- `GET /error` - Página de error

## 📖 Lectura Recomendada

1. **README.md** ← Estás aquí
2. **EJEMPLOS.md** - Ejemplos de uso de Spring Boot y utilidades
3. **ENDPOINTS.md** - Referencia completa de API
4. **agent.md** - Contexto técnico del proyecto

## 🛠️ Utilidades Disponibles

El proyecto incluye tres componentes principales:

### 1️⃣ AppLogger - Logger Personalizado

```java
private final AppLogger log = new AppLogger(MiClase.class);

// Métodos disponibles
log.operacionIniciada("nombreOperacion");
log.operacionCompletada("nombreOperacion");
log.debug("Mensaje de debug");
log.info("Mensaje informativo");
log.warn("Advertencia");
log.error("Error detectado");
```

**Ventajas:**
- Wrapper sobre SLF4J
- Logging consistente
- Fácil de usar y cambiar a nivel global

### 2️⃣ AppUtils - Utilidades Estáticas

```java
// Generar ID único
String id = AppUtils.generarIdUnico();

// Crear respuesta exitosa
Map<String, Object> respuesta = AppUtils.crearRespuestaExitosa(
    "Operación completada", 
    datos
);

// Procesar en thread separado
AppUtils.procesarEnThread("MiTarea", () -> {
    // Código a ejecutar en background
});

// Obtener información del sistema
String info = AppUtils.obtenerInfo();
```

**Utilidad para:**
- Generación de IDs
- Respuestas estandarizadas
- Processing asíncrono
- Información del sistema

### 3️⃣ AppService - Servicio de Negocio

```java
@Autowired
private AppService appService;

// Inyectado automáticamente por Spring
public void miMetodo() {
    appService.procesarInformacion(datos);
    appService.ejecutarProceso();
}
```

**Características:**
- Singleton inyectable
- Lógica de negocio centralizada
- Acceso a dependencias Spring

## 🏗️ Arquitectura del Proyecto

## 🎯 Componentes Principales

### Controllers (Controladores)

#### AppController (MVC - Vistas HTML)
```
Endpoints:
- GET  /admin/info        → Información del sistema
- GET  /admin/health      → Estado de salud
- GET  /admin/configuracion → Configuración actual
- POST /admin/procesar    → Procesar en background
```

#### AppRestController (REST API Genérica)
```
Endpoints:
- GET    /api/{path}/{id}  → Obtener recurso
- POST   /api/{path}/{id}  → Procesar recurso
- PUT    /api/{path}/{id}  → Actualizar recurso
- DELETE /api/{path}/{id}  → Eliminar recurso
```

#### AppAdvancedController (REST API v1 - Avanzada)
```
Endpoints:
- GET  /api/v1/echo                → Echo test
- GET  /api/v1/estadisticas/{tipo} → Obtener estadísticas
- POST /api/v1/procesar            → Procesar con servicio
- POST /api/v1/tarea-larga         → Ejecutar tarea larga
```

### Services (Servicios de Negocio)

- **AppService** - Lógica de negocio central
  - Procesamiento de información
  - Ejecución de operaciones
  - Acceso a repositorios

### Data Access (Persistencia)

- **ImagenRepository** - JpaRepository para entidad Imagen
  - CRUD de imágenes
  - Almacenamiento en base64

- **RegistroRepository** - JpaRepository para entidad Registro
  - CRUD de registros
  - Auditoría de operaciones

### Configuration (Configuración)

- **SecurityConfig** - Configuración de seguridad HTTP Basic
- **SwaggerConfig** - Configuración de OpenAPI/Swagger UI
- **DataSourceConfiguration** - Configuración de base de datos
- **JpaDisabledConfiguration** - Deshabilitación condicional de JPA

## 📊 Modelos de Datos

### Imagen
```java
id: Long              // Primary Key
nombre: String        // Nombre de la imagen
datos: byte[]         // Datos en base64
```

### Registro
```java
id: Long              // Primary Key
tipo: String          // Tipo de registro
contenido: String     // Contenido del registro
fechaCreacion: LocalDateTime // Timestamp
```

## 📝 Ejemplos de Uso

### Ejemplo 1: Autenticación Básica

```bash
# Sin autenticación (falla)
curl http://localhost:8080/api/
# → 401 Unauthorized

# Con autenticación (exitosa)
curl -u leandro:impresora123 http://localhost:8080/api/
# → {"status": "OK", "message": "Sistema operativo"}
```

### Ejemplo 2: Obtener Estadísticas

```bash
curl -u leandro:impresora123 \
  http://localhost:8080/api/v1/estadisticas/usuarios
# → {"status": "OK", "data": {...}}
```

### Ejemplo 3: Ejecutar Tarea Larga

```bash
curl -u leandro:impresora123 -X POST \
  http://localhost:8080/api/v1/tarea-larga \
  -H "Content-Type: application/json" \
  -d '{"duracion": 5000}'
# → Procesa en background, retorna ID de tarea
```

### Ejemplo 4: Usar AppUtils en Controlador

```java
@RestController
public class MiControlador {
    
    @GetMapping("/datos")
    public ResponseEntity<?> obtenerDatos() {
        String idUnico = AppUtils.generarIdUnico();
        Map<String, Object> respuesta = AppUtils.crearRespuestaExitosa(
            "Datos obtenidos",
            Map.of("id", idUnico)
        );
        return ResponseEntity.ok(respuesta);
    }
}
```

## 🔍 Logging

Configuración de niveles:
```properties
logging.level.root=INFO
logging.level.ggigeroa.impresora.runner=DEBUG
logging.level.org.springframework.security=DEBUG
```

Todos los componentes utilizan `AppLogger` que implementa SLF4J/Logback internamente.

## 🎓 Características de Spring Boot Demostradas

El proyecto incluye ejemplos prácticos de:

| Anotación | Descripción | Ubicación |
|-----------|-------------|-----------|
| `@RestController` | Controlador REST | AppRestController.java |
| `@Service` | Servicio de negocio | AppService.java |
| `@Autowired` | Inyección de dependencias | Todos los controllers |
| `@Value` | Inyección de propiedades | SecurityConfig.java |
| `@RequestMapping/@GetMapping` | Mapeo de rutas HTTP | Controllers |
| `@PathVariable` | Parámetros de ruta | AppRestController.java |
| `@RequestParam` | Parámetros de query | Controllers |
| `@RequestBody` | Cuerpo de solicitud JSON | Controllers |
| `ResponseEntity` | Control de respuestas HTTP | Todos los endpoints |
| `@ExceptionHandler` | Manejo global de excepciones | ErrorPageController.java |
| `@Configuration` | Configuración de Spring | SecurityConfig.java |
| `@ConditionalOnProperty` | Beans condicionales | JpaDisabledConfiguration.java |
| `Environment` | Acceso a propiedades | Configuración |
| `ApplicationContextInitializer` | Inicializadores | DatabaseInitializer.java |
| **Spring Data JPA** | Persistencia ORM | ImagenRepository.java |

## 📋 Mejores Prácticas Implementadas

✅ **Arquitectura en capas** - Controllers → Services → Repositories
✅ **Inyección de dependencias** - Totalmente basada en Spring Beans
✅ **Manejo de excepciones global** - ErrorPageController
✅ **Logging consistente** - AppLogger en todos lados
✅ **Respuestas JSON estandarizadas** - Patrón único
✅ **Seguridad** - HTTP Basic Auth implementado
✅ **Documentación API** - Swagger/OpenAPI integrado
✅ **Health checks** - Monitoreo de aplicación
✅ **Processing asíncrono** - Tareas en background
✅ **Sin Lombok** - Código explícito y legible
✅ **Graceful degradation** - Funciona sin BD

## 🐛 Solución de Problemas

### Problema: Puerto 8080 en uso
```bash
# Cambiar puerto en application.properties
server.port=8081
```

### Problema: Credenciales incorrectas
```bash
# Verificar en SecurityConfig.java
Usuario por defecto: leandro
Contraseña por defecto: impresora123
```

### Problema: MySQL no disponible
```
→ La aplicación detecta la ausencia de BD
→ Deshabilita JPA automáticamente
→ Continúa funcionando con endpoints
```

### Problema: Swagger UI no carga
```bash
# Verificar que Springdoc esté incluido
# Ver dependencias en pom.xml
# Acceder a http://localhost:8080/swagger-ui.html
```

## 📚 Recursos Adicionales

- [Spring Boot Official Documentation](https://spring.io/projects/spring-boot)
- [Spring Security Reference](https://docs.spring.io/spring-security/reference/)
- [Spring Data JPA Documentation](https://spring.io/projects/spring-data-jpa)
- [Springdoc OpenAPI Documentation](https://springdoc.org/)
- [SLF4J Manual](https://www.slf4j.org/manual.html)

## 👨‍💼 Información del Proyecto

| Campo | Valor |
|-------|-------|
| **Nombre** | Sistema de Impresora - Leandro |
| **Versión** | 0.0.1-SNAPSHOT |
| **Java** | 17+ |
| **Spring Boot** | 3.5.10 |
| **Build Tool** | Maven |
| **Licencia** | Ver LICENSE |
| **Última Actualización** | 07/03/2026 |

## 📞 Soporte

Sistema desarrollado para **Leandro**.

Para reportar problemas o sugerencias, consulte la documentación complementaria:
- `EJEMPLOS.md` - Ejemplos de código
- `ENDPOINTS.md` - Referencia de API
- `agent.md` - Contexto técnico

---

**Nota:** Este es un proyecto de demostración educativa que sigue buenas prácticas de desarrollo profesional en Java/Spring Boot.
