package com.example.organic.Repository;

import com.example.organic.Entity.Carrito_ProductosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface Carrito_ProductosRepository extends JpaRepository<Carrito_ProductosEntity, Long> {

    //@Override
    Optional<Carrito_ProductosRepository> findByCarritoIdAndProductoId(Long carritoId, Long productoId);

}
