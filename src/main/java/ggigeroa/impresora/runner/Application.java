package ggigeroa.impresora.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ggigeroa.impresora.runner.db.DatabaseInitializer;

@SpringBootApplication
public class Application {
	private static final Logger logger = LoggerFactory.getLogger(Application.class);
	private static final String NAME = "Impresora";

	public static void main(String[] args) {
		logger.info("Iniciando "+NAME+ ".");
		SpringApplication app = new SpringApplication(Application.class);
		app.addInitializers(new DatabaseInitializer());
		app.run(args);
		logger.info(NAME + " iniciado correctamente.");
	}

}
