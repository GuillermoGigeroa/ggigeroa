package ggigeroa.impresora.runner;

import java.util.Map;
import java.util.HashMap;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class AppRestController {

	private static final Logger logger = LoggerFactory.getLogger(AppRestController.class);

	@GetMapping(value = "/", produces = "application/json")
	public Map<String, String> getObject() {
		String message = "El sistema ha sido iniciado correctamente.";
		logger.info(message);
		Map<String, String> response = new HashMap<>();
		response.put("status", "success");
		response.put("message", message);
		return response;
	}

	@GetMapping(value = "/{path}/{id}", produces = "application/json")
	public Map<String, Object> getPath(@PathVariable(name = "path") String path, @PathVariable(name = "id") String id) {
		logger.info("GET called - path: {}, idUsuario: {}", path, id);
		Map<String, Object> response = new HashMap<>();
		response.put("path", path);
		response.put("idUsuario", id);
		response.put("message", "GET called");
		return response;
	}

	@PostMapping(value = "/{path}/{id}", consumes = "application/json", produces = "application/json")
	public Map<String, Object> postPath(@PathVariable(name = "path") String path,
			@PathVariable(name = "id") String idUsuario,
			@RequestBody Map<String, Object> requestBody) {
		logger.info("POST /{}/{} called - path: {}, idUsuario: {}", path, idUsuario, path, idUsuario);

		Map<String, Object> response = new HashMap<>();
		response.put("path", path);
		response.put("idUsuario", idUsuario);
		response.put("message", "POST called");
		response.put("receivedJson", requestBody);

		return response;
	}

}
