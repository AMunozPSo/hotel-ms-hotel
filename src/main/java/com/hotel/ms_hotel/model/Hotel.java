package com.hotel.ms_hotel.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "hotel")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Nombre de Hotel
    //Regla:  no puede estar vacío ni contener solo espacios
    @Column(nullable = false, length = 100)
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    //Direccion de hotel
    //Regla:  no puede estar vacío ni contener solo espacios
    @Column(length = 200)
    @NotBlank(message = "La direccion no puede estar vacío")
    private String direccion;

    @NotBlank(message = "El nombre de ciudad no puede estar vacío")
    private String ciudad;

    //Categoria de Hotel
    //Regla:  no puede nulo y debe ser mayor a cero
    @NotNull(message = "La categoria de hotel es obligatorio")
    @Positive(message = "La categoria debe ser mayor a cero(0)")
    private int categoria;


}
