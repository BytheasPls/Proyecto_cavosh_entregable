package com.cavosh.cafe.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "codigos_verificacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class CodigoVerificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 6)
    private String codigo;

    @Column(nullable = false)
    private LocalDateTime fechaExpiracion;

    private Boolean usado = false;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    public Boolean estaExpirado() {
        return LocalDateTime.now().isAfter(fechaExpiracion);
    }

}
