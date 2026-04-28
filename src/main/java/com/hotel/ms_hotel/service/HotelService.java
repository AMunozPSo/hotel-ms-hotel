package com.hotel.ms_hotel.service;

import com.hotel.ms_hotel.Repository.HotelRepository;
import com.hotel.ms_hotel.dto.HotelRequestDTO;
import com.hotel.ms_hotel.dto.HotelResponseDTO;
import com.hotel.ms_hotel.model.Hotel;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class HotelService {
    private final HotelRepository hotelRepository;

    //mapeo privado
    private HotelResponseDTO mapToDTO(Hotel hotel) {
        return new HotelResponseDTO(
                hotel.getId(),
                hotel.getNombre(),
                hotel.getCiudad(),
                hotel.getCategoria()
        );
    }

    // Obtener todos
    public List<HotelResponseDTO> obtenerTodos() {
        return hotelRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    //Buscar hoteles por id, se usa el orElse asi cuando se llame en controller no se caiga
    public Optional<HotelResponseDTO> findById(Long id){
        return hotelRepository.findById(id).map(this::mapToDTO);
    }

    //Buscar hotel por nombre
    public Optional<HotelResponseDTO> findByNombre(String nombre){
        return Optional.ofNullable(hotelRepository.findByNombre(nombre))
                .map(this::mapToDTO);
    }

    //Buscar hoteles por ciudad
    public List<HotelResponseDTO> findByCiudad(String ciudad) {
        return hotelRepository.findByCiudad(ciudad)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    //Guardar Hotel
    public HotelResponseDTO guardar(HotelRequestDTO dto) {
        // Regla de Negocio: Verificar si el nombre ya existe
        if (hotelRepository.findByNombre(dto.getNombre()) != null) {
            throw new RuntimeException("Regla de Negocio: El nombre del hotel ya existe.");
        }

        Hotel hotel = new Hotel(
                null, // ID autogenerado
                dto.getNombre(),
                dto.getDireccion(),
                dto.getCiudad(),
                dto.getCategoria()
        );
        return mapToDTO(hotelRepository.save(hotel));
    }

    //Eliminar hotel por ID
    public void delete(Long id){
        if(hotelRepository.existsById(id)){
            hotelRepository.deleteById(id); // <-- Aquí era delete, no exists
        } else {
            throw new RuntimeException("No se puede eliminar: El hotel no existe.");
        }
    }



}
