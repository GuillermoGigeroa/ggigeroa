package ggigeroa.impresora.runner;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class AppRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(AppRestController.class);
	
	@GetMapping("/")
    public @ResponseBody Object getObject() {
		String message = "El sistema ha sido iniciado correctamente.";
		logger.info(message);
		return message;
    }
	
	@GetMapping("/{path}/{id}")
	public String getPath(@PathVariable (name = "path") String path, @PathVariable (name = "id") String id) {
//		logger.info("GET called - path: {}, idUsuario: {}", path, id);
//		StringBuilder sb = new StringBuilder();
//		sb.append("Path: " + path);
//		sb.append(" - ");
//		sb.append("Id: " + id);
		return "GET called - path: " + path + ", idUsuario: " + id;
	}
	
//	@PostMapping("/{path}/{id}")
//	public String postPath(@PathVariable (name = "path") String path, @PathVariable (name = "id") String idUsuario, @RequestBody Object object) {
//		logger.info("POST /{}/{} called - path: {}, idUsuario: {}", path, idUsuario, path, idUsuario);
//		StringBuilder sb = new StringBuilder();
//		sb.append("Path: " + path);
//		sb.append(" - ");
//		sb.append("Id: " + idUsuario);
//		try {
//			StringBuilder json = new StringBuilder((String) object);
//			return sb.toString() + " - JSON: " + json.toString();
//		} catch (Exception e) {
//			logger.error("Error processing object", e);
//		}
//		return sb.toString();
//	}
	
}
