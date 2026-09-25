package com.cavosh.cafe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 100)
    private String email;

    @Column(nullable = true)
    @JsonIgnore
    private String password;

    @Column(nullable = true, length = 100)
    private String nombreCompleto;

    @Column(length = 20)
    private String telefono;

    @Column(nullable = false)
    @Builder.Default
    private String rol = "CLIENTE";

    @Column(nullable = false)
    @Builder.Default
    private Boolean emailVerificado = false;

    private String proveedorSocial;
    private String idSocial;

    @Builder.Default
    private Integer puntosRecompensa = 0;

    @Builder.Default
    private Boolean descuentoEstudiante = false;

    @Builder.Default
    private Boolean recibirNotificaciones = true;

    @Builder.Default
    private Boolean compartirUbicacion = false;

    @Builder.Default
    private String moneda = "USD";

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
