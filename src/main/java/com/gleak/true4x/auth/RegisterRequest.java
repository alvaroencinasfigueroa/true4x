package com.gleak.true4x.auth;

import com.gleak.true4x.usuario.RolUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

    @Email(message = "Email no válido")
    @NotBlank(message = "El email es obligatorio")
    private String email;

    @NotBlank(message = "El password es obligatorio")
    @Size(min = 8, message = "El password debe tener mínimo 8 caracteres")
    private String password;

    private RolUsuario rol;
}