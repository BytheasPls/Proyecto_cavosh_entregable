package com.cavosh.cafe.service;

import com.cavosh.cafe.dto.response.ApiResponse;
import com.cavosh.cafe.model.Usuario;
import com.cavosh.cafe.repository.UsuarioRepository;
import com.cavosh.cafe.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PerfilService {

    private final UsuarioRepository usuarioRepository;
    private final SecurityUtil securityUtil;

    public ApiResponse<Usuario> obtenerPerfil() {
        Long userId = securityUtil.getCurrentUserId();
        Usuario usuario = usuarioRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return ApiResponse.ok(usuario, "Perfil obtenido");
    }

    @Transactional
    public ApiResponse<Usuario> actualizarPerfil(String nombreCompleto, String telefono) {
        Long userId = securityUtil.getCurrentUserId();
        Usuario usuario = usuarioRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (nombreCompleto != null) usuario.setNombreCompleto(nombreCompleto);
        if (telefono != null) usuario.setTelefono(telefono);

        usuarioRepository.save(usuario);
        return ApiResponse.ok(usuario, "Perfil actualizado");
    }
}