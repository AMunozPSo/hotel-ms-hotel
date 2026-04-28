package com.hotel.ms_hotel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelResponseDTO {

    private Long id;
    private String nombre;
    private String ciudad;
    private int categoria;
}
