package com.example.organic.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.organic.Entity.CondicionesCabellosEntity;

@Repository
public interface CondicionesCabellosRepository extends JpaRepository <CondicionesCabellosEntity, Long>{

}
