package com.example.organic.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.organic.Entity.CategoriasEntity;
import com.example.organic.Service.CategoriasService;

@RestController
@RequestMapping("/categorias")
public class CategoriasController {

    @Autowired
    private CategoriasService categoriasService;

    @GetMapping
    public List<CategoriasEntity> getAllCategorias() {
        return categoriasService.getAll();
    }
    
    @GetMapping("/{id}")
    public CategoriasEntity getCategoriaById(Long id) {
        return categoriasService.getById(id);
    }

    @PostMapping
    public CategoriasEntity createCategoria(CategoriasEntity categoria) {
        return categoriasService.create(categoria);
    }

    @PutMapping("/{id}")
    public CategoriasEntity updateCategoria(Long id, CategoriasEntity categoria) {
        return categoriasService.update(categoria);
    }

    @DeleteMapping("/{id}")
    public void deleteCategoria(Long id) {
        categoriasService.delete(id);
    }

}
