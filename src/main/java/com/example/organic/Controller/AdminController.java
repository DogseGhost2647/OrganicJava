package com.example.organic.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.organic.Entity.PedidosEntity;
import com.example.organic.Entity.ProductosEntity;
import com.example.organic.Repository.PedidosRepository;
import com.example.organic.Service.ProductosService;
import com.example.organic.Service.UsuarioService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ProductosService productosService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PedidosRepository pedidosRepository;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // Productos
        List<ProductosEntity> productos = productosService.getAll();
        long totalProductos = productosService.contarProductos();

        // Usuarios
        long usuariosRegistrados = usuarioService.contarUsuarios();

        // Pedidos recientes (orden descendente por fecha)
        List<PedidosEntity> pedidosRecientes = pedidosRepository.findAll(Sort.by(Sort.Direction.DESC, "fecha"));

        // Pasar atributos al modelo
        model.addAttribute("productos", productos);
        model.addAttribute("totalProductos", totalProductos);
        model.addAttribute("usuariosRegistrados", usuariosRegistrados);
        model.addAttribute("pedidosRecientes", pedidosRecientes);

        return "admin/dashboard";
    }
}
