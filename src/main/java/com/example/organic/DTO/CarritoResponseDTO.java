package com.example.organic.DTO;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarritoResponseDTO {

    private Long carritoId;

    private List <ProductoEnCarritoDTO> productos;

}
