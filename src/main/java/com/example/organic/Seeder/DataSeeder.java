package com.example.organic.Seeder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.organic.Entity.CategoriasEntity;
import com.example.organic.Entity.CondicionesCabellosEntity;
import com.example.organic.Entity.TiposCabellosEntity;
import com.example.organic.Entity.UsuarioEntity;
import com.example.organic.Service.UsuarioService;
import com.example.organic.Repository.CategoriasRepository;
import com.example.organic.Repository.CondicionesCabellosRepository;
import com.example.organic.Repository.TiposCabellosRepository;
import com.example.organic.Repository.UsuarioRepository;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private CategoriasRepository categoriasRepository;

    @Autowired
    private CondicionesCabellosRepository condicionesRepository;

    @Autowired
    private TiposCabellosRepository tiposRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public void run(String... args) throws Exception {

        // --------- CATEGORÍAS ---------
        if (!categoriasRepository.existsByNombre("Shampoo")) {
            categoriasRepository.save(new CategoriasEntity("Shampoo", "producto para el lavado del cabello"));
        }
        if (!categoriasRepository.existsByNombre("Acondicionador")) {
            categoriasRepository.save(new CategoriasEntity("Acondicionador", "producto para hidratar y suavizar el cabello"));
        }
        if (!categoriasRepository.existsByNombre("Tratamiento")) {
            categoriasRepository.save(new CategoriasEntity("Tratamiento", "producto para reparar el cabello"));
        }

        // --------- CONDICIONES ---------
        if (!condicionesRepository.existsByNombre("Seco")) {
            condicionesRepository.save(new CondicionesCabellosEntity("Seco", "Cabello sin brillo"));
        }
        if (!condicionesRepository.existsByNombre("Graso")) {
            condicionesRepository.save(new CondicionesCabellosEntity("Graso", "cabello con exceso de grasa"));
        }
        if (!condicionesRepository.existsByNombre("Normal")) {
            condicionesRepository.save(new CondicionesCabellosEntity("Normal", "cabello cuidado"));
        }

        // --------- TIPOS DE CABELLO ---------
        if (!tiposRepository.existsByNombre("Liso")) {
            tiposRepository.save(new TiposCabellosEntity("Liso", "cabello manejable y sin ondulaciones"));
        }
        if (!tiposRepository.existsByNombre("Ondulado")) {
            tiposRepository.save(new TiposCabellosEntity("Ondulado", "Cabello con ligeras ondulaciones"));
        }
        if (!tiposRepository.existsByNombre("Rizado")) {
            tiposRepository.save(new TiposCabellosEntity("Rizado", "Cabello muy ondulado y difícil de manejar"));
        }

        System.out.println("✔ Datos base creados o verificados correctamente.");

        // --------- ADMIN ---------
        if (!usuarioRepository.existsByCorreo("admin@gmail.com")) {

            UsuarioEntity admin = new UsuarioEntity();
            admin.setCorreo("admin@gmail.com");
            admin.setNombre("Administrador");
            admin.setEsadmin(true);
            admin.setPassword("admin1234");

            usuarioService.registrar(admin); // CREA ADMIN + CARRITO AUTOMÁTICO

            System.out.println("✔ Usuario admin creado con carrito.");
        }
    }
}
