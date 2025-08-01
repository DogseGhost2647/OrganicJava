package com.example.organic.Controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.example.organic.Entity.ProductosEntity;
import com.example.organic.Service.CategoriasService;
import com.example.organic.Service.CondicionesCabellosService;
import com.example.organic.Service.ProductosService;
import com.example.organic.Service.TiposCabellosService;
import com.example.organic.util.ListarProductosPdf;



@Controller
@RequestMapping("/productos")
public class ProductosController {

    @Autowired
    private ListarProductosPdf listarProductosPdf;

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
    public String guardarProducto(@ModelAttribute ProductosEntity producto, @RequestParam("imagen")MultipartFile imagen){
        producto.setCategoria(categoriasService.getById(producto.getCategoria().getId()));
        producto.setCondicionesCabellos(condicionesCabellosService.getById(producto.getCondicionesCabellos().getId()));
        producto.setTiposCabellos(tiposCabellosService.getById(producto.getTiposCabellos().getId()));
    
        if (!imagen.isEmpty()) {
            String nombreArchivo = UUID.randomUUID() + "_" + imagen.getOriginalFilename();
            Path uploadDir = Paths.get(System.getProperty("user.dir"), "uploads");
            Path rutaArchivo = uploadDir.resolve(nombreArchivo);

            try {
                Files.createDirectories(uploadDir);
                imagen.transferTo(rutaArchivo.toFile());
                producto.setImagenUrl("/uploads/" + nombreArchivo);
                System.out.println("✅ Imagen guardada en: " + rutaArchivo.toAbsolutePath());
            } catch (IOException e) {
                System.err.println("Error guardando la imagen:");
                e.printStackTrace();
            }
        } else {
            System.err.println("Imagen vacía o no seleccionada");
        }

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
        return "productos/editar";
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

    @Autowired
    @Qualifier("listaProductosPdfView")
    private AbstractPdfView listaProductosPdfView;

    @GetMapping("/list/pdf")
    public ModelAndView generarPdf(@RequestParam(required = false, defaultValue = "todos") String categoria) {
        List<ProductosEntity> productos = productosService.getAll();

        List<ProductosEntity> productosFiltrados;
        if ("todos".equalsIgnoreCase(categoria)) {
            productosFiltrados = productos;
        } else {
            productosFiltrados = productos.stream()
                .filter(p -> p.getCategoria() != null && 
                            p.getCategoria().getNombre().equalsIgnoreCase(categoria))
                .collect(Collectors.toList());
        }

        Map<String, Object> model = new HashMap<>();
        model.put("productos", productosFiltrados);
        model.put("categoriaFiltro", categoria);

        return new ModelAndView(listaProductosPdfView, model);
    }




}

