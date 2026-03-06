package ggigeroa.impresora.runner.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "spring.jpa.enabled", havingValue = "false")
public class JpaDisabledConfiguration {
}
