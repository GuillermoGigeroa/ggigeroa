package ggigeroa.impresora.runner.service;

import ggigeroa.impresora.runner.utils.AppLogger;
import ggigeroa.impresora.runner.utils.AppUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AppService {
    
    private final AppLogger log = new AppLogger(AppService.class);
    
    public Map<String, Object> procesarInformacion(Map<String, Object> datos) {
        log.operacionIniciada("procesarInformacion");
        
        try {
            Map<String, Object> resultado = new HashMap<>();
            resultado.put("original", datos);
            resultado.put("procesado", true);
            resultado.put("elementosProcesados", datos.size());
            resultado.put("idProceso", AppUtils.generarIdUnico());
            
            log.info("Información procesada: {} elementos", datos.size());
            log.operacionCompletada("procesarInformacion");
            
            return resultado;
        } catch (Exception e) {
            log.operacionFallida("procesarInformacion", e);
            throw new RuntimeException("Error al procesar información", e);
        }
    }
    
    public boolean validarDatos(Map<String, Object> datos) {
        log.operacionIniciada("validarDatos");
        
        if (datos == null || datos.isEmpty()) {
            log.warn("Datos vacíos o nulos");
            log.operacionFallida("validarDatos", new IllegalArgumentException("Datos vacíos"));
            return false;
        }
        
        log.info("Datos validados correctamente");
        log.operacionCompletada("validarDatos");
        return true;
    }
    
    public Map<String, Object> obtenerEstadisticas(String tipo) {
        log.operacionIniciada("obtenerEstadisticas: " + tipo);
        
        Map<String, Object> estadisticas = new HashMap<>();
        estadisticas.put("tipo", tipo);
        estadisticas.put("totalProcesos", Math.random() * 1000);
        estadisticas.put("tiempoPromedio", Math.random() * 5000 + "ms");
        estadisticas.put("tasaExito", Math.random() * 100 + "%");
        
        log.operacionCompletada("obtenerEstadisticas");
        return estadisticas;
    }
    
    public void ejecutarTareaLarga(String nombreTarea, int duracionSegundos) {
        log.operacionIniciada("ejecutarTareaLarga: " + nombreTarea);
        
        AppUtils.procesarEnThread(nombreTarea, () -> {
            log.info("Tarea larga iniciada: {}, duración: {} segundos", nombreTarea, duracionSegundos);
            try {
                for (int i = 0; i < duracionSegundos; i++) {
                    Thread.sleep(1000);
                    log.debug("Progreso: {}/{}", i + 1, duracionSegundos);
                }
                log.info("Tarea larga completada: {}", nombreTarea);
            } catch (InterruptedException e) {
                log.operacionFallida(nombreTarea, e);
            }
        });
        
        log.operacionCompletada("ejecutarTareaLarga");
    }
}
