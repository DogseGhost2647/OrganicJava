package com.example.organic.Controller;

import com.example.organic.Entity.UsuarioEntity;
import com.example.organic.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/usuario")
public class UsuariosController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/{id}/direccion")
    public String guardarDireccion(@PathVariable Long id, @RequestParam String direccion,
                                   RedirectAttributes redirectAttributes) {

        UsuarioEntity usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario != null) {
            usuario.setDireccion(direccion);
            usuarioRepository.save(usuario);
            redirectAttributes.addFlashAttribute("mensajeExito", "Dirección actualizada con éxito!");
        } else {
            redirectAttributes.addFlashAttribute("mensajeError", "Usuario no encontrado.");
        }

        return "redirect:/carrito";
    }
}
