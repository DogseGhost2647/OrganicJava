package com.example.organic.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.organic.Entity.UsuarioEntity;
import com.example.organic.Repository.UsuarioRepository;

@Controller
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/login")
public String mostrarLogin(@RequestParam(required = false) String error,
                           @RequestParam(required = false) String registroExitoso,
                           Model model) {
    if (error != null) {
        model.addAttribute("mensajeError", "Correo o contraseña incorrectos.");
    }
    if (registroExitoso != null) {
        model.addAttribute("mensajeExito", "✅ Registro exitoso. Ahora puedes iniciar sesión.");
    }
    return "login";
}

    @PostMapping("/login")
    public String procesarLogin(@RequestParam String correo, 
                                @RequestParam String password, 
                                Model model) {
        Optional<UsuarioEntity> usuarioOpt = usuarioRepository.findByCorreo(correo);

        if (usuarioOpt.isPresent() && usuarioOpt.get().getPassword().equals(password)) {
            UsuarioEntity usuario = usuarioOpt.get();
            if (usuario.EsAdmin()) {
                return "redirect:/admin/dashboard";
            } else {
                return "redirect:/homeusuario";
            }
        } else {
            model.addAttribute("mensajeError", "Correo o contraseña incorrectos.");
            return "login";
        }
    }


}


