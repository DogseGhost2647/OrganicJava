package com.example.organic.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.organic.Entity.ProductosEntity;

@Repository
public interface ProductosRepository extends JpaRepository<ProductosEntity, Long>{

}
