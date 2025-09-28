package com.example.organic.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.organic.Entity.UsuarioEntity;
import com.example.organic.Repository.UsuarioRepository;

@Service
public class EmailMasivoService {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Async
    public void enviarCorreoNuevoProducto(String nombreProducto, String descripcion) {
        List<UsuarioEntity> usuarios = usuarioRepository.findAll();

        List<String> correos = usuarios.stream()
                .map(UsuarioEntity::getCorreo)
                .filter(correo -> correo != null && !correo.isBlank())
                .toList();

        String asunto = "¡Nuevo producto disponible: " + nombreProducto + "!";
        String mensaje = "Hola, tenemos un nuevo producto para ti: "
                + nombreProducto + "\n\n"
                + descripcion + "\n\nVisítanos en Organic!";

        emailService.enviarCorreoMasivo(correos, asunto, mensaje);
    }
}

