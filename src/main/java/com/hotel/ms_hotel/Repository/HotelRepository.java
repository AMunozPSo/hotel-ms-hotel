package com.hotel.ms_hotel.Repository;

import com.hotel.ms_hotel.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    //Se busca hotel por nombre del hotel en especifico.
    Hotel findByNombre(String nombre);

    //Se utiliza una lista, ya que se buscara todos los hoteles por ciudad.
    List<Hotel> findByCiudad (String ciudad);


}
