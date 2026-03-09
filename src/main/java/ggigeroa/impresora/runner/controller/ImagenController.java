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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import ggigeroa.impresora.runner.impl.ImagenRepository;
import ggigeroa.impresora.runner.model.Imagen;

@RestController
@RequestMapping("/api/imagenes")
@Tag(name = "Imágenes", description = "API para gestionar imágenes codificadas en Base64")
public class ImagenController {

    private static final Logger logger = LoggerFactory.getLogger(ImagenController.class);

    @Autowired
    private ImagenRepository imagenRepository;

    @Operation(summary = "Obtener todas las imágenes", description = "Devuelve una lista de todas las imágenes (Base64) almacenadas en la base de datos.")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<Imagen>> listarTodas() {
        logger.info("GET /api/imagenes - Listando todas las imagenes");
        return ResponseEntity.ok(imagenRepository.findAll());
    }

    @Operation(summary = "Obtener una imagen por ID", description = "Busca y devuelve los detalles de una imagen específica a partir de su ID.")
    @ApiResponse(responseCode = "200", description = "Imagen encontrada exitosamente")
    @ApiResponse(responseCode = "404", description = "La imagen no existe")
    @GetMapping("/{id}")
    public ResponseEntity<Imagen> obtenerPorId(
            @Parameter(description = "ID único de la imagen a buscar", required = true) @PathVariable Long id) {
        logger.info("GET /api/imagenes/{} - Buscando imagen", id);
        return imagenRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear una nueva imagen", description = "Almacena una nueva imagen en formato Base64.")
    @ApiResponse(responseCode = "201", description = "Imagen creada exitosamente")
    @ApiResponse(responseCode = "500", description = "Error interno al crear la imagen")
    @PostMapping
    public ResponseEntity<Imagen> crear(
            @Parameter(description = "Objeto Imagen conteniendo el nombre y el Base64", required = true) @RequestBody Imagen imagen) {
        logger.info("POST /api/imagenes - Creando nueva imagen");
        try {
            Imagen nuevaImagen = imagenRepository.save(imagen);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaImagen);
        } catch (Exception e) {
            logger.error("Error al crear imagen", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Actualizar una imagen existente", description = "Modifica los datos (nombre y contenido Base64) de una imagen existente.")
    @ApiResponse(responseCode = "200", description = "Imagen actualizada exitosamente")
    @ApiResponse(responseCode = "404", description = "La imagen a actualizar no existe")
    @ApiResponse(responseCode = "500", description = "Error interno al actualizar la imagen")
    @PutMapping("/{id}")
    public ResponseEntity<Imagen> actualizar(
            @Parameter(description = "ID de la imagen a actualizar", required = true) @PathVariable Long id,
            @Parameter(description = "Nuevos datos de la imagen", required = true) @RequestBody Imagen datosNuevos) {
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

    @Operation(summary = "Eliminar una imagen", description = "Borra físicamente una imagen de la base de datos utilizando su ID.")
    @ApiResponse(responseCode = "204", description = "Imagen eliminada exitosamente (Sin contenido)")
    @ApiResponse(responseCode = "404", description = "La imagen a borrar no existe")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID de la imagen a eliminar", required = true) @PathVariable Long id) {
        logger.info("DELETE /api/imagenes/{} - Eliminando imagen", id);
        if (imagenRepository.existsById(id)) {
            imagenRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
