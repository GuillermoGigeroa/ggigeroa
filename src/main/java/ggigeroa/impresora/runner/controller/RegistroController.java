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
import org.springframework.security.access.prepost.PreAuthorize;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import ggigeroa.impresora.runner.impl.RegistroRepository;
import ggigeroa.impresora.runner.model.Registro;

@RestController
@RequestMapping("/api/registros")
@Tag(name = "Registros", description = "API para gestionar los registros de impresiones o archivos")
public class RegistroController {

    private static final Logger logger = LoggerFactory.getLogger(RegistroController.class);

    @Autowired
    private RegistroRepository registroRepository;

    @Operation(summary = "Obtener todos los registros", description = "Devuelve una lista con todos los registros almacenados en la base de datos.")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<Registro>> listarTodos() {
        logger.info("GET /api/registros - Listando todos los registros");
        return ResponseEntity.ok(registroRepository.findAll());
    }

    @Operation(summary = "Obtener un registro por ID", description = "Busca y devuelve los detalles de un registro específico a partir de su ID.")
    @ApiResponse(responseCode = "200", description = "Registro encontrado exitosamente")
    @ApiResponse(responseCode = "404", description = "El registro no existe")
    @GetMapping("/{id}")
    public ResponseEntity<Registro> obtenerPorId(
            @Parameter(description = "ID único del registro a buscar", required = true) @PathVariable Long id) {
        logger.info("GET /api/registros/{} - Buscando registro", id);
        return registroRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear un nuevo registro", description = "Almacena la información de un nuevo registro en la base de datos.")
    @ApiResponse(responseCode = "201", description = "Registro creado exitosamente")
    @ApiResponse(responseCode = "500", description = "Error interno al crear el registro")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Registro> crear(
            @Parameter(description = "Objeto Registro conteniendo el path y usuario", required = true) @RequestBody Registro registro) {
        logger.info("POST /api/registros - Creando nuevo registro");
        try {
            Registro nuevoRegistro = registroRepository.save(registro);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoRegistro);
        } catch (Exception e) {
            logger.error("Error al crear registro", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Actualizar un registro existente", description = "Modifica los datos (path y usuario) de un registro ya existente mediante su ID.")
    @ApiResponse(responseCode = "200", description = "Registro actualizado exitosamente")
    @ApiResponse(responseCode = "404", description = "El registro a actualizar no existe")
    @ApiResponse(responseCode = "500", description = "Error interno al actualizar el registro")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Registro> actualizar(
            @Parameter(description = "ID del registro a actualizar", required = true) @PathVariable Long id,
            @Parameter(description = "Nuevos datos del registro", required = true) @RequestBody Registro datosNuevos) {
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

    @Operation(summary = "Eliminar un registro", description = "Borra físicamente un registro de la base de datos utilizando su ID.")
    @ApiResponse(responseCode = "204", description = "Registro eliminado exitosamente (Sin contenido)")
    @ApiResponse(responseCode = "404", description = "El registro a borrar no existe")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del registro a eliminar", required = true) @PathVariable Long id) {
        logger.info("DELETE /api/registros/{} - Eliminando registro", id);
        if (registroRepository.existsById(id)) {
            registroRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
