package com.example.organic.Repository;

import com.example.organic.Entity.Carrito_ProductosId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface Carrito_ProductosRepository extends JpaRepository<Carrito_ProductosRepository, Carrito_ProductosId> {

    //@Override
    Optional<Carrito_ProductosRepository> findByCarritoIdAndProductoId(Long carritoId, Long productoId);

}
