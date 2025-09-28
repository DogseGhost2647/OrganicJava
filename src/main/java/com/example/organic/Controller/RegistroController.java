package com.example.organic.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.organic.Entity.UsuarioEntity;
import com.example.organic.Service.UsuarioService;

@Controller
public class RegistroController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model){
        model.addAttribute("usuario", new UsuarioEntity());
        return "registro";
    }

    @PostMapping("/registro")
    public String procesarRegistro(@ModelAttribute UsuarioEntity usuario, Model model) {
        usuarioService.create(usuario);
        return "redirect:/login?registroExitoso";
    }
}
