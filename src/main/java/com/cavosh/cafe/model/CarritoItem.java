package com.cavosh.cafe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "carrito_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarritoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "carrito_id")
    @JsonIgnore
    private Carrito carrito;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "producto_id")
    @JsonIgnore
    private Producto producto;

    @Column(nullable = false)
    private Integer cantidad = 1;

    @Column(columnDefinition = "TEXT")
    private String opcionesSeleccionadas;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precioUnitario;
}