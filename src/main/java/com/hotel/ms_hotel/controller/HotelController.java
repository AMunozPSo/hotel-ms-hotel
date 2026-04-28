package com.hotel.ms_hotel.controller;

import com.hotel.ms_hotel.dto.HotelRequestDTO;
import com.hotel.ms_hotel.dto.HotelResponseDTO;
import com.hotel.ms_hotel.service.HotelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hoteles")
@RequiredArgsConstructor
public class HotelController {
    private final HotelService hotelService;

    //Obtener todos
    @GetMapping
    public ResponseEntity<List<HotelResponseDTO>> obtenerTodos(){
        return ResponseEntity.ok(hotelService.obtenerTodos());
    }

    //Obtener por id
    @GetMapping("/{id}")
    public ResponseEntity<HotelResponseDTO> obtenerPorId(@PathVariable Long id){
        return hotelService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //Obtener por ciudad
    @GetMapping("/ciudad/{ciudad}")
    public ResponseEntity<List<HotelResponseDTO>> buscarPorCiudad(@PathVariable String ciudad){
        List<HotelResponseDTO> hoteles = hotelService.findByCiudad(ciudad);
        return hoteles.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(hoteles);
    }

    //Guardar un hotel
    @PostMapping
    public ResponseEntity<HotelResponseDTO> guardar(@Valid @RequestBody HotelRequestDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(hotelService.guardar(dto));
    }

    //Eliminar un hotel
    @DeleteMapping("/{id}")
    public ResponseEntity<Void>eliminar(@PathVariable Long id){
        hotelService.delete(id);
        return ResponseEntity.noContent().build();
    }



}
