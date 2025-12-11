package com.example.organic.Controller;

import com.example.organic.DTO.CarritoItemRequestDTO;
import com.example.organic.DTO.CarritoResponseDTO;
import com.example.organic.Entity.CarritoEntity;
import com.example.organic.Entity.UsuarioEntity;
import com.example.organic.Repository.CarritoRepository;
import com.example.organic.Repository.UsuarioRepository;
import com.example.organic.Service.CarritoService;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/carrito")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    // Mostrar carrito
    @GetMapping
    public String verCarrito(Model model) {
        CarritoResponseDTO carritoDTO = carritoService.obtenerCarritoDTO();
        model.addAttribute("carrito", carritoDTO);
        return "carrito/carrito";
    }

    // Agregar producto
    @PostMapping("/agregar")
    public String agregarProducto(@ModelAttribute CarritoItemRequestDTO itemDTO) {
        carritoService.agregarProductos(itemDTO);
        return "redirect:/carrito";
    }

    // Actualizar cantidad
    @PostMapping("/actualizar")
    public String actualizarProducto(@ModelAttribute CarritoItemRequestDTO itemDTO) {
        carritoService.actualizarCantidad(itemDTO);
        return "redirect:/carrito";
    }

    // Eliminar producto
    @PostMapping("/eliminar/{productoId}")
    public String eliminarProducto(@PathVariable Long productoId) {
        carritoService.eliminarProducto(productoId);
        return "redirect:/carrito";
    }
}
