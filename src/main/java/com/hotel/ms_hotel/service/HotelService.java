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

@Service
@Transactional
@RequiredArgsConstructor
public class HotelService {
    @Autowired
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

    //Buscar hoteles por id, se usa el orElse asi cuando se llame en controller no se caiga
    public Hotel findById(Long id){
        return hotelRepository.findById(id).orElse(null);
    }

    //Buscar hotel por nombre
    public Hotel findByNombre(String nombre){
        return hotelRepository.findByNombre(nombre);
    }

    //Buscar hoteles por ciudad
    public List<Hotel> findByCiudad(String ciudad) {
        return hotelRepository.findByCiudad(ciudad);
    }

    //Guardar Hotel (modificado)
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
        //primero se valida si existe
        if(hotelRepository.existsById(id)){
            hotelRepository.existsById(id);
        }else {
            throw new RuntimeException("No se puede eliminar hotel");
        }
    }



}
