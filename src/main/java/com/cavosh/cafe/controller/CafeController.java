package com.cavosh.cafe.controller;

import com.cavosh.cafe.dto.response.ApiResponse;
import com.cavosh.cafe.model.Cafe;
import com.cavosh.cafe.repository.CafeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cafes")
@RequiredArgsConstructor
public class CafeController {

    private final CafeRepository cafeRepository;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Cafe>>> obtenerTodos() {
        List<Cafe> cafes = cafeRepository.findByActivoTrue();
        return ResponseEntity.ok(ApiResponse.ok(cafes, "Cafés obtenidos"));
    }

    @GetMapping("/ciudad/{ciudad}")
    public ResponseEntity<ApiResponse<List<Cafe>>> obtenerPorCiudad(@PathVariable String ciudad) {
        List<Cafe> cafes = cafeRepository.findByCiudadIgnoreCase(ciudad);
        return ResponseEntity.ok(ApiResponse.ok(cafes, "Cafés por ciudad"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Cafe>> obtenerPorId(@PathVariable Long id) {
        Cafe cafe = cafeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Café no encontrado"));
        return ResponseEntity.ok(ApiResponse.ok(cafe, "Café encontrado"));
    }
}