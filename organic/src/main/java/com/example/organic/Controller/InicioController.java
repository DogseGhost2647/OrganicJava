package com.example.organic.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.organic.Service.ProductosService;


@Controller
public class InicioController {

    @Autowired
    private ProductosService productosService;

    @GetMapping("/inicio")
    public String mostrarInicio(Model model) {
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
