package com.cavosh.cafe.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cafes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Cafe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private  String nombre;

    @Column(nullable = false, length = 200)
    private String direccion;

    @Column(nullable = false, length = 100)
    private String ciudad;

    private Double latitud;
    private Double longitud;

    @Column(nullable = false)
    private String horaApertura;

    @Column(nullable = false)
    private String horaCierre;

    private String telefono;
    private String imagenUrl;
    private Boolean activo = true;

}
