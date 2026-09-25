package com.cavosh.cafe.service;

import com.cavosh.cafe.dto.response.ApiResponse;
import com.cavosh.cafe.model.*;
import com.cavosh.cafe.repository.*;
import com.cavosh.cafe.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CarritoService {

    private final CarritoRepository carritoRepository;
    private final ProductoRepository productoRepository;
    private final SecurityUtil securityUtil;

    public ApiResponse<Carrito> obtenerCarrito() {
        Long userId = securityUtil.getCurrentUserId();
        Carrito carrito = carritoRepository.findByUsuarioId(userId)
                .orElseGet(() -> crearCarritoVacio(userId));
        return ApiResponse.ok(carrito, "Carrito obtenido");
    }

    @Transactional
    public ApiResponse<Carrito> agregarItem(Long productoId, Integer cantidad, String opciones) {
        Long userId = securityUtil.getCurrentUserId();

        Carrito carrito = carritoRepository.findByUsuarioId(userId)
                .orElseGet(() -> crearCarritoVacio(userId));

        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (!producto.getDisponible() || producto.getStock() < cantidad) {
            throw new RuntimeException("Producto no disponible o stock insuficiente");
        }

        CarritoItem item = CarritoItem.builder()
                .carrito(carrito)
                .producto(producto)
                .cantidad(cantidad)
                .opcionesSeleccionadas(opciones)
                .precioUnitario(producto.getPrecioBase())
                .build();

        carrito.getItems().add(item);
        carritoRepository.save(carrito);

        return ApiResponse.ok(carrito, "Producto agregado al carrito");
    }

    @Transactional
    public ApiResponse<Carrito> eliminarItem(Long itemId) {
        Long userId = securityUtil.getCurrentUserId();
        Carrito carrito = carritoRepository.findByUsuarioId(userId)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        carrito.getItems().removeIf(item -> item.getId().equals(itemId));
        carritoRepository.save(carrito);

        return ApiResponse.ok(carrito, "Item eliminado del carrito");
    }

    private Carrito crearCarritoVacio(Long userId) {
        return carritoRepository.save(Carrito.builder()
                .usuario(Usuario.builder().id(userId).build())
                .descuento(BigDecimal.ZERO)
                .build());
    }
}