package com.example.organic.DTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CarritoItemRequestDTO {

    private Long productoId;

    private int cantidad;

}
