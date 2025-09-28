package com.example.organic.Entity;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.logging.log4j.simple.internal.SimpleProvider;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "carrito")
public class CarritoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    //@OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL)
    //private Carrito_ProductosEntity carrito_productos;

    //@OneToOne
    //@JoinColumn(name = "usuario_id", unique = true)
    //private UsuarioEntity usuario;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private UsuarioEntity usuario;

    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Carrito_ProductosEntity> items = new ArrayList<>(); // El nombre del campo "items" es lo que determina el getItems() que necesitas

}