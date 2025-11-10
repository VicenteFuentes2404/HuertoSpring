package com.example.huerto.huerto.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List; // 1. IMPORTAR LIST

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

    @Lob 
    @Column(name = "imagen", columnDefinition = "BLOB")
    private byte[] imagen; // Imagen principal (sin cambios)


    // 2. AÑADIR ESTE BLOQUE
    @ElementCollection // Le dice a JPA que es una colección
    @CollectionTable(
        name = "producto_imagenes", // Nombre de la nueva tabla para la galería
        joinColumns = @JoinColumn(name = "producto_id") // Llave foránea a la tabla 'producto'
    )
    @Lob // Cada imagen es un objeto grande
    @Column(name = "imagen_data", columnDefinition = "BLOB") // Columna en la nueva tabla
    private List<byte[]> imagenes; // La galería de imágenes

}
