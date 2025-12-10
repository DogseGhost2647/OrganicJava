package com.example.organic.DTO;

import com.example.organic.Entity.CarritoEntity;

import jakarta.persistence.OneToOne;
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
public class UsuariosDTO {

    private long id;

    private String nombre;

    private String correo;

    private String password;

    private String confirmPassword;

    private boolean esadmin;

    private CarritoEntity carrito;
}
