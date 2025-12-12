package com.example.organic.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductoEnCarritoDTO {

    private Long productoId;

    private String nombreProducto;

    private double precio;

    private int cantidad;

}