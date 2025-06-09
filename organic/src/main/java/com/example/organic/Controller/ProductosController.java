package com.example.organic.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.organic.Entity.ProductosEntity;
import com.example.organic.Service.ProductosService;

@RestController
@RequestMapping("/productos")
public class ProductosController {

    @Autowired
    private ProductosService productosService;
    
    @GetMapping
    public List<ProductosEntity> getAllProductos() {
        return productosService.getAll();
    }

    @GetMapping("/{id}")
    public ProductosEntity getProductoById(Long id) {
        return productosService.getById(id);
    }

    @PostMapping
    public ProductosEntity createProducto(ProductosEntity producto) {
        return productosService.create(producto);
    }

    @PutMapping("/{id}")
    public ProductosEntity updateProducto(Long id, ProductosEntity producto) {
        return productosService.update(producto);
    }

    @DeleteMapping("/{id}")
    public void deleteProducto(Long id) {
        productosService.delete(id);
    }
}
