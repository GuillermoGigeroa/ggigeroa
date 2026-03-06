package ggigeroa.impresora.runner.db;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseInitializer.class);
    
    @Override
    public void initialize(ConfigurableApplicationContext context) {
        String jpaEnabled = System.getProperty("spring.jpa.enabled");
        if ("false".equals(jpaEnabled)) {
            logger.warn("JPA ha sido deshabilitado debido a falla de conexión a base de datos");
        }
    }
}
