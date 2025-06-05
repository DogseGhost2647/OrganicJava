package com.example.organic.Entity;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "productos")
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

    @Column(name = "categoria")
    private Integer categoria;

    @Column(name = "tipo_cabello")
    private Integer tipoCabello;

    @Column(name = "condicion_cabello")
    private Integer condicionCabello;

    @Column(name = "disponible")
    private Boolean disponible;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private CategoriasEntity categorias;

    @ManyToOne
    @JoinColumn(name = "condiciones_cabellos_id")
    private CondicionesCabellosEntity condicionesCabellos;

    @ManyToOne
    @JoinColumn(name = "tipos_cabellos_id")
    private TiposCabellosEntity tiposCabellos;

}
