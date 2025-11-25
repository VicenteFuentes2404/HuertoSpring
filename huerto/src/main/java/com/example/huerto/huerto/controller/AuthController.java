package com.example.huerto.huerto.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.huerto.huerto.dto.LoginRequest;
import com.example.huerto.huerto.model.Usuario;
import com.example.huerto.huerto.repository.UsuarioRepository;
import com.example.huerto.huerto.security.JwtUtil;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

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

                        // 👉 Generamos el token JWT
                        String token = jwtUtil.generateToken(usuario.getCorreo(), usuario.getRol());

                        return ResponseEntity.ok().body(
                                Map.of(
                                        "token", token,
                                        "correo", usuario.getCorreo(),
                                        "role", usuario.getRol(),
                                        "nombre", usuario.getNombre()
                                )
                        );
                    } else {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                .body("Usuario o contraseña incorrectos");
                    }
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Usuario o contraseña incorrectos"));
    }
}
