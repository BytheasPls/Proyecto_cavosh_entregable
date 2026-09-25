package com.cavosh.cafe.controller;

import com.cavosh.cafe.dto.response.ApiResponse;
import com.cavosh.cafe.model.Categoria;
import com.cavosh.cafe.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaRepository categoriaRepository;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Categoria>>> obtenerTodas() {
        List<Categoria> categorias = categoriaRepository.findAll();
        return ResponseEntity.ok(ApiResponse.ok(categorias, "Categorías obtenidas"));
    }
}