package com.example.organic.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.organic.Entity.CondicionesCabellosEntity;
import com.example.organic.Repository.CondicionesCabellosRepository;
import com.example.organic.Service.DAO.IDAO;

@Service
public class CondicionesCabellosService implements IDAO<CondicionesCabellosEntity, Long> {

    @Autowired
    private CondicionesCabellosRepository condicionesCabellosRepository;

    @Override
    public List<CondicionesCabellosEntity> getAll() {
        return condicionesCabellosRepository.findAll();
    }

    @Override
    public CondicionesCabellosEntity getById(Long id) {
        return condicionesCabellosRepository.findById(id).orElse(null);
    }

    @Override
    public CondicionesCabellosEntity create(CondicionesCabellosEntity entity) {
        return condicionesCabellosRepository.save(entity);
    }

    @Override
    public CondicionesCabellosEntity update(CondicionesCabellosEntity entity) {
        if (condicionesCabellosRepository.existsById(entity.getId())) {
            return condicionesCabellosRepository.save(entity);
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        if (condicionesCabellosRepository.existsById(id)) {
            condicionesCabellosRepository.deleteById(id);
        }
    }
}
