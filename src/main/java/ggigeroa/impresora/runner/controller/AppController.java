package ggigeroa.impresora.runner.controller;

import ggigeroa.impresora.runner.utils.AppLogger;
import ggigeroa.impresora.runner.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.env.ConfigTreePropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin")
@Scope("singleton")
public class AppController {
    
    private final AppLogger log = new AppLogger(AppController.class);
    
    @Autowired
    private Environment environment;
    
    @Value("${server.port:8080}")
    private String serverPort;
    
    @Value("${spring.application.name:Impresora}")
    private String appName;
    
    public AppController() {
        log.operacionIniciada("Inicialización de AppController");
    }
    
    @GetMapping("/info")
    @ResponseBody
    public Map<String, Object> obtenerInfoAplicacion() {
        log.operacionIniciada("obtenerInfoAplicacion");
        
        Map<String, Object> info = new HashMap<>();
        info.put("aplicacion", appName);
        info.put("puerto", serverPort);
        info.put("javaVersion", System.getProperty("java.version"));
        info.put("osName", System.getProperty("os.name"));
        info.put("idSesion", AppUtils.generarIdUnico());
        
        log.operacionCompletada("obtenerInfoAplicacion");
        return AppUtils.crearRespuestaExitosa("Información de aplicación obtenida", info);
    }
    
    @GetMapping("/procesar")
    @ResponseBody
    public Map<String, Object> procesarEnBackground() {
        log.operacionIniciada("procesarEnBackground");
        
        String taskId = AppUtils.generarIdUnico();
        
        AppUtils.procesarEnThread("Tarea-" + taskId, () -> {
            try {
                Thread.sleep(2000);
                log.info("Tarea {} procesada exitosamente", taskId);
            } catch (InterruptedException e) {
                log.operacionFallida("Tarea-" + taskId, e);
            }
        });
        
        Map<String, Object> resultado = new HashMap<>();
        resultado.put("taskId", taskId);
        resultado.put("estado", "En procesamiento");
        
        log.operacionCompletada("procesarEnBackground");
        return AppUtils.crearRespuestaExitosa("Tarea iniciada en background", resultado);
    }
    
    @GetMapping("/health")
    @ResponseBody
    public Map<String, Object> healthCheck() {
        log.debug("healthCheck ejecutado");
        
        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP");
        health.put("timestamp", AppUtils.formatearFecha(java.time.LocalDateTime.now()));
        health.put("uptime", Runtime.getRuntime().totalMemory() / 1024 / 1024 + " MB");
        
        return health;
    }
    
    @GetMapping("/configuracion")
    @ResponseBody
    public Map<String, Object> obtenerConfiguracion() {
        log.operacionIniciada("obtenerConfiguracion");
        
        Map<String, Object> config = new HashMap<>();
        config.put("datasource.url", environment.getProperty("spring.datasource.url"));
        config.put("jpa.enabled", environment.getProperty("spring.jpa.enabled"));
        config.put("jpa.ddl-auto", environment.getProperty("spring.jpa.hibernate.ddl-auto"));
        config.put("server.port", serverPort);
        
        log.operacionCompletada("obtenerConfiguracion");
        return AppUtils.crearRespuestaExitosa("Configuración obtenida", config);
    }
    
    public void inicializarComponente() {
        log.operacionIniciada("inicializarComponente");
        AppUtils.procesarEnThread("ComponenteInit", () -> {
            log.info("Componente inicializado");
        });
        log.operacionCompletada("inicializarComponente");
    }
    
    public void ejecutarTareaAsincrona(String nombreTarea) {
        log.operacionIniciada("ejecutarTareaAsincrona: " + nombreTarea);
        AppUtils.procesarEnThread(nombreTarea, () -> {
            log.info("Ejecutando tarea: {}", nombreTarea);
        });
    }
}

