package com.example.organic.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.organic.Entity.ProductosEntity;
import com.example.organic.Service.CategoriasService;
import com.example.organic.Service.CondicionesCabellosService;
import com.example.organic.Service.ProductosService;
import com.example.organic.Service.TiposCabellosService;



@Controller
@RequestMapping("/productos")
public class ProductosController {

    @Autowired
    private ProductosService productosService;

    @Autowired
    private CategoriasService categoriasService;   
    
    @Autowired
    private CondicionesCabellosService condicionesCabellosService;
    
    @Autowired
    private TiposCabellosService tiposCabellosService;

    @GetMapping
    public String listarProductos(Model model){
        model.addAttribute("productos", productosService.getAll());
        return "productos/list";
    }

    @GetMapping("/crear")
    public String mostrarFormularioCreacion(Model model){
        model.addAttribute("producto", new ProductosEntity());
        model.addAttribute("categoria", categoriasService.getAll());
        model.addAttribute("condicionesCabellos", condicionesCabellosService.getAll());
        model.addAttribute("tiposCabellos", tiposCabellosService.getAll());
        return "productos/create";
    }

    @PostMapping("/guardar")
    public String guardarProducto(@ModelAttribute ProductosEntity producto){
        producto.setCategoria(categoriasService.getById(producto.getCategoria().getId()));
        producto.setCondiciones_cabellos(condicionesCabellosService.getById(producto.getCondiciones_cabellos().getId()));
        producto.setTiposCabellos(tiposCabellosService.getById(producto.getTiposCabellos().getId()));
    
        productosService.create(producto);
        return "redirect:/productos";
    }


    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model model){
        ProductosEntity producto = productosService.getById(id);
        model.addAttribute("producto", producto);
        model.addAttribute("categoria", categoriasService.getAll());
        model.addAttribute("condicionesCabellos", condicionesCabellosService.getAll());
        model.addAttribute("tiposCabellos", tiposCabellosService.getAll());
        return "productos/edit";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizarProducto(@PathVariable Long id, @ModelAttribute ProductosEntity producto) {
        producto.setId(id);
        productosService.update(producto);
        return "redirect:/productos";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Long id) {
        productosService.delete(id);
        return "redirect:/productos";
    }
}
