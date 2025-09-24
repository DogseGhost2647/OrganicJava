package com.example.organic.Controller;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import com.example.organic.Entity.ProductosEntity;
import com.example.organic.Service.ProductosService;
import com.example.organic.Service.UsuarioService;


@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ProductosService productosService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
    List<ProductosEntity> productos = productosService.getAll();

    long totalProductos = productosService.contarProductos();
    long usuariosRegistrados = usuarioService.contarUsuarios();

    model.addAttribute("productos", productos);
    model.addAttribute("totalProductos", totalProductos);
    model.addAttribute("usuariosRegistrados", usuariosRegistrados);
    
    return "admin/dashboard";
    }
}
