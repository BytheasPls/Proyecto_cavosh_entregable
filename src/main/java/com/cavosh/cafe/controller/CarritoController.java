package com.cavosh.cafe.controller;

import com.cavosh.cafe.dto.response.ApiResponse;
import com.cavosh.cafe.model.Carrito;
import com.cavosh.cafe.service.CarritoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carrito")
@RequiredArgsConstructor
public class CarritoController {

    private final CarritoService carritoService;

    @GetMapping
    public ResponseEntity<ApiResponse<Carrito>> obtenerCarrito() {
        return ResponseEntity.ok(carritoService.obtenerCarrito());
    }

    @PostMapping("/agregar")
    public ResponseEntity<ApiResponse<Carrito>> agregarItem(
            @RequestParam Long productoId,
            @RequestParam(defaultValue = "1") Integer cantidad,
            @RequestParam(required = false) String opciones) {
        return ResponseEntity.ok(carritoService.agregarItem(productoId, cantidad, opciones));
    }

    @DeleteMapping("/item/{itemId}")
    public ResponseEntity<ApiResponse<Carrito>> eliminarItem(@PathVariable Long itemId) {
        return ResponseEntity.ok(carritoService.eliminarItem(itemId));
    }
}