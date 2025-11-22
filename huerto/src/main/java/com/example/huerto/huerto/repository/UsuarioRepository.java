// src/main/java/com/example/huerto/huerto/repository/UsuarioRepository.java
package com.example.huerto.huerto.repository;

import com.example.huerto.huerto.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCorreo(String correo);
}