package com.example.organic.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.organic.Entity.DetallesPedidosEntity;

@Repository
public interface DetallesPedidosRepository extends JpaRepository<DetallesPedidosEntity, Long>{

}
