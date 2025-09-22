package com.example.organic.Controller;

//import ch.qos.logback.core.model.Model;
import com.example.organic.DTO.CarritoItemRequestDTO;
import com.example.organic.DTO.CarritoResponseDTO;
import com.example.organic.Service.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/carrito")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @PostMapping("/agregar/{usuarioId}")
    public String agregarProducto(@PathVariable Long usuarioId, @ModelAttribute CarritoItemRequestDTO itemDTO) {

        carritoService.agregarProductos(usuarioId, itemDTO);
        return "redirect:/carrito/" + usuarioId;

    }

    @GetMapping("/{usuarioId")
    public String verCarrito(@PathVariable Long usuarioId, Model model) {

        CarritoResponseDTO carritoDTO = carritoService.obtenerCarritoDTO(usuarioId);
        model.addAttribute("carrito", carritoDTO);
        return "carrito/carrito";

    }

}
