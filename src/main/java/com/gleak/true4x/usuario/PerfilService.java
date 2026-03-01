package com.gleak.true4x.usuario;

import org.springframework.stereotype.Service;

@Service
public class PerfilService {

    private final UsuarioRepository usuarioRepository;

    public PerfilService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public PerfilResponse obtenerPerfil(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return new PerfilResponse(
                usuario.getId(),
                usuario.getEmail(),
                usuario.getNombreUsuario(),
                usuario.getCelular(),
                usuario.getFotoPerfil(),
                usuario.getRol(),
                usuario.getOrgones()
        );
    }

    public PerfilResponse editarPerfil(String email, EditarPerfilRequest request) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (request.getNombreUsuario() != null) {
            usuario.setNombreUsuario(request.getNombreUsuario());
        }
        if (request.getCelular() != null) {
            usuario.setCelular(request.getCelular());
        }

        usuarioRepository.save(usuario);

        return new PerfilResponse(
                usuario.getId(),
                usuario.getEmail(),
                usuario.getNombreUsuario(),
                usuario.getCelular(),
                usuario.getFotoPerfil(),
                usuario.getRol(),
                usuario.getOrgones()
        );
    }
}