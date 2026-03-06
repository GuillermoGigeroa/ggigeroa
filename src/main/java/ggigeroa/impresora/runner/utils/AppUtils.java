package ggigeroa.impresora.runner.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AppUtils {
    private static final Logger logger = LoggerFactory.getLogger(AppUtils.class);
    
    public static String generarIdUnico() {
        return UUID.randomUUID().toString();
    }
    
    public static Map<String, Object> crearRespuestaExitosa(String mensaje, Object datos) {
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("status", "success");
        respuesta.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        respuesta.put("message", mensaje);
        respuesta.put("data", datos);
        logger.info("Respuesta exitosa: {}", mensaje);
        return respuesta;
    }
    
    public static Map<String, Object> crearRespuestaError(String mensaje, String codigo) {
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("status", "error");
        respuesta.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        respuesta.put("message", mensaje);
        respuesta.put("errorCode", codigo);
        logger.error("Respuesta error: {} [{}]", mensaje, codigo);
        return respuesta;
    }
    
    public static void procesarEnThread(String tarea, Runnable runnable) {
        Thread thread = new Thread(() -> {
            logger.info("Iniciando tarea en thread: {}", tarea);
            try {
                runnable.run();
                logger.info("Tarea completada: {}", tarea);
            } catch (Exception e) {
                logger.error("Error en tarea: {}", tarea, e);
            }
        });
        thread.setName("AppThread-" + tarea);
        thread.start();
    }
    
    public static String formatearFecha(LocalDateTime fecha) {
        return fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }
    
    public static boolean validarJSON(String json) {
        try {
            new com.fasterxml.jackson.databind.ObjectMapper().readTree(json);
            return true;
        } catch (Exception e) {
            logger.warn("JSON inválido: {}", e.getMessage());
            return false;
        }
    }
}
