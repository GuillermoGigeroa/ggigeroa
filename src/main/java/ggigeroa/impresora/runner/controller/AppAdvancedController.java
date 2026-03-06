package ggigeroa.impresora.runner.controller;

import ggigeroa.impresora.runner.service.AppService;
import ggigeroa.impresora.runner.utils.AppLogger;
import ggigeroa.impresora.runner.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class AppAdvancedController {
    
    private final AppLogger log = new AppLogger(AppAdvancedController.class);
    
    @Autowired
    private AppService appService;
    
    @PostMapping("/procesar")
    public ResponseEntity<Map<String, Object>> procesarDatos(@RequestBody Map<String, Object> datos) {
        log.operacionIniciada("procesarDatos");
        
        try {
            if (!appService.validarDatos(datos)) {
                return ResponseEntity.badRequest()
                        .body(AppUtils.crearRespuestaError("Datos inválidos", "ERR_VALIDATION"));
            }
            
            Map<String, Object> resultado = appService.procesarInformacion(datos);
            log.operacionCompletada("procesarDatos");
            
            return ResponseEntity.ok(AppUtils.crearRespuestaExitosa("Datos procesados correctamente", resultado));
        } catch (Exception e) {
            log.operacionFallida("procesarDatos", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(AppUtils.crearRespuestaError("Error al procesar datos", "ERR_PROCESSING"));
        }
    }
    
    @GetMapping("/estadisticas/{tipo}")
    public ResponseEntity<Map<String, Object>> obtenerEstadisticas(@PathVariable String tipo) {
        log.operacionIniciada("obtenerEstadisticas: " + tipo);
        
        try {
            Map<String, Object> estadisticas = appService.obtenerEstadisticas(tipo);
            log.operacionCompletada("obtenerEstadisticas");
            
            return ResponseEntity.ok(AppUtils.crearRespuestaExitosa("Estadísticas obtenidas", estadisticas));
        } catch (Exception e) {
            log.operacionFallida("obtenerEstadisticas", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(AppUtils.crearRespuestaError("Error al obtener estadísticas", "ERR_STATS"));
        }
    }
    
    @PostMapping("/tarea-larga")
    public ResponseEntity<Map<String, Object>> iniciarTareaLarga(
            @RequestParam String nombre,
            @RequestParam(defaultValue = "5") int duracionSegundos) {
        
        log.operacionIniciada("iniciarTareaLarga: " + nombre);
        
        try {
            appService.ejecutarTareaLarga(nombre, duracionSegundos);
            
            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("tareaId", AppUtils.generarIdUnico());
            respuesta.put("nombre", nombre);
            respuesta.put("duracion", duracionSegundos + "s");
            respuesta.put("estado", "En ejecución");
            
            log.operacionCompletada("iniciarTareaLarga");
            
            return ResponseEntity.accepted()
                    .body(AppUtils.crearRespuestaExitosa("Tarea larga iniciada", respuesta));
        } catch (Exception e) {
            log.operacionFallida("iniciarTareaLarga", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(AppUtils.crearRespuestaError("Error al iniciar tarea", "ERR_TASK"));
        }
    }
    
    @GetMapping("/echo")
    public ResponseEntity<Map<String, Object>> echo(
            @RequestParam(required = false) String mensaje,
            @RequestParam(defaultValue = "INFO") String nivel) {
        
        log.operacionIniciada("echo");
        
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("mensaje", mensaje != null ? mensaje : "Sin mensaje");
        respuesta.put("nivel", nivel);
        respuesta.put("timestamp", System.currentTimeMillis());
        
        switch (nivel) {
            case "ERROR":
                log.error("Echo: " + mensaje);
                break;
            case "WARN":
                log.warn("Echo: " + mensaje);
                break;
            case "DEBUG":
                log.debug("Echo: " + mensaje);
                break;
            default:
                log.info("Echo: " + mensaje);
        }
        
        log.operacionCompletada("echo");
        return ResponseEntity.ok(AppUtils.crearRespuestaExitosa("Echo completado", respuesta));
    }
}
