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

import ggigeroa.impresora.runner.impl.ImagenRepository;
import ggigeroa.impresora.runner.model.Imagen;

@RestController
@RequestMapping("/api/imagenes")
public class ImagenController {

    private static final Logger logger = LoggerFactory.getLogger(ImagenController.class);

    @Autowired
    private ImagenRepository imagenRepository;

    @GetMapping
    public ResponseEntity<List<Imagen>> listarTodas() {
        logger.info("GET /api/imagenes - Listando todas las imagenes");
        return ResponseEntity.ok(imagenRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Imagen> obtenerPorId(@PathVariable Long id) {
        logger.info("GET /api/imagenes/{} - Buscando imagen", id);
        return imagenRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Imagen> crear(@RequestBody Imagen imagen) {
        logger.info("POST /api/imagenes - Creando nueva imagen");
        try {
            Imagen nuevaImagen = imagenRepository.save(imagen);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaImagen);
        } catch (Exception e) {
            logger.error("Error al crear imagen", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Imagen> actualizar(@PathVariable Long id, @RequestBody Imagen datosNuevos) {
        logger.info("PUT /api/imagenes/{} - Actualizando imagen", id);
        Optional<Imagen> imagenExistente = imagenRepository.findById(id);

        if (imagenExistente.isPresent()) {
            Imagen imagen = imagenExistente.get();
            imagen.setNombre(datosNuevos.getNombre());
            imagen.setBase64(datosNuevos.getBase64());

            try {
                Imagen actualizada = imagenRepository.save(imagen);
                return ResponseEntity.ok(actualizada);
            } catch (Exception e) {
                logger.error("Error al actualizar imagen", e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        logger.info("DELETE /api/imagenes/{} - Eliminando imagen", id);
        if (imagenRepository.existsById(id)) {
            imagenRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
