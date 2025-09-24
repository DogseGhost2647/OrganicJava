package com.example.organic.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.organic.Entity.TiposCabellosEntity;

@Repository
public interface TiposCabellosRepository extends JpaRepository<TiposCabellosEntity, Long>{

}
