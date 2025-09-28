package com.example.organic.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.organic.Entity.ProductosEntity;
import com.example.organic.Repository.ProductosRepository;
import com.example.organic.Service.DAO.IDAO;

@Service
public class ProductosService implements IDAO<ProductosEntity, Long> {

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

        Optional<ProductosEntity> existente = productosRepository.findByNombreAndCategoriaAndTiposCabellosAndCondicionesCabellos(
        entity.getNombre(),
        entity.getCategoria(),
        entity.getTiposCabellos(),
        entity.getCondicionesCabellos()
        );

        if(existente.isPresent()){
            ProductosEntity productoExistente = existente.get();
            productoExistente.setCantidadDisponible(
                productoExistente.getCantidadDisponible() + entity.getCantidadDisponible()
            );
            return productosRepository.save(productoExistente);
        } else {
            return productosRepository.save(entity);
        }
    }

    @Override
    public ProductosEntity update(ProductosEntity entity) {
        if (productosRepository.existsById(entity.getId())) {
            return productosRepository.save(entity);
        }
        return null;
    }
    
    @Override
    public void delete(Long id) {
        if (productosRepository.existsById(id)) {
            productosRepository.deleteById(id);
        }
    }

    public long contarProductos() {
    return productosRepository.count();
    }

}
