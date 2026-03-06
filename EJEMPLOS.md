# Ejemplos de Spring Boot y Utilidades del Proyecto

Este documento describe los ejemplos y utilidades del proyecto Impresora.

## Utilidades Disponibles

### 1. AppLogger - Logger Personalizado

Clase que envuelve SLF4J con métodos convenientes para logging.

**Métodos:**
- `info(String mensaje)` - Log de información
- `info(String mensaje, Object... argumentos)` - Log con parámetros
- `error(String mensaje, Exception e)` - Log de error
- `debug(String mensaje)` - Log de debug
- `warn(String mensaje)` - Log de advertencia
- `operacionIniciada(String operacion)` - Log de inicio de operación
- `operacionCompletada(String operacion)` - Log de finalización
- `operacionFallida(String operacion, Exception e)` - Log de fallo

**Ejemplo de uso:**
```java
private final AppLogger log = new AppLogger(MiClase.class);

public void miMetodo() {
    log.operacionIniciada("miMetodo");
    try {
        // código
        log.operacionCompletada("miMetodo");
    } catch (Exception e) {
        log.operacionFallida("miMetodo", e);
    }
}
```

### 2. AppUtils - Utilidades Globales

Clase con métodos estáticos para funcionalidad común.

**Métodos:**
- `generarIdUnico()` - Genera UUID único
- `crearRespuestaExitosa(String mensaje, Object datos)` - Crea respuesta JSON exitosa
- `crearRespuestaError(String mensaje, String codigo)` - Crea respuesta JSON de error
- `procesarEnThread(String tarea, Runnable runnable)` - Ejecuta código en thread separado
- `formatearFecha(LocalDateTime fecha)` - Formatea fecha a string
- `validarJSON(String json)` - Valida JSON

**Ejemplo de uso:**
```java
Map<String, Object> respuesta = AppUtils.crearRespuestaExitosa(
    "Operación completada", 
    datos
);

AppUtils.procesarEnThread("MiTarea", () -> {
    log.info("Ejecutando en background");
});
```

### 3. AppService - Servicio de Negocio

Servicio Spring que contiene lógica de procesamiento.

**Métodos:**
- `procesarInformacion(Map<String, Object> datos)` - Procesa datos
- `validarDatos(Map<String, Object> datos)` - Valida datos
- `obtenerEstadisticas(String tipo)` - Obtiene estadísticas
- `ejecutarTareaLarga(String nombre, int duracionSegundos)` - Ejecuta tarea larga

**Ejemplo de uso:**
```java
@Autowired
private AppService appService;

public void usarServicio() {
    Map<String, Object> datos = new HashMap<>();
    Map<String, Object> resultado = appService.procesarInformacion(datos);
}
```

## Controladores de Ejemplo

### 1. AppController - Controlador web

Controlador clásico para vistas y operaciones MVC.

**Endpoints:**
- `GET /admin/info` - Información de la aplicación
- `GET /admin/procesar` - Procesar en background
- `GET /admin/health` - Health check
- `GET /admin/configuracion` - Configuración actual

### 2. AppRestController - REST API

Controlador REST con ejemplos de CRUD.

**Endpoints:**
- `GET /api/` - Health check
- `GET /api/{path}/{id}` - Obtener datos
- `POST /api/{path}/{id}` - Crear/Procesar datos
- `PUT /api/{path}/{id}` - Actualizar datos
- `DELETE /api/{path}/{id}` - Eliminar datos
- `GET /api/validar` - Validar conexión
- `POST /api/procesar-async` - Procesar asincronamente

### 3. AppAdvancedController - REST API Avanzada

Controlador REST con servicios inyectados.

**Endpoints:**
- `POST /api/v1/procesar` - Procesar con validación
- `GET /api/v1/estadisticas/{tipo}` - Obtener estadísticas
- `POST /api/v1/tarea-larga` - Iniciar tarea larga
- `GET /api/v1/echo` - Echo con logging

## Ejemplos de Uso

### Ejemplo 1: Crear una respuesta exitosa

```java
Map<String, Object> datos = new HashMap<>();
datos.put("id", 123);
datos.put("nombre", "Test");

Map<String, Object> respuesta = AppUtils.crearRespuestaExitosa(
    "Datos obtenidos correctamente", 
    datos
);
// Respuesta: {status: "success", timestamp: "...", message: "...", data: {...}}
```

### Ejemplo 2: Procesar en background

```java
AppUtils.procesarEnThread("DescargaArchivos", () -> {
    log.info("Iniciando descarga");
    // código largo
    log.info("Descarga completada");
});
```

### Ejemplo 3: Usar el servicio

```java
@Autowired
private AppService appService;

@PostMapping("/procesar")
public ResponseEntity<?> procesar(@RequestBody Map<String, Object> datos) {
    if (!appService.validarDatos(datos)) {
        return ResponseEntity.badRequest().body("Datos inválidos");
    }
    
    Map<String, Object> resultado = appService.procesarInformacion(datos);
    return ResponseEntity.ok(resultado);
}
```

### Ejemplo 4: Logging completo

```java
private final AppLogger log = new AppLogger(MiController.class);

@GetMapping("/test")
public void test() {
    log.operacionIniciada("test");
    
    try {
        // código
        log.info("Paso 1 completado");
        // más código
        log.operacionCompletada("test");
    } catch (Exception e) {
        log.operacionFallida("test", e);
    }
}
```

## Patrones de Respuesta JSON

Todas las respuestas siguen este patrón:

**Respuesta exitosa:**
```json
{
    "status": "success",
    "timestamp": "2026-03-06T15:00:00",
    "message": "Descripción de la operación",
    "data": {
        "id": 123,
        "valor": "..."
    }
}
```

**Respuesta de error:**
```json
{
    "status": "error",
    "timestamp": "2026-03-06T15:00:00",
    "message": "Descripción del error",
    "errorCode": "ERR_CODE"
}
```

## Mejores Prácticas

1. **Siempre usar AppLogger** en lugar de System.out.println()
2. **Wrappear respuestas** con AppUtils.crearRespuesta*
3. **Usar AppService** para lógica compleja
4. **Procesar en threads** tareas largas con AppUtils.procesarEnThread()
5. **Validar datos** antes de procesar
6. **Documentar excepciones** con operacionFallida()
7. **Usar @Autowired** para inyección de dependencias
