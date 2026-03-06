package ggigeroa.impresora.runner.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
@ConditionalOnProperty(name = "spring.jpa.enabled", havingValue = "true")
public class DatabaseHealthCheck {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseHealthCheck.class);
    
    private final DataSource dataSource;
    
    public DatabaseHealthCheck(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    @EventListener(ApplicationReadyEvent.class)
    public void checkDatabaseConnection() {
        try (Connection connection = dataSource.getConnection()) {
            logger.info("Base de datos conectada exitosamente en localhost:3306/impresora_db");
        } catch (SQLException e) {
            logger.error("No se pudo detectar base de datos. El sistema iniciará en modo sin persistencia. Detalles: {}", e.getMessage());
        }
    }
}
