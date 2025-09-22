package com.example.organic.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "carrito_productos")
@IdClass(Carrito_ProductosId.class)
public class Carrito_ProductosEntity {

    @Id
    @ManyToOne
    @JoinColumn(name = "carrito_id")
    private CarritoEntity carrito;

    @Id
    @ManyToOne
    @JoinColumn(name = "productos_id")
    private ProductosEntity producto;

    @Column(name = "cantidad")
    private Integer cantidad;

}
