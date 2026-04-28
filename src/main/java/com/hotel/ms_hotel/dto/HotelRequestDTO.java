package com.hotel.ms_hotel.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class HotelRequestDTO {
    @NotBlank(message = "El nombre del hotel es obligatorio")
    private String nombre;

    @NotBlank(message = "La direccion de hotel es obligatorio")
    private String direccion;

    @NotBlank(message = "La ciudad donde esta hotel es obligatorio")
    private String ciudad;

    @NotNull(message = "La categoria de hotel es obligatorio")
    @Positive(message = "La categoria de hotel debe ser mayor a 0")
    private  int categoria;



}
