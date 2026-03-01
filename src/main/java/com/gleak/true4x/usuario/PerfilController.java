package com.gleak.true4x.usuario;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/perfil")
@CrossOrigin(origins = "*")
public class PerfilController {

    private final PerfilService perfilService;

    public PerfilController(PerfilService perfilService) {
        this.perfilService = perfilService;
    }

    @GetMapping
    public ResponseEntity<PerfilResponse> obtenerPerfil(Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(perfilService.obtenerPerfil(email));
    }

    @PutMapping
    public ResponseEntity<PerfilResponse> editarPerfil(
            Authentication authentication,
            @RequestBody EditarPerfilRequest request) {
        String email = authentication.getName();
        return ResponseEntity.ok(perfilService.editarPerfil(email, request));
    }
}