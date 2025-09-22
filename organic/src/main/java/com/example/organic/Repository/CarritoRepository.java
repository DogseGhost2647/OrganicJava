package com.example.organic.Repository;

import com.example.organic.Entity.CarritoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarritoRepository extends JpaRepository<CarritoEntity, Long>{

    //@Override
    Optional<CarritoEntity> findByUsuarioId(Long usuarioId);

}
