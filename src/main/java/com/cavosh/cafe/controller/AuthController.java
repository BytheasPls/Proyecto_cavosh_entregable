package com.cavosh.cafe.controller;

import com.cavosh.cafe.dto.request.LoginRequest;
import com.cavosh.cafe.dto.request.RegisterRequest;
import com.cavosh.cafe.dto.response.ApiResponse;
import com.cavosh.cafe.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Map<String, Object>>> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Map<String, Object>>> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.status(201).body(authService.register(request));
    }

    @PostMapping("/validar-correo")
    public ResponseEntity<ApiResponse<Map<String, Object>>> validarCorreo(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        return ResponseEntity.ok(authService.validarCorreo(email));
    }

    @PostMapping("/validar-codigo")
    public ResponseEntity<ApiResponse<Map<String, Object>>> validarCodigo(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String codigo = request.get("codigo");
        return ResponseEntity.ok(authService.validarCodigo(email, codigo));
    }
}