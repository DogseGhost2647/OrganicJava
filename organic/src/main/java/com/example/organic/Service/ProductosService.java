package com.example.organic.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.organic.Entity.ProductosEntity;
import com.example.organic.Repository.ProductosRepository;
import com.example.organic.Service.DAO.IDAO;

public class ProductosService implements IDAO<ProductosEntity, Long> {

    @Autowired
    private IDAO idao;

    @Autowired
    private ProductosRepository productosRepository;

    @Override
    public List<ProductosEntity> getAll() {
        return productosRepository.findAll();
    }

    @Override
    public ProductosEntity getById(Long id) {
        return productosRepository.findById(id).orElse(null);
    }

    @Override
    public ProductosEntity create(ProductosEntity entity){
        return productosRepository.save(entity);
    }

    @Override
    public ProductosEntity update(ProductosEntity entity) {
        if (productosRepository.existsById(entity.getId())) {
            return productosRepository.save(entity);
        }
        return null; // or throw an exception
    }
    
    @Override
    public void delete(Long id) {
        if (productosRepository.existsById(id)) {
            productosRepository.deleteById(id);
        }
    }

}
