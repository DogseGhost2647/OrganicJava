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
public class DetallesPedidosDTO {

    private long id;
    
    private Integer pedido;

    private Integer producto;

    private Integer cantidad;

    private Double precioUnitario;

    private Double precioDouble;
}
