package com.cavosh.cafe.controller;

import com.cavosh.cafe.dto.response.ApiResponse;
import com.cavosh.cafe.model.Usuario;
import com.cavosh.cafe.service.PerfilService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/perfil")
@RequiredArgsConstructor
public class PerfilController {

    private final PerfilService perfilService;

    @GetMapping
    public ResponseEntity<ApiResponse<Usuario>> obtenerPerfil() {
        return ResponseEntity.ok(perfilService.obtenerPerfil());
    }

    @PutMapping("/actualizar")
    public ResponseEntity<ApiResponse<Usuario>> actualizarPerfil(
            @RequestParam(required = false) String nombreCompleto,
            @RequestParam(required = false) String telefono) {
        return ResponseEntity.ok(perfilService.actualizarPerfil(nombreCompleto, telefono));
    }
}