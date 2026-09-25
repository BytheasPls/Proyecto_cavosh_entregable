package com.cavosh.cafe.service;

import com.cavosh.cafe.dto.response.ApiResponse;
import com.cavosh.cafe.model.Favorito;
import com.cavosh.cafe.model.Producto;
import com.cavosh.cafe.model.Usuario;
import com.cavosh.cafe.repository.FavoritoRepository;
import com.cavosh.cafe.repository.ProductoRepository;
import com.cavosh.cafe.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoritoService {

    private final FavoritoRepository favoritoRepository;
    private final ProductoRepository productoRepository;
    private final SecurityUtil securityUtil;

    public ApiResponse<List<Favorito>> obtenerFavoritos() {
        Long userId = securityUtil.getCurrentUserId();
        return ApiResponse.ok(favoritoRepository.findByUsuarioId(userId), "Favoritos obtenidos");
    }

    @Transactional
    public ApiResponse<String> agregarFavorito(Long productoId) {
        Long userId = securityUtil.getCurrentUserId();

        if (favoritoRepository.existsByUsuarioIdAndProductoId(userId, productoId)) {
            throw new RuntimeException("El producto ya está en favoritos");
        }

        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        Favorito favorito = Favorito.builder()
                .usuario(Usuario.builder().id(userId).build())
                .producto(producto)
                .build();

        favoritoRepository.save(favorito);
        return ApiResponse.ok("Producto agregado a favoritos", "Éxito");
    }

    @Transactional
    public ApiResponse<String> eliminarFavorito(Long productoId) {
        Long userId = securityUtil.getCurrentUserId();
        favoritoRepository.deleteByUsuarioIdAndProductoId(userId, productoId);
        return ApiResponse.ok("Producto eliminado de favoritos", "Éxito");
    }
}