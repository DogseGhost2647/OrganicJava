package com.example.organic.Entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class Carrito_ProductosId implements Serializable{
    
    private Long carrito;
    private Long producto;
    
    public Carrito_ProductosId(){}
    
    public Carrito_ProductosId(Long carrito, Long producto){
        
        this.carrito = carrito;
        this.producto = producto;
        
    }
    
    @Override
    public  boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Carrito_ProductosId that = (Carrito_ProductosId) o;
        return Objects.equals(carrito, that.carrito) && Objects.equals(producto, that.producto);

    }
    
    @Override
    public int hashCode() {
        return Objects.hash(carrito, producto);
    }
    
}
