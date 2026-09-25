package com.cavosh.cafe.service;

import com.cavosh.cafe.dto.request.LoginRequest;
import com.cavosh.cafe.dto.request.RegisterRequest;
import com.cavosh.cafe.dto.response.ApiResponse;
import com.cavosh.cafe.model.CodigoVerificacion;
import com.cavosh.cafe.model.Usuario;
import com.cavosh.cafe.repository.CodigoVerificacionRepository;
import com.cavosh.cafe.repository.UsuarioRepository;
import com.cavosh.cafe.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final CodigoVerificacionRepository codigoRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public ApiResponse<Map<String, Object>> login(LoginRequest request) {
        log.info("Intento de login para: {}", request.getEmail());

        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!usuario.getEmailVerificado()) {
            throw new RuntimeException("Correo no verificado. Valida tu correo primero.");
        }

        if (usuario.getPassword() != null &&
                !passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        String token = jwtUtil.generateToken(
                usuario.getId(),
                usuario.getEmail(),
                usuario.getRol()
        );

        Map<String, Object> data = new HashMap<>();
        data.put("usuarioId", usuario.getId());
        data.put("email", usuario.getEmail());
        data.put("nombreCompleto", usuario.getNombreCompleto());
        data.put("rol", usuario.getRol());
        data.put("token", token);
        data.put("tokenExpiration", "24 horas");

        log.info("Login exitoso para: {}", usuario.getEmail());

        return ApiResponse.ok(data, "Login exitoso");
    }

    @Transactional
    public ApiResponse<Map<String, Object>> register(RegisterRequest request) {
        log.info(" Registro de nuevo usuario: {}", request.getEmail());

        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }

        Usuario usuario = Usuario.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .nombreCompleto(request.getNombreCompleto())
                .telefono(request.getTelefono())
                .rol("CLIENTE")
                .emailVerificado(false)
                .build();

        usuarioRepository.save(usuario);

        Map<String, Object> data = new HashMap<>();
        data.put("usuarioId", usuario.getId());
        data.put("email", usuario.getEmail());

        log.info("Usuario registrado: {}", usuario.getEmail());

        return ApiResponse.created(data, "Registro exitoso. Verifica tu correo.");
    }

    @Transactional
    public ApiResponse<Map<String, Object>> validarCorreo(String email) {
        log.info("Solicitud de código para: {}", email);

        if (!usuarioRepository.existsByEmail(email)) {
            throw new RuntimeException("Usuario no encontrado");
        }

        codigoRepository.deleteByEmailAndUsadoFalse(email);

        String codigo = String.format("%06d", new Random().nextInt(999999));

        CodigoVerificacion codigoVerif = CodigoVerificacion.builder()
                .email(email)
                .codigo(codigo)
                .fechaExpiracion(LocalDateTime.now().plusMinutes(10))
                .usado(false)
                .build();

        codigoRepository.save(codigoVerif);

        emailService.enviarCodigoVerificacion(email, codigo);

        Map<String, Object> data = new HashMap<>();
        data.put("email", email);
        data.put("expiresIn", "10 minutos");

        log.info("Código enviado a: {}", email);

        return ApiResponse.ok(data, "Código de verificación enviado al correo");
    }

    @Transactional
    public ApiResponse<Map<String, Object>> validarCodigo(String email, String codigo) {
        log.info("Validando código para: {}", email);

        CodigoVerificacion codigoVerif = codigoRepository
                .findByEmailAndCodigoAndUsadoFalse(email, codigo)
                .orElseThrow(() -> new RuntimeException("Código inválido o ya usado"));

        if (codigoVerif.estaExpirado()) {
            throw new RuntimeException("El código ha expirado");
        }

        codigoVerif.setUsado(true);
        codigoRepository.save(codigoVerif);

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setEmailVerificado(true);
        usuarioRepository.save(usuario);

        emailService.enviarBienvenida(usuario.getEmail(), usuario.getNombreCompleto());

        Map<String, Object> data = new HashMap<>();
        data.put("email", usuario.getEmail());
        data.put("nombreCompleto", usuario.getNombreCompleto());

        log.info("Correo verificado: {}", email);

        return ApiResponse.ok(data, "Correo verificado exitosamente");
    }
}