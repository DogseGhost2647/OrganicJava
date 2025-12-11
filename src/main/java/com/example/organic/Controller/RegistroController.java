package com.example.organic.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.organic.Entity.CarritoEntity;
import com.example.organic.Entity.UsuarioEntity;
import com.example.organic.Repository.CarritoRepository;
import com.example.organic.Service.UsuarioService;
import com.example.organic.DTO.UsuariosDTO;

@Controller
public class RegistroController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private CarritoRepository carritoRepository;

    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model){
        model.addAttribute("usuario", new UsuariosDTO());
        return "registro";
    }

    @PostMapping("/registro")
    public String procesarRegistro(@ModelAttribute("usuario") UsuariosDTO dto, Model model) {
        try {
            // Validación de contraseñas
            if(!dto.getPassword().equals(dto.getConfirmPassword())) {
                model.addAttribute("mensajeError", "Las contraseñas no coinciden");
                model.addAttribute("usuario", dto);
                return "registro";
            }

            // Crear el usuario
            UsuarioEntity usuario = new UsuarioEntity();
            usuario.setNombre(dto.getNombre());
            usuario.setCorreo(dto.getCorreo());
            usuario.setPassword(dto.getPassword());

            CarritoEntity carrito = new CarritoEntity();
            carrito.setUsuario(usuario);
            carritoRepository.save(carrito);


            // Llamar al método que crea usuario + carrito
            usuarioService.registrar(usuario);

            return "redirect:/login?registroExitoso";

        } catch (IllegalArgumentException e) {
            model.addAttribute("mensajeError", e.getMessage());
            model.addAttribute("usuario", dto);
            return "registro";
        } catch (Exception e) {
            model.addAttribute("mensajeError", "Ocurrió un error inesperado. Intenta nuevamente.");
            model.addAttribute("usuario", dto);
            return "registro";
        }
    }
}
