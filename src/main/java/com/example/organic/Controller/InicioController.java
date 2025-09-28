package com.example.organic.Controller;

import com.example.organic.Entity.UsuarioEntity;
import com.example.organic.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.organic.Service.ProductosService;


@Controller
public class InicioController {

    @Autowired
    private ProductosService productosService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/inicio")
    public String mostrarInicio(Model model) {
        // En una aplicación real, aquí obtendrías el usuario de la sesión.
        // Por ahora, para probar, obtendremos un usuario de prueba (ID 1).
        UsuarioEntity usuario = usuarioRepository.findById(1L).orElse(null);

        // Agrega el usuario al modelo
        model.addAttribute("usuario", usuario);

        // Agrega los productos al modelo
        model.addAttribute("productos", productosService.getAll());
        return "inicio";
    }

    @GetMapping("/home")
    public String mostrarHome(Model model) {
        model.addAttribute("productos", productosService.getAll());
        return "home";
    }

    @GetMapping("/homeusuario")
    public String mostrarHomeUsuario(Model model) {
        model.addAttribute("productos", productosService.getAll());
        return "homeusuario";
    }

    
}
