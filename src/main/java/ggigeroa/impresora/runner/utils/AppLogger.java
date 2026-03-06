package ggigeroa.impresora.runner.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppLogger {
    private final Logger logger;
    private final String clase;
    
    public AppLogger(Class<?> clazz) {
        this.logger = LoggerFactory.getLogger(clazz);
        this.clase = clazz.getSimpleName();
    }
    
    public void info(String mensaje) {
        logger.info("[{}] {}", clase, mensaje);
    }
    
    public void info(String mensaje, Object... argumentos) {
        logger.info("[{}] " + mensaje, clase, argumentos);
    }
    
    public void error(String mensaje, Exception e) {
        logger.error("[{}] {} - {}", clase, mensaje, e.getMessage());
    }
    
    public void error(String mensaje) {
        logger.error("[{}] {}", clase, mensaje);
    }
    
    public void debug(String mensaje) {
        logger.debug("[{}] {}", clase, mensaje);
    }
    
    public void debug(String mensaje, Object... argumentos) {
        logger.debug("[{}] " + mensaje, clase, argumentos);
    }
    
    public void warn(String mensaje) {
        logger.warn("[{}] {}", clase, mensaje);
    }
    
    public void operacionIniciada(String operacion) {
        info("OPERACIÓN INICIADA: " + operacion);
    }
    
    public void operacionCompletada(String operacion) {
        info("OPERACIÓN COMPLETADA: " + operacion);
    }
    
    public void operacionFallida(String operacion, Exception e) {
        error("OPERACIÓN FALLIDA: " + operacion, e);
    }
}
