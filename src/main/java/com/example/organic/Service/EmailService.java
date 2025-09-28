package com.example.organic.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Async
    public void enviarCorreoMasivo(List<String> destinatarios, String asunto, String cuerpo) {
        for (String correo : destinatarios) {
            if (correo == null || correo.isBlank()) {
                System.out.println("Correo vacío, se ignora");
                continue;
            }
            try {
                SimpleMailMessage mensaje = new SimpleMailMessage();
                mensaje.setTo(correo);
                mensaje.setSubject(asunto);
                mensaje.setText(cuerpo);
                mailSender.send(mensaje);
                System.out.println("Correo enviado a: " + correo);
            } catch (Exception e) {
                System.err.println("❌ Error enviando a " + correo + ": " + e.getMessage());
            }
        }
    }

}

