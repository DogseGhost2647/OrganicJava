package com.example.organic.Service;

import com.example.organic.DTO.CarritoResponseDTO;
import com.example.organic.DTO.ProductoEnCarritoDTO;
import com.example.organic.Entity.CarritoEntity;
import com.example.organic.Entity.Carrito_ProductosEntity;
import com.example.organic.Entity.PedidosEntity;
import com.example.organic.Entity.ProductosEntity;
import com.example.organic.Entity.UsuarioEntity;
import com.example.organic.Repository.CarritoRepository;
import com.example.organic.Repository.PedidosRepository;
import com.example.organic.Repository.ProductosRepository;
import com.example.organic.Repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PedidoService {

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private PedidosRepository pedidosRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProductosRepository productosRepository;

    // Método para calcular el total de un carrito
    public double calcularTotal(Long carritoId) {
        CarritoEntity carritoEntity = carritoRepository.findById(carritoId)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        CarritoResponseDTO carritoDTO = new CarritoResponseDTO();
        carritoDTO.setCarritoId(carritoEntity.getId());
        carritoDTO.setProductos(
                carritoEntity.getItems().stream().map(item -> {
                    ProductoEnCarritoDTO dto = new ProductoEnCarritoDTO();
                    dto.setProductoId(item.getProducto().getId());
                    dto.setNombreProducto(item.getProducto().getNombre());
                    dto.setPrecio(item.getProducto().getPrecio());
                    dto.setCantidad(item.getCantidad());
                    return dto;
                }).toList()
        );

        double total = 0;
        for (ProductoEnCarritoDTO producto : carritoDTO.getProductos()) {
            total += producto.getCantidad() * producto.getPrecio();
        }

        return total;
    }

    // Método para crear un pedido
    public PedidosEntity crearPedido(Long carritoId, Long usuarioId) {
        double total = calcularTotal(carritoId);

        UsuarioEntity usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        CarritoEntity carritoEntity = carritoRepository.findById(carritoId)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        for (Carrito_ProductosEntity item : carritoEntity.getItems()) {
            ProductosEntity producto = item.getProducto();
            int stockActual = producto.getCantidadDisponible();
            int cantidadComprada = item.getCantidad();

            if (stockActual < cantidadComprada) {
                throw new RuntimeException("Stock insuficiente para: " + producto.getNombre());
            }

            producto.setCantidadDisponible(stockActual - cantidadComprada);
            productosRepository.save(producto); // Guardar cambio de stock
        }

        // Crear pedido
        PedidosEntity pedido = new PedidosEntity();
        pedido.setUsuarioId(usuarioId);
        pedido.setFecha(LocalDateTime.now());
        pedido.setTotal(total);
        pedido.setDireccionEntrega(usuario.getDireccion());

        return pedidosRepository.save(pedido);
    }

}
