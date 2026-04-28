package com.hotel.ms_hotel.config;

import com.hotel.ms_hotel.Repository.HotelRepository;
import com.hotel.ms_hotel.model.Hotel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final HotelRepository hotelRepository;

    @Override
    public void run(String... args){
        if(hotelRepository.count()> 0){
            log.info(">>>DataInitalizer: La BD de hoteles ya tiene datos, omitiendo carga");
            return;
        }
        log.info(">>>DataInitalizer: BD detectada, insertando hoteles de prueba... ");
        hotelRepository.save(new Hotel(null, "Hotel Quilpué Centro", "Los Carrera 123", "Quilpué", 4));
        hotelRepository.save(new Hotel(null, "Gran Hotel Viña", "Libertad 456", "Viña del Mar", 5));
        hotelRepository.save(new Hotel(null, "Hostal Olmué", "Paradero 29", "Olmué", 3));
        hotelRepository.save(new Hotel(null, "Hotel Limache", "Palmira Romano 789", "Limache", 2));

        log.info(">>>DataInitializer: {} hoteles insertados correctamente", hotelRepository.count());
    }
}

