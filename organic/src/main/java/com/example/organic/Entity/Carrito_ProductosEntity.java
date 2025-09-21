package com.example.organic.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "carrito_productos")
public class Carrito_ProductosEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "cantidad")
    private Integer cantidad;

    @ManyToOne
    @JoinColumn(name = "carrito_id")
    private CarritoEntity carrito;

    @ManyToOne
    @JoinColumn(name = "productos_id")
    private ProductosEntity producto;

}
