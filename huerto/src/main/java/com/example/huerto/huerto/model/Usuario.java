// src/main/java/com/example/huerto/huerto/model/Usuario.java
package com.example.huerto.huerto.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String correo;
    private String password;
    private String telefono;
    private String region;
    private String comuna;
    private String rol; // "ADMIN" o "USER"
}