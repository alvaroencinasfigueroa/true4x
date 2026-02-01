package com.gleak.true4x.auth;

import com.gleak.true4x.usuario.RolUsuario;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {

    private String token;
    private String email;
    private RolUsuario rol;
}