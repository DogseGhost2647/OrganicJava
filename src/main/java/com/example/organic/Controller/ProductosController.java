package com.example.organic.Controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.Map;

import com.example.organic.Entity.CategoriasEntity;
import com.example.organic.Entity.CondicionesCabellosEntity;
import com.example.organic.Entity.TiposCabellosEntity;
import com.example.organic.Repository.ProductosRepository;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import com.example.organic.Service.EmailMasivoService;
import com.example.organic.Service.ProductosService;
import com.example.organic.Service.TiposCabellosService;
import com.example.organic.util.EstadisticasProductosPdf;
import com.example.organic.util.ListarProductosPdf;

import jakarta.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;

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

    @Autowired
    private EmailMasivoService emailMasivoService;

    @Autowired
    private ProductosRepository productosRepository;

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

        emailMasivoService.enviarCorreoNuevoProducto(
            producto.getNombre(),
            producto.getDescripcion()
        );

        productosService.create(producto);
        return "redirect:/productos";
    }


    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model model) {
        ProductosEntity producto = productosService.getById(id);

        model.addAttribute("producto", producto);
        model.addAttribute("categoria", categoriasService.getAll());
        model.addAttribute("condicionesCabellos", condicionesCabellosService.getAll());
        model.addAttribute("tiposCabellos", tiposCabellosService.getAll());

        return "productos/editar";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizarProducto(
            @PathVariable Long id,
            @ModelAttribute ProductosEntity producto,
            @RequestParam("imagen") MultipartFile imagen
    ) {
        ProductosEntity productoActual = productosService.getById(id);

        producto.setCategoria(categoriasService.getById(producto.getCategoria().getId()));
        producto.setCondicionesCabellos(condicionesCabellosService.getById(producto.getCondicionesCabellos().getId()));
        producto.setTiposCabellos(tiposCabellosService.getById(producto.getTiposCabellos().getId()));

        producto.setId(id);

        if (!imagen.isEmpty()) {
            try {
                String nombreArchivo = UUID.randomUUID() + "_" + imagen.getOriginalFilename();
                Path uploadDir = Paths.get(System.getProperty("user.dir"), "uploads");
                Path rutaArchivo = uploadDir.resolve(nombreArchivo);

                Files.createDirectories(uploadDir);
                imagen.transferTo(rutaArchivo.toFile());

                producto.setImagenUrl("/uploads/" + nombreArchivo);

                System.out.println("📸 Imagen ACTUALIZADA en: " + rutaArchivo.toAbsolutePath());

            } catch (IOException e) {
                System.err.println("Error actualizando imagen:");
                e.printStackTrace();
            }
        } else {
            producto.setImagenUrl(productoActual.getImagenUrl());
        }

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

    @GetMapping("/upload")
    public String showUploadForm(Model model) {

        return "upload";

    }

    @PostMapping("/upload")
    public String uploadExcel(@RequestParam("file") MultipartFile file, Model model) {

        if (file.isEmpty()) {
            model.addAttribute("message", "Por favor, seleccione un archivo.");
            return "upload";
        }

        if (!file.getOriginalFilename().endsWith(".xlsx")) {
            model.addAttribute("message", "Solo se permiten archivos .xlsx.");
            return "upload";
        }

        long productosInsertados = 0;

        try (InputStream is = file.getInputStream()) {
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheetAt(0);

            boolean isHeader = true;
            for (Row row : sheet) {
                if (isHeader) {
                    isHeader = false;
                    continue;

                }

                if (row == null || row.getCell(0) == null) {
                    break;

                }

                    ProductosEntity nuevoProducto = new ProductosEntity();

                Long categoria_id = (long) row.getCell(6).getNumericCellValue();
                CategoriasEntity categoria = categoriasService.getById(categoria_id);
                nuevoProducto.setCategoria(categoria);

                Long condiciones_cabellos_id = (long) row.getCell(7).getNumericCellValue();
                CondicionesCabellosEntity condiciones_cabellos = condicionesCabellosService.getById(condiciones_cabellos_id);
                nuevoProducto.setCondicionesCabellos(condiciones_cabellos);

                Long tipos_cabellos_id = (long) row.getCell(8).getNumericCellValue();
                TiposCabellosEntity tipos_cabellos = tiposCabellosService.getById(tipos_cabellos_id);
                nuevoProducto.setTiposCabellos(tipos_cabellos);

                // IMPORTANTE configurar esto desp -> if (categoria == null)

                nuevoProducto.setNombre(row.getCell(0).getStringCellValue());
                nuevoProducto.setDescripcion(row.getCell(1).getStringCellValue());
                nuevoProducto.setPrecio(row.getCell(2).getNumericCellValue());
                nuevoProducto.setCantidadDisponible((int) row.getCell(3).getNumericCellValue());
                nuevoProducto.setDisponible(row.getCell(4).getBooleanCellValue());
                nuevoProducto.setImagenUrl(row.getCell(5).getStringCellValue());

                productosService.create(nuevoProducto);
                productosInsertados++;

                }

            model.addAttribute("message", "Carga masiva exitosa. Productos insertados: " + productosInsertados);

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "Error durante la carga: " + e.getMessage());
        }

        return "upload";
    }
    

    @GetMapping("/estadisticas/pdf")
    public void generarEstadisticasPdf(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=estadisticas_productos.pdf");

        List<ProductosEntity> productos = productosRepository.findAll();

        if (productos == null || productos.isEmpty()) {
            response.getWriter().write("No hay productos disponibles para generar estadísticas.");
            return;
        }

        EstadisticasProductosPdf exporter = new EstadisticasProductosPdf(productos);
        exporter.export(response);
    }

}

