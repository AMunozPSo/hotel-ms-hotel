package com.hotel.ms_hotel.service;

import com.hotel.ms_hotel.Repository.HotelRepository;
import com.hotel.ms_hotel.dto.HotelRequestDTO;
import com.hotel.ms_hotel.dto.HotelResponseDTO;
import com.hotel.ms_hotel.model.Hotel;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class HotelService {

    private final HotelRepository hotelRepository;

    // mapeo privado
    private HotelResponseDTO mapToDTO(Hotel hotel) {
        return new HotelResponseDTO(
                hotel.getId(),
                hotel.getNombre(),
                hotel.getCiudad(),
                hotel.getCategoria()
        );
    }

    @Transactional
    public List<HotelResponseDTO> obtenerTodos() {
        log.info("[HOTEL_SERVICE] Consultando todos los hoteles");
        return hotelRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public Optional<HotelResponseDTO> findById(Long id){
        log.info("[HOTEL_SERVICE] Buscando hotel por ID: {}", id);
        return hotelRepository.findById(id).map(this::mapToDTO);
    }

    @Transactional
    public Optional<HotelResponseDTO> findByNombre(String nombre){
        log.info("[HOTEL_SERVICE] Buscando hotel por nombre: {}", nombre);
        return Optional.ofNullable(hotelRepository.findByNombre(nombre))
                .map(this::mapToDTO);
    }

    @Transactional
    public List<HotelResponseDTO> findByCiudad(String ciudad) {
        log.info("[HOTEL_SERVICE] Consultando hoteles en la ciudad: {}", ciudad);
        return hotelRepository.findByCiudad(ciudad)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public HotelResponseDTO guardar(HotelRequestDTO dto) {
        log.info("[HOTEL_SERVICE] Validando reglas de negocio para el hotel...");

        // --- 1. VALIDACIÓN DE NOMBRE (REGLA DE NEGOCIO) ---
        if (hotelRepository.findByNombre(dto.getNombre()) != null) {
            log.error("Falla en regla de negocio: El nombre del hotel '{}' ya existe", dto.getNombre());
            throw new IllegalArgumentException("Error: No se puede crear el hotel porque el nombre ya está registrado.");
        }
        log.info("[HOTEL_SERVICE] Reglas validadas. Preparando datos...");

        Hotel hotel = new Hotel(
                null,
                dto.getNombre(),
                dto.getDireccion(),
                dto.getCiudad(),
                dto.getCategoria()
        );

        Hotel hotelGuardado;

        // --- 2. GUARDADO EN BASE DE DATOS ---
        try {
            log.info("[HOTEL_SERVICE] Guardando hotel en BD...");
            hotelGuardado = hotelRepository.save(hotel);
            log.info("[HOTEL_SERVICE] Hotel guardado de forma exitosa con ID: {}.", hotelGuardado.getId());
        } catch (Exception e) {
            log.error("[HOTEL_SERVICE] La validación pasó, pero falló el guardado en base de datos: {}", e.getMessage());
            throw new RuntimeException("Error interno: No se pudo guardar el hotel en la base de datos.");
        }

        return mapToDTO(hotelGuardado);
    }

    @Transactional
    public void delete(Long id){
        log.info("[HOTEL_SERVICE] Verificando eliminacion de hotel ID: {}", id);

        if(!hotelRepository.existsById(id)){
            log.error("Falla en eliminacion: Hotel ID {} no existe", id);
            throw new IllegalArgumentException("El hotel con el ID " + id + " no existe en la base de datos");
        }

        try {
            hotelRepository.deleteById(id);
            log.info("[HOTEL_SERVICE] Hotel ID: {} eliminado correctamente", id);
        } catch (Exception e) {
            log.error("[HOTEL_SERVICE] Falló la eliminación del hotel en la base de datos: {}", e.getMessage());
            throw new RuntimeException("Error interno al intentar eliminar el hotel.");
        }
    }
}
