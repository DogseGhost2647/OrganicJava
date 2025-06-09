package com.example.organic.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.organic.Entity.CategoriasEntity;
import com.example.organic.Repository.CategoriasRepository;
import com.example.organic.Service.DAO.IDAO;

public class CategoriasService implements IDAO<CategoriasEntity, Long> {

    @Autowired
    private IDAO idao;

    @Autowired
    private CategoriasRepository categoriasRepository;

    @Override
    public List<CategoriasEntity> getAll() {
        return categoriasRepository.findAll();
    }

    @Override
    public CategoriasEntity getById(Long id) {
        return categoriasRepository.findById(id).orElse(null);
    }

    @Override
    public CategoriasEntity create(CategoriasEntity entity) {
        return categoriasRepository.save(entity);
    }

    @Override
    public CategoriasEntity update(CategoriasEntity entity) {
        if (categoriasRepository.existsById(entity.getId())) {
            return categoriasRepository.save(entity);
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        if (categoriasRepository.existsById(id)) {
            categoriasRepository.deleteById(id);
        }
    }
}
