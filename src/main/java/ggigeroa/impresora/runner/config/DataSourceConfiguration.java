package ggigeroa.impresora.runner.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Configuration
public class DataSourceConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(DataSourceConfiguration.class);
    
    @SuppressWarnings("unused")
	private final Environment environment;
    
    public DataSourceConfiguration(Environment environment) {
        this.environment = environment;
    }
    
    @Bean
    @Primary
    @ConditionalOnMissingBean
    public DataSource dataSource() {
        String url = "jdbc:mysql://localhost:3306/impresora_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        String username = "root";
        String password = "";
        
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        
        try (Connection connection = dataSource.getConnection()) {
            logger.info("Base de datos detectada y conectada correctamente en localhost:3306/impresora_db");
        } catch (SQLException e) {
            logger.error("No se pudo detectar base de datos. El sistema iniciará en modo sin persistencia. Detalles: {}", e.getMessage());
            System.setProperty("spring.jpa.enabled", "false");
        }
        
        return dataSource;
    }
}
