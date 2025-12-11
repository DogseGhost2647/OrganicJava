package com.example.organic.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.organic.Entity.UsuarioEntity;
import com.example.organic.Repository.UsuarioRepository;
import com.example.organic.Service.UsuarioService;

@Controller
public class RegistroController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model){
        model.addAttribute("usuario", new UsuarioEntity());
        return "registro";
    }

    @PostMapping("/registro")
    public String procesarRegistro(@ModelAttribute UsuarioEntity usuario, Model model) {
        try {
            usuarioService.registrar(usuario);
            return "redirect:/login?registroExitoso";
        } catch (IllegalArgumentException e) {
            // Captura cuando el correo ya existe
            model.addAttribute("mensajeError", e.getMessage());
            model.addAttribute("usuario", usuario);
            return "registro"; // vuelve al formulario con el error
        } catch (Exception e) {
            // Cualquier otro error inesperado
            model.addAttribute("mensajeError", "⚠️ Ocurrió un error inesperado. Intenta nuevamente.");
            model.addAttribute("usuario", usuario);
            return "registro";
        }
    }

}
