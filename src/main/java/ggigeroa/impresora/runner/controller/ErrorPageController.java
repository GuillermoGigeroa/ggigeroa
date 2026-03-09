package ggigeroa.impresora.runner.controller;

import ggigeroa.impresora.runner.utils.AppLogger;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ErrorPageController implements ErrorController {

    private final AppLogger log = new AppLogger(ErrorPageController.class);

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        log.operacionIniciada("Manejo de error");

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object message = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        Object exception = request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);

        int statusCode = 500;
        String statusText = "Error Interno del Servidor";
        String errorMessage = "Algo salió mal";

        if (status != null) {
            statusCode = Integer.parseInt(status.toString());

            try {
                HttpStatus httpStatus = HttpStatus.valueOf(statusCode);
                statusText = httpStatus.getReasonPhrase();
            } catch (IllegalArgumentException e) {
                statusText = "Error";
            }
        }

        if (message != null) {
            errorMessage = message.toString();
        } else if (exception != null) {
            errorMessage = exception.toString();
        }

        log.error("Error " + statusCode + ": " + errorMessage);

        model.addAttribute("statusCode", statusCode);
        model.addAttribute("statusText", statusText);
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("timestamp", System.currentTimeMillis());

        log.operacionCompletada("Manejo de error");

        return "error";
    }
}
