package com.example.organic.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.organic.Entity.CategoriasEntity;

@Repository
public interface CategoriasRepository extends JpaRepository<CategoriasEntity, Long> {

}
