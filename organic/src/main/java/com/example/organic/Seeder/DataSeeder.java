package com.example.organic.Seeder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.organic.Entity.CategoriasEntity;
import com.example.organic.Entity.CondicionesCabellosEntity;
import com.example.organic.Entity.TiposCabellosEntity;
import com.example.organic.Repository.CategoriasRepository;
import com.example.organic.Repository.CondicionesCabellosRepository;
import com.example.organic.Repository.TiposCabellosRepository;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private CategoriasRepository categoriasRepository;

    @Autowired
    private CondicionesCabellosRepository condicionesRepository;

    @Autowired
    private TiposCabellosRepository tiposRepository;

    @Override
    public void run(String... args) {
        if (categoriasRepository.count() >3 && condicionesRepository.count()>3 && tiposRepository.count()>3) {
            categoriasRepository.deleteAll();
            condicionesRepository.deleteAll();
            tiposRepository.deleteAll();
        }

        if (categoriasRepository.count()==0 && condicionesRepository.count()== 0 && tiposRepository.count()==0) {

        categoriasRepository.save(new CategoriasEntity("Shampoo", "producto para el lavado del cabello"));
        categoriasRepository.save(new CategoriasEntity("Acondicionador", "producto para hidratar y suavizar el cabello"));
        categoriasRepository.save(new CategoriasEntity("Tratamiento", "producto para reparar el cabello"));

        condicionesRepository.save(new CondicionesCabellosEntity("Seco", "Cabello sin brillo"));
        condicionesRepository.save(new CondicionesCabellosEntity("Graso", "cabello con exceso de grasa"));
        condicionesRepository.save(new CondicionesCabellosEntity("Normal", "cabello cuidado"));

        tiposRepository.save(new TiposCabellosEntity("Liso", "cabello manejable y sin ondulaciones"));
        tiposRepository.save(new TiposCabellosEntity("Ondulado", "Cabello con ligeras ondulaciones"));
        tiposRepository.save(new TiposCabellosEntity("Rizado", "Cabello muy ondulado y difícil de manejar"));

        System.out.println("Datos insertados con éxito");
        }
    }
}