package com.cavosh.cafe.controller;

import com.cavosh.cafe.dto.response.ApiResponse;
import com.cavosh.cafe.model.Producto;
import com.cavosh.cafe.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoRepository productoRepository;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Producto>>> obtenerTodos() {
        List<Producto> productos = productoRepository.findByDisponibleTrue();
        return ResponseEntity.ok(ApiResponse.ok(productos, "Productos obtenidos"));
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<ApiResponse<List<Producto>>> obtenerPorCategoria(@PathVariable String categoria) {
        List<Producto> productos = productoRepository.findByCategoriaNombreIgnoreCase(categoria);
        return ResponseEntity.ok(ApiResponse.ok(productos, "Productos por categoría"));
    }

    @GetMapping("/nuevos")
    public ResponseEntity<ApiResponse<List<Producto>>> obtenerNuevos() {
        List<Producto> productos = productoRepository.findByNuevoTrueAndDisponibleTrue();
        return ResponseEntity.ok(ApiResponse.ok(productos, "Productos nuevos"));
    }

    @GetMapping("/populares")
    public ResponseEntity<ApiResponse<List<Producto>>> obtenerPopulares() {
        List<Producto> productos = productoRepository.findByPopularTrueAndDisponibleTrue();
        return ResponseEntity.ok(ApiResponse.ok(productos, "Productos populares"));
    }

    @GetMapping("/buscar")
    public ResponseEntity<ApiResponse<List<Producto>>> buscarPorNombre(@RequestParam String nombre) {
        List<Producto> productos = productoRepository.buscarPorNombre(nombre);
        return ResponseEntity.ok(ApiResponse.ok(productos, "Resultados de búsqueda"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Producto>> obtenerPorId(@PathVariable Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return ResponseEntity.ok(ApiResponse.ok(producto, "Producto encontrado"));
    }
}