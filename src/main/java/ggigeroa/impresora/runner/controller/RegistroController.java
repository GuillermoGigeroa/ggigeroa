package ggigeroa.impresora.runner.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ggigeroa.impresora.runner.impl.RegistroRepository;
import ggigeroa.impresora.runner.model.Registro;

@RestController
@RequestMapping("/api/registros")
public class RegistroController {

    private static final Logger logger = LoggerFactory.getLogger(RegistroController.class);

    @Autowired
    private RegistroRepository registroRepository;

    @GetMapping
    public ResponseEntity<List<Registro>> listarTodos() {
        logger.info("GET /api/registros - Listando todos los registros");
        return ResponseEntity.ok(registroRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Registro> obtenerPorId(@PathVariable Long id) {
        logger.info("GET /api/registros/{} - Buscando registro", id);
        return registroRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Registro> crear(@RequestBody Registro registro) {
        logger.info("POST /api/registros - Creando nuevo registro");
        try {
            Registro nuevoRegistro = registroRepository.save(registro);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoRegistro);
        } catch (Exception e) {
            logger.error("Error al crear registro", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Registro> actualizar(@PathVariable Long id, @RequestBody Registro datosNuevos) {
        logger.info("PUT /api/registros/{} - Actualizando registro", id);
        Optional<Registro> registroExistente = registroRepository.findById(id);

        if (registroExistente.isPresent()) {
            Registro registro = registroExistente.get();
            registro.setPath(datosNuevos.getPath());
            registro.setIdUsuario(datosNuevos.getIdUsuario());

            try {
                Registro actualizado = registroRepository.save(registro);
                return ResponseEntity.ok(actualizado);
            } catch (Exception e) {
                logger.error("Error al actualizar registro", e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        logger.info("DELETE /api/registros/{} - Eliminando registro", id);
        if (registroRepository.existsById(id)) {
            registroRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
