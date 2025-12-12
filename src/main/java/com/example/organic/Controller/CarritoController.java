package com.example.organic.Controller;

import com.example.organic.DTO.CarritoItemRequestDTO;
import com.example.organic.DTO.CarritoResponseDTO;
import com.example.organic.Entity.CarritoEntity;
import com.example.organic.Entity.PedidosEntity;
import com.example.organic.Entity.UsuarioEntity;
import com.example.organic.Repository.CarritoRepository;
import com.example.organic.Repository.UsuarioRepository;
import com.example.organic.Service.CarritoService;
import com.example.organic.Service.PedidoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/carrito")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public String verCarrito(Model model, @ModelAttribute("mensajeExito") String mensajeExito) {
        CarritoResponseDTO carritoDTO = carritoService.obtenerCarritoDTO();
        double total = carritoDTO.getProductos()
                .stream()
                .mapToDouble(p -> p.getPrecio() * p.getCantidad())
                .sum();

        model.addAttribute("carrito", carritoDTO);
        model.addAttribute("total", total);

        // Obtener usuario desde el carrito
        CarritoEntity carrito = carritoService.obtenerCarrito();
        if (carrito != null && carrito.getUsuario() != null) {
            model.addAttribute("usuario", carrito.getUsuario());
        }

        if (!mensajeExito.isEmpty()) {
            model.addAttribute("mensajeExito", mensajeExito);
        }

        return "carrito/carrito";
    }



    @PostMapping("/agregar")
    public String agregarProducto(@ModelAttribute CarritoItemRequestDTO itemDTO) {
        carritoService.agregarProductos(itemDTO);
        return "redirect:/carrito";
    }

    @PostMapping("/actualizar")
    public String actualizarProducto(@ModelAttribute CarritoItemRequestDTO itemDTO) {
        carritoService.actualizarCantidad(itemDTO);
        return "redirect:/carrito";
    }

    @PostMapping("/eliminar/{productoId}")
    public String eliminarProducto(@PathVariable Long productoId) {
        carritoService.eliminarProducto(productoId);
        return "redirect:/carrito";
    }

    @PostMapping("/solicitar-pedido")
    public String solicitarPedido(RedirectAttributes redirectAttributes) {
        CarritoEntity carrito = carritoService.obtenerCarrito();
        Long usuarioId = carrito.getUsuario().getId();

        PedidosEntity pedido = pedidoService.crearPedido(carrito.getId(), usuarioId);

        carrito.getItems().clear();
        carritoRepository.save(carrito);

        redirectAttributes.addFlashAttribute("mensajeExito", "¡Pedido solicitado con éxito!");

        return "redirect:/carrito"; 
    }

}
