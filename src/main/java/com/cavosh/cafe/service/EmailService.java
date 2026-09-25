package com.cavosh.cafe.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    @Async
    public void enviarCodigoVerificacion(String email, String codigo) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("🔐 Código de verificación - Cavosh Café");
            message.setText(
                    "¡Hola!\n\n" +
                            "Tu código de verificación es: " + codigo + "\n\n" +
                            "Este código expira en 10 minutos.\n\n" +
                            "Si no solicitaste este código, ignora este correo.\n\n" +
                            "Saludos,\nCavosh Café"
            );
            mailSender.send(message);
            log.info("Correo de verificación enviado a: {}", email);
        } catch (Exception e) {
            log.error("Error al enviar correo a {}: {}", email, e.getMessage());
        }
    }

    @Async
    public void enviarBienvenida(String email, String nombre) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("¡Bienvenido a Cavosh Café!");
            message.setText(
                    "¡Hola " + nombre + "!\n\n" +
                            "Gracias por registrarte en Cavosh Café.\n\n" +
                            "Ya puedes disfrutar de nuestros mejores cafés.\n\n" +
                            "Saludos,\nCavosh Café"
            );
            mailSender.send(message);
            log.info("Correo de bienvenida enviado a: {}", email);
        } catch (Exception e) {
            log.error("Error al enviar bienvenida a {}: {}", email, e.getMessage());
        }
    }
}