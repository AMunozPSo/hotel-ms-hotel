package com.hotel.ms_hotel.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos del hotel retornados por el sistema")
public class HotelResponseDTO {

    @Schema(description = "ID único del hotel", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Nombre del hotel", example = "Hotel Pullman")
    private String nombre;

    @Schema(description = "Ciudad de ubicación", example = "Arica")
    private String ciudad;

    @Schema(description = "Categoría en cantidad de estrellas", example = "5")
    private int categoria;
}