package com.example.organic.Controller;

import com.example.organic.Entity.ProductosEntity;
import com.example.organic.Entity.UsuarioEntity;
import com.example.organic.Repository.UsuarioRepository;

import java.util.List;

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
        
        UsuarioEntity usuario = usuarioRepository.findById(1L).orElse(null);

        model.addAttribute("usuario", usuario);

        List<ProductosEntity> productos = productosService.getAll();

        List<ProductosEntity> shampoos = productos.stream()
        .filter(p -> p.getCategoria().getNombre().equals("Shampoo")).toList();


        model.addAttribute("productos", productosService.getAll());
        model.addAttribute("shampoos", shampoos);
        return "inicio";
    }

    @GetMapping("/home")
    public String mostrarHome(Model model) {
        List<ProductosEntity> productos = productosService.getAll();

        List<ProductosEntity> shampoos = productos.stream()
        .filter(p -> p.getCategoria().getNombre().equals("Shampoo")).toList();

        model.addAttribute("productos", productosService.getAll());
        model.addAttribute("shampoos", shampoos);
        return "home";
    }

    @GetMapping("/homeusuario")
    public String mostrarHomeUsuario(Model model) {
        List<ProductosEntity> productos = productosService.getAll();

        List<ProductosEntity> shampoos = productos.stream()
        .filter(p -> p.getCategoria().getNombre().equals("Shampoo")).toList();
        
        model.addAttribute("productos", productosService.getAll());
        model.addAttribute("shampoos", shampoos);
        return "homeusuario";
    }

    
}
