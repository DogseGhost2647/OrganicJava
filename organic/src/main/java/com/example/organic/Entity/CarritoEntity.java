package com.example.organic.Entity;
import jakarta.persistence.*;

@Entity
@Table(name = "carrito")
public class CarritoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @OneToOne(mappedBy = "carrito", cascade = CascadeType.ALL)
    private Carrito_ProductosEntity carrito_productos;

    @OneToOne
    @JoinColumn(name = "usuario_id", unique = true)
    private UsuarioEntity usuario;

}