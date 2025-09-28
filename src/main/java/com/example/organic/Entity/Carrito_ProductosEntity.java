package com.example.organic.Entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "carrito_productos")
public class Carrito_ProductosEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "carrito_id")
    private CarritoEntity carrito;

    @ManyToOne
    @JoinColumn(name = "productos_id")
    private ProductosEntity producto;

    @Column(name = "cantidad")
    private Integer cantidad;

}
