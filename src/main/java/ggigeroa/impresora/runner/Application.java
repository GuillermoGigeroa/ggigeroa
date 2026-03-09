package ggigeroa.impresora.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ggigeroa.impresora.runner.model.Constants;

@SpringBootApplication
public class Application {
	private static final Logger logger = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		logger.info("Inciando sistema [" + Constants.NAME.getValue() + "] ...");
		SpringApplication app = new SpringApplication(Application.class);
		app.run(args);
		logger.info("Sistema [" + Constants.NAME.getValue() + "] iniciado correctamente.");
	}

}
