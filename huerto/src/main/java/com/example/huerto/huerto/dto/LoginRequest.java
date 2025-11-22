// src/main/java/com/example/huerto/huerto/dto/LoginRequest.java
package com.example.huerto.huerto.dto;

public class LoginRequest {
    private String correo;
    private String password;

    // Getters y setters
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
