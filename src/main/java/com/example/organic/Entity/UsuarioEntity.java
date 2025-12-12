package com.example.organic.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "correo", unique = true , nullable = false)
    private String correo;

    @Column(name="contraseña")
    private String password;

    @Column(name="esadmin")
    private boolean esadmin;

    @Column(name="direccion")
    private String direccion;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private CarritoEntity carrito;

    public String getPassword() {
        return password;
    }

    public boolean EsAdmin(){
        return esadmin;
    }
    
    public void setEsadmin(boolean esadmin) {
    this.esadmin = esadmin;
    }

}

