package ggigeroa.impresora.runner.controller;

import ggigeroa.impresora.runner.utils.AppLogger;
import ggigeroa.impresora.runner.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AppRestController {
    
    private final AppLogger log = new AppLogger(AppRestController.class);
    
    @Autowired
    private Environment environment;
    
    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> raiz() {
        log.operacionIniciada("GET /");
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("status", "success");
        respuesta.put("message", "El sistema ha sido iniciado correctamente");
        respuesta.put("timestamp", System.currentTimeMillis());
        
        log.operacionCompletada("GET /");
        return ResponseEntity.ok(respuesta);
    }
    
    @GetMapping("/{path}/{id}")
    public ResponseEntity<Map<String, Object>> obtenerDatos(@PathVariable String path, @PathVariable String id) {
        log.operacionIniciada("GET /" + path + "/" + id);
        
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("status", "success");
        respuesta.put("path", path);
        respuesta.put("idUsuario", id);
        respuesta.put("message", "Datos obtenidos correctamente");
        respuesta.put("requestId", AppUtils.generarIdUnico());
        respuesta.put("timestamp", AppUtils.formatearFecha(java.time.LocalDateTime.now()));
        
        log.info("GET llamado - path: {}, idUsuario: {}", path, id);
        log.operacionCompletada("GET /" + path + "/" + id);
        
        return ResponseEntity.ok(respuesta);
    }
    
    @PostMapping("/{path}/{id}")
    public ResponseEntity<Map<String, Object>> procesarDatos(
            @PathVariable String path,
            @PathVariable String id,
            @RequestBody Map<String, Object> datosRecibidos) {
        
        log.operacionIniciada("POST /" + path + "/" + id);
        
        if (datosRecibidos == null || datosRecibidos.isEmpty()) {
            log.warn("POST recibido con body vacío");
            return ResponseEntity.badRequest()
                    .body(AppUtils.crearRespuestaError("Body vacío o inválido", "ERR_EMPTY_BODY"));
        }
        
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("status", "success");
        respuesta.put("path", path);
        respuesta.put("idUsuario", id);
        respuesta.put("message", "Datos procesados correctamente");
        respuesta.put("receivedJson", datosRecibidos);
        respuesta.put("requestId", AppUtils.generarIdUnico());
        respuesta.put("timestamp", AppUtils.formatearFecha(java.time.LocalDateTime.now()));
        respuesta.put("procesado", true);
        
        log.info("POST /{}/{} llamado con {} propiedades", path, id, datosRecibidos.size());
        log.operacionCompletada("POST /" + path + "/" + id);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }
    
    @PutMapping("/{path}/{id}")
    public ResponseEntity<Map<String, Object>> actualizarDatos(
            @PathVariable String path,
            @PathVariable String id,
            @RequestBody Map<String, Object> datosActualizados) {
        
        log.operacionIniciada("PUT /" + path + "/" + id);
        
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("status", "success");
        respuesta.put("path", path);
        respuesta.put("idUsuario", id);
        respuesta.put("message", "Datos actualizados correctamente");
        respuesta.put("datosAnteriores", new HashMap<>()); // Simulación
        respuesta.put("datosNuevos", datosActualizados);
        respuesta.put("requestId", AppUtils.generarIdUnico());
        
        log.info("PUT /{}/{} - Actualización completada", path, id);
        log.operacionCompletada("PUT /" + path + "/" + id);
        
        return ResponseEntity.ok(respuesta);
    }
    
    @DeleteMapping("/{path}/{id}")
    public ResponseEntity<Map<String, Object>> eliminarDatos(@PathVariable String path, @PathVariable String id) {
        log.operacionIniciada("DELETE /" + path + "/" + id);
        
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("status", "success");
        respuesta.put("path", path);
        respuesta.put("idUsuario", id);
        respuesta.put("message", "Datos eliminados correctamente");
        respuesta.put("eliminado", true);
        respuesta.put("requestId", AppUtils.generarIdUnico());
        
        log.info("DELETE /{}/{} - Eliminación completada", path, id);
        log.operacionCompletada("DELETE /" + path + "/" + id);
        
        return ResponseEntity.ok(respuesta);
    }
    
    @GetMapping("/validar")
    public ResponseEntity<Map<String, Object>> validarConexion() {
        log.operacionIniciada("GET /validar");
        
        Map<String, Object> validacion = new HashMap<>();
        validacion.put("jpaEnabled", environment.getProperty("spring.jpa.enabled"));
        validacion.put("datasourceUrl", environment.getProperty("spring.datasource.url"));
        validacion.put("puerto", environment.getProperty("server.port"));
        validacion.put("aplicacion", environment.getProperty("spring.application.name", "Impresora"));
        
        Map<String, Object> respuesta = AppUtils.crearRespuestaExitosa("Validación completada", validacion);
        
        log.operacionCompletada("GET /validar");
        return ResponseEntity.ok(respuesta);
    }
    
    @PostMapping("/procesar-async")
    public ResponseEntity<Map<String, Object>> procesarAsincrono(@RequestBody Map<String, Object> datos) {
        log.operacionIniciada("POST /procesar-async");
        
        String taskId = AppUtils.generarIdUnico();
        
        AppUtils.procesarEnThread("AsyncTask-" + taskId, () -> {
            log.info("Procesando datos asincronos: {}", datos);
            try {
                Thread.sleep(3000);
                log.info("Procesamiento completado para task: {}", taskId);
            } catch (InterruptedException e) {
                log.operacionFallida("AsyncTask-" + taskId, e);
            }
        });
        
        Map<String, Object> resultado = new HashMap<>();
        resultado.put("taskId", taskId);
        resultado.put("estado", "En procesamiento");
        resultado.put("datosRecibidos", datos);
        
        log.operacionCompletada("POST /procesar-async");
        return ResponseEntity.accepted().body(AppUtils.crearRespuestaExitosa("Tarea procesada en background", resultado));
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> manejarError(Exception e) {
        log.error("Error no manejado", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(AppUtils.crearRespuestaError("Error interno del servidor", "ERR_INTERNAL"));
    }
}
