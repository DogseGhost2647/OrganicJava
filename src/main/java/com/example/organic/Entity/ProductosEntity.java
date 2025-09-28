package com.example.organic.Entity;

import java.util.List;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "productos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ProductosEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "precio")
    private Double precio;

    @Column(name = "cantidad_disponible")
    private Integer cantidadDisponible;

    @Column(name = "disponible")
    private Boolean disponible;

    @Column(name= "imagen_url")
    private String imagenUrl;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
    private List<Carrito_ProductosEntity> carrito_productos;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private CategoriasEntity categoria;

    @ManyToOne
    @JoinColumn(name = "condiciones_cabellos_id")
    private CondicionesCabellosEntity condicionesCabellos;

    @ManyToOne
    @JoinColumn(name = "tipos_cabellos_id")
    private TiposCabellosEntity tiposCabellos;

}
