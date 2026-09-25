package com.cavosh.cafe.controller;

import com.cavosh.cafe.dto.response.ApiResponse;
import com.cavosh.cafe.model.Favorito;
import com.cavosh.cafe.service.FavoritoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favoritos")
@RequiredArgsConstructor
public class FavoritoController {

    private final FavoritoService favoritoService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Favorito>>> obtenerFavoritos() {
        return ResponseEntity.ok(favoritoService.obtenerFavoritos());
    }

    @PostMapping("/{productoId}")
    public ResponseEntity<ApiResponse<String>> agregarFavorito(@PathVariable Long productoId) {
        return ResponseEntity.ok(favoritoService.agregarFavorito(productoId));
    }

    @DeleteMapping("/{productoId}")
    public ResponseEntity<ApiResponse<String>> eliminarFavorito(@PathVariable Long productoId) {
        return ResponseEntity.ok(favoritoService.eliminarFavorito(productoId));
    }
}