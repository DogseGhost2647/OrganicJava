package com.example.organic.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "detalles_pedidos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class DetallesPedidosEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;
    
    @Column(name="pedido_id")
    private Integer pedido;

    @Column(name="producto_id")
    private Integer producto;

    @Column(name="cantidad")
    private Integer cantidad;

    @Column(name="precio_unitario")
    private Double precioUnitario;

    @Column(name="subtotal")
    private Double precioDouble;
    
    //@OneToOne
    //@JoinColumn(name="pedido_id")
    //private PedidosEntity pedidos;
}
