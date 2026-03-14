package ggigeroa.impresora.runner.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class DatabaseHealthCheck {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseHealthCheck.class);

    private final DataSource dataSource;

    public DatabaseHealthCheck(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void checkDatabaseConnection() {
        try (Connection connection = dataSource.getConnection()) {
            String url = connection.getMetaData().getURL();
            logger.info("[DatabaseHealthCheck] ✓ Base de datos conectada exitosamente en {}", url);
        } catch (SQLException e) {
            logger.warn(
                    "[DatabaseHealthCheck] ⚠ No se pudo conectar a la base de datos. El sistema funcionará sin persistencia.");
        }
    }
}
