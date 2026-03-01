package com.gleak.true4x.usuario;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PerfilResponse {
    private Long id;
    private String email;
    private String nombreUsuario;
    private String celular;
    private String fotoPerfil;
    private RolUsuario rol;
    private Integer orgones;
}