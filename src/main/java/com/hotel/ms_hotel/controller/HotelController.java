package com.hotel.ms_hotel.controller;

import com.hotel.ms_hotel.dto.HotelRequestDTO;
import com.hotel.ms_hotel.dto.HotelResponseDTO;
import com.hotel.ms_hotel.service.HotelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Hoteles", description = "Gestión del catálogo de infraestructura hotelera")
@Slf4j
@RestController
@RequestMapping("/api/v1/hoteles")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;

    // Obtener todos
    @Operation(summary = "Listar todos los hoteles")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada exitosamente")
    })
    @GetMapping
    @PreAuthorize("permitAll()") // Abierto a todo el público
    public ResponseEntity<List<HotelResponseDTO>> obtenerTodos(){
        log.info(">> Consultando lista completa de hoteles");
        return ResponseEntity.ok(hotelService.obtenerTodos());
    }

    // Obtener por id
    @Operation(summary = "Buscar usuario por ID", description = "Busca un hotel específico en el sistema mediante su ID único.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Hotel encontrado"),
            @ApiResponse(responseCode = "404", description = "Hotel no encontrado")
    })
    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<HotelResponseDTO> obtenerPorId(@PathVariable Long id){
        log.info(">> Buscando hotel con ID: {}", id);
        return hotelService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Obtener por ciudad
    @Operation(summary = "Buscar hoteles por ciudad")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Hoteles encontrados exitosamente"),
            @ApiResponse(responseCode = "404", description = "No se encontraron hoteles en la ciudad especificada")
    })
    @GetMapping("/ciudad/{ciudad}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<HotelResponseDTO>> buscarPorCiudad(@PathVariable String ciudad){
        log.info(">> Buscando hoteles en la ciudad: {}", ciudad);
        List<HotelResponseDTO> hoteles = hotelService.findByCiudad(ciudad);
        return hoteles.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(hoteles);
    }

    // Guardar un hotel
    @Operation(summary = "Crear nuevo usuario", description = "Registra un nuevo hotel en el sistema. Controla que el nombre no esté duplicado.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Hotel creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o conflicto con regla de negocio")
    })
    @PostMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<HotelResponseDTO> guardar(@Valid @RequestBody HotelRequestDTO dto){
        log.info(">> Registrando nuevo hotel");
        return ResponseEntity.status(HttpStatus.CREATED).body(hotelService.guardar(dto));
    }

    // Eliminar un hotel
    @Operation(summary = "Eliminar usuario por ID", description = "Elimina de forma permanente un hotel mediante su identificador.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Hotel eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Hotel no encontrado")
    })
    @SecurityRequirement(name = "bearerAuth") // Muestra el candado en Swagger porque requiere token
    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        log.warn(">> Solicitud para eliminar hotel ID: {}", id);
        hotelService.delete(id);
        return ResponseEntity.noContent().build();
    }
}