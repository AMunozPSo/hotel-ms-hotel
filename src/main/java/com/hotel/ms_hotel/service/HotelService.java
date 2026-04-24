package com.hotel.ms_hotel.service;

import com.hotel.ms_hotel.Repository.HotelRepository;
import com.hotel.ms_hotel.model.Hotel;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional

public class HotelService {
    @Autowired
    private HotelRepository hotelRepository;

    //Listar todos los hoteles
    public List<Hotel> findAll(){
        return hotelRepository.findAll();
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

    //Guardar Hotel
    public Hotel save(Hotel hotel){
        //Regla: verificar si ya existe el nombre antes de guardar.
        Hotel existe = hotelRepository.findByNombre(hotel.getNombre());
        if(existe !=null && !existe.getId().equals(hotel.getId())){
            throw new RuntimeException("El nombre del hotel ya existe");
        }
        return  hotelRepository.save(hotel);

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
