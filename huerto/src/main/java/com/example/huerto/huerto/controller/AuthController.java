package com.example.huerto.huerto.controller;

import com.example.huerto.huerto.model.Usuario;
import com.example.huerto.huerto.repository.UsuarioRepository;
import com.example.huerto.huerto.dto.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Usuario usuario) {
        if (usuarioRepository.findByCorreo(usuario.getCorreo()).isPresent()) {
            return ResponseEntity.badRequest().body("El correo ya está registrado");
        }
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        if (usuario.getCorreo().matches("^[^\\s@]+@(profesor\\.duoc\\.cl)$")) {
            usuario.setRol("ADMIN");
        } else {
            usuario.setRol("USER");
        }
        Usuario saved = usuarioRepository.save(usuario);
        return ResponseEntity.ok(saved);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        return usuarioRepository.findByCorreo(loginRequest.getCorreo())
            .map(usuario -> {
                if (passwordEncoder.matches(loginRequest.getPassword(), usuario.getPassword())) {
                    return ResponseEntity.ok().body(
                        java.util.Map.of(
                            "correo", usuario.getCorreo(),
                            "role", usuario.getRol(),
                            "nombre", usuario.getNombre()
                        )
                    );
                } else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario o contraseña incorrectos");
                }
            })
            .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario o contraseña incorrectos"));
    }
}