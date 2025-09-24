package com.example.organic.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.organic.Entity.CategoriasEntity;
import com.example.organic.Entity.CondicionesCabellosEntity;
import com.example.organic.Entity.ProductosEntity;
import com.example.organic.Entity.TiposCabellosEntity;

@Repository
public interface ProductosRepository extends JpaRepository<ProductosEntity, Long>{
    Optional<ProductosEntity> findByNombreAndCategoriaAndTiposCabellosAndCondicionesCabellos(
        String nombre,
        CategoriasEntity categoria,
        TiposCabellosEntity tipo,
        CondicionesCabellosEntity condicion
    );
}
