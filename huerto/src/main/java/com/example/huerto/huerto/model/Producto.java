package com.example.huerto.huerto.model;

import jakarta.persistence.*;
import lombok.Data;

@Data 
@Entity
@Table(name = "producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private Integer precio;
    private String descripcion;
    private Boolean disponible;
    private String categoria;

    @Lob //large object 
    @Column(name = "imagen", columnDefinition = "BLOB")
    private byte[] imagen; 
}
