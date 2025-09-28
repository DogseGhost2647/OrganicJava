package com.example.organic.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.organic.Entity.TiposCabellosEntity;
import com.example.organic.Repository.TiposCabellosRepository;
import com.example.organic.Service.DAO.IDAO;

@Service
public class TiposCabellosService implements IDAO<TiposCabellosEntity, Long> {

    @Autowired
    private TiposCabellosRepository tiposCabellosRepository;

    @Override
    public List<TiposCabellosEntity> getAll() {
        return tiposCabellosRepository.findAll();
    }

    @Override
    public TiposCabellosEntity getById(Long id) {
        return tiposCabellosRepository.findById(id).orElse(null);
    }

    @Override
    public TiposCabellosEntity create(TiposCabellosEntity entity) {
        return tiposCabellosRepository.save(entity);
    }

    @Override
    public TiposCabellosEntity update(TiposCabellosEntity entity) {
        if (tiposCabellosRepository.existsById(entity.getId())) {
            return tiposCabellosRepository.save(entity);
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        if (tiposCabellosRepository.existsById(id)) {
            tiposCabellosRepository.deleteById(id);
        }
    }
}
