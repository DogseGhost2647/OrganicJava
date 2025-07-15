package com.example.organic.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.organic.Entity.UsuarioEntity;

public interface UsuarioRepository extends JpaRepository <UsuarioEntity, Long> {
    UsuarioEntity findByCorreo(String correo);
    boolean existsByCorreo(String correo);
}
