package com.example.organic.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductosDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private String precio;
    private Integer cantidadDisponible;
    private Integer categoria;
    private Integer tipoCabello;
    private Integer condicionCabello;
    private Boolean disponible;
}
