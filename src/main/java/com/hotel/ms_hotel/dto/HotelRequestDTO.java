package com.hotel.ms_hotel.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos requeridos para registrar un nuevo hotel")
public class HotelRequestDTO {

    @Schema(description = "Nombre único del hotel", example = "Hotel Pullman Arica")
    @NotBlank(message = "El nombre del hotel es obligatorio")
    private String nombre;

    @Schema(description = "Dirección física del recinto", example = "Av. 21 de Mayo 456")
    @NotBlank(message = "La direccion de hotel es obligatorio")
    private String direccion;

    @Schema(description = "Ciudad de ubicación", example = "Arica")
    @NotBlank(message = "La ciudad donde esta hotel es obligatorio")
    private String ciudad;

    @Schema(description = "Categoría en cantidad de estrellas", example = "5")
    @NotNull(message = "La categoria de hotel es obligatorio")
    @Positive(message = "La categoria de hotel debe ser mayor a 0")
    private int categoria;
}