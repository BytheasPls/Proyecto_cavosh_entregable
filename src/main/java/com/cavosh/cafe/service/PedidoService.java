package com.cavosh.cafe.service;

import com.cavosh.cafe.dto.response.ApiResponse;
import com.cavosh.cafe.enums.EstadoPedido;
import com.cavosh.cafe.enums.MetodoEntrega;
import com.cavosh.cafe.model.*;
import com.cavosh.cafe.repository.*;
import com.cavosh.cafe.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final CarritoRepository carritoRepository;
    private final CafeRepository cafeRepository;
    private final SecurityUtil securityUtil;

    public ApiResponse<List<Pedido>> obtenerHistorial() {
        Long userId = securityUtil.getCurrentUserId();
        return ApiResponse.ok(pedidoRepository.findByUsuarioIdOrderByFechaPedidoDesc(userId), "Historial obtenido");
    }

    @Transactional
    public ApiResponse<Pedido> crearPedidoDesdeCarrito(String notas, MetodoEntrega metodoEntrega) {
        Long userId = securityUtil.getCurrentUserId();

        Carrito carrito = carritoRepository.findByUsuarioId(userId)
                .orElseThrow(() -> new RuntimeException("Carrito vacío o no encontrado"));

        if (carrito.getItems().isEmpty()) {
            throw new RuntimeException("No hay productos en el carrito");
        }

        Cafe cafePorDefecto = cafeRepository.findById(1L).orElse(null);

        Pedido pedido = Pedido.builder()
                .numeroPedido("PED-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .usuario(Usuario.builder().id(userId).build())
                .cafe(cafePorDefecto)
                .items(carrito.getItems().stream().map(item -> PedidoItem.builder()
                        .producto(item.getProducto())
                        .cantidad(item.getCantidad())
                        .precioUnitario(item.getPrecioUnitario())
                        .opcionesSeleccionadas(item.getOpcionesSeleccionadas())
                        .build()).toList())
                .subtotal(carrito.calcularSubtotal())
                .descuento(carrito.getDescuento())
                .total(carrito.calcularTotal())
                .metodoEntrega(metodoEntrega)
                .estado(EstadoPedido.PEDIDO_REALIZADO)
                .notas(notas)
                .fechaPedido(LocalDateTime.now())
                .build();

        pedidoRepository.save(pedido);

        carrito.getItems().clear();
        carritoRepository.save(carrito);

        return ApiResponse.created(pedido, "Pedido creado exitosamente");
    }
}