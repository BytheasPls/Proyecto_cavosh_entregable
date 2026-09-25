package com.cavosh.cafe.controller;

import com.cavosh.cafe.dto.response.ApiResponse;
import com.cavosh.cafe.enums.MetodoEntrega;
import com.cavosh.cafe.model.Pedido;
import com.cavosh.cafe.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    @GetMapping("/historial")
    public ResponseEntity<ApiResponse<List<Pedido>>> obtenerHistorial() {
        return ResponseEntity.ok(pedidoService.obtenerHistorial());
    }

    @PostMapping("/crear")
    public ResponseEntity<ApiResponse<Pedido>> crearPedido(
            @RequestParam(required = false) String notas,
            @RequestParam(defaultValue = "PICKUP") MetodoEntrega metodoEntrega) {
        return ResponseEntity.status(201).body(pedidoService.crearPedidoDesdeCarrito(notas, metodoEntrega));
    }
}