package com.gleak.true4x.usuario;

import com.gleak.true4x.storage.B2Service;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Service
public class PerfilService {

    private final UsuarioRepository usuarioRepository;
    private final B2Service b2Service;

    public PerfilService(UsuarioRepository usuarioRepository, B2Service b2Service) {
        this.usuarioRepository = usuarioRepository;
        this.b2Service = b2Service;
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

    public PerfilResponse subirFotoPerfil(String email, MultipartFile archivo) throws IOException {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (usuario.getFotoPerfil() != null) {
            b2Service.eliminarArchivo(usuario.getFotoPerfil());
        }

        String nombreArchivo = b2Service.subirArchivo(archivo, "fotos-perfil");
        usuario.setFotoPerfil(nombreArchivo);
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