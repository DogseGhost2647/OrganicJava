package com.example.organic.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    public String mostrarFormularioLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam String correo, @RequestParam String password) {
    UsuarioEntity usuario = usuarioRepository.findByCorreo(correo);

    if (usuario != null && usuario.getPassword().equals(password)) {
        if (usuario.EsAdmin()) {
            return "redirect:/admin/dashboard";
        } else {
            return "redirect:/homeusuario";
        }
    } else {
        return "redirect:/login?error=true";
    }

}

}


