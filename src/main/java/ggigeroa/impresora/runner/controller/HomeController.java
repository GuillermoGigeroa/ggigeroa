package ggigeroa.impresora.runner.controller;

import ggigeroa.impresora.runner.utils.AppLogger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    
    private final AppLogger log = new AppLogger(HomeController.class);
    
    public HomeController() {
        log.operacionIniciada("Inicialización de HomeController");
    }
    
    @GetMapping("/")
    public String inicio() {
        log.operacionIniciada("GET /");
        log.info("Sirviendo página principal - index.html");
        log.operacionCompletada("GET /");
        return "index";
    }
}
