package ggigeroa.impresora.runner.config;

import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;

/**
 * Swagger/OpenAPI Configuration
 * Accessible at: http://localhost:8080/swagger-ui.html
 */
@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
			.info(new Info()
				.title("Impresora System API")
				.version("1.0.0")
				.description("Universal Input API for Printer System (Leandro)")
				.contact(new Contact()
					.name("Guillermo Gigeroa")
					.email("ggigeroa@example.com")
				)
			);
	}
}
