package com.example.organic.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.organic.Entity.UsuarioEntity;
import com.example.organic.Repository.UsuarioRepository;
import com.example.organic.Service.DAO.IDAO;

@Service
public class UsuarioService implements IDAO<UsuarioEntity, Long> {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<UsuarioEntity> getAll() {
        return usuarioRepository.findAll();
    }

    @Override
    public UsuarioEntity getById(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @Override
    public UsuarioEntity create(UsuarioEntity entity) {
        Optional<UsuarioEntity> existente = usuarioRepository.findByCorreo(entity.getCorreo());
        if (existente.isPresent()) {
            throw new IllegalArgumentException("Ya existe un usuario con ese correo");
        }
        return usuarioRepository.save(entity);
    }


    @Override
    public UsuarioEntity update(UsuarioEntity entity) {
        if (usuarioRepository.existsById(entity.getId())) {
            return usuarioRepository.save(entity);
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
        }
    }

    public long contarUsuarios() {
    return usuarioRepository.count();
    }

}
