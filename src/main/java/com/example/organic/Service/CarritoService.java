package com.example.organic.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;
import com.example.organic.DTO.CarritoItemRequestDTO;
import com.example.organic.DTO.CarritoResponseDTO;
import com.example.organic.DTO.ProductoEnCarritoDTO;
import com.example.organic.Entity.CarritoEntity;
import com.example.organic.Entity.Carrito_ProductosEntity;
import com.example.organic.Entity.ProductosEntity;
import com.example.organic.Entity.UsuarioEntity;
import com.example.organic.Repository.CarritoRepository;
import com.example.organic.Repository.Carrito_ProductosRepository;
import com.example.organic.Repository.ProductosRepository;
import com.example.organic.Repository.UsuarioRepository;
import com.example.organic.Service.DAO.IDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@Service
public class CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private Carrito_ProductosRepository carrito_productosRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProductosRepository productosRepository;

    //metodo para obtener el id del usuario
    private UsuarioEntity obtenerUsuarioLogueado() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        String correo = userDetails.getUsername();

        return usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con email: " + correo));
    }


    //metodo para obtener el carritp de un usuario
    public CarritoEntity obtenerCarrito() {
        UsuarioEntity usuario = obtenerUsuarioLogueado();

        return carritoRepository.findByUsuarioId(usuario.getId())
                .orElseGet(() -> {
                    CarritoEntity nuevo = new CarritoEntity();
                    nuevo.setUsuario(usuario);
                    nuevo.setItems(new ArrayList<>());
                    return carritoRepository.save(nuevo);
                });
    }



    //metodo para añadir productos al carrito
    public void agregarProductos(CarritoItemRequestDTO itemDTO) {
        CarritoEntity carrito = obtenerCarrito(); // ahora ya obtiene al usuario logueado
        ProductosEntity producto = productosRepository.findById(itemDTO.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado."));

        Optional<Carrito_ProductosEntity> itemExistente = carrito_productosRepository
                .findByCarritoIdAndProductoId(carrito.getId(), producto.getId());

        if (itemExistente.isPresent()) {
            Carrito_ProductosEntity item = itemExistente.get();
            item.setCantidad(item.getCantidad() + itemDTO.getCantidad());
            carrito_productosRepository.save(item);
        } else {
            Carrito_ProductosEntity nuevoItem = new Carrito_ProductosEntity();
            nuevoItem.setCarrito(carrito);
            nuevoItem.setProducto(producto);
            nuevoItem.setCantidad(itemDTO.getCantidad());
            carrito_productosRepository.save(nuevoItem);
        }
    }


    //metodo para ver el carrito
    public CarritoResponseDTO obtenerCarritoDTO() {
        CarritoEntity carrito = obtenerCarrito();
        CarritoResponseDTO carritoDTO = new CarritoResponseDTO();
        carritoDTO.setCarritoId(carrito.getId());
        carritoDTO.setProductos(carrito.getItems().stream()
                .map(item -> {
                    ProductoEnCarritoDTO dto = new ProductoEnCarritoDTO();
                    dto.setProductoId(item.getProducto().getId());
                    dto.setNombreProducto(item.getProducto().getNombre());
                    dto.setPrecio(item.getProducto().getPrecio());
                    dto.setCantidad(item.getCantidad());
                    return dto;
                }).collect(Collectors.toList()));
        return carritoDTO;
    }


    //metodo para actualizar la cantidad de un producto
    public void actualizarCantidad(CarritoItemRequestDTO itemDTO) {
        CarritoEntity carrito = obtenerCarrito();
        Carrito_ProductosEntity item = carrito_productosRepository
                .findByCarritoIdAndProductoId(carrito.getId(), itemDTO.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no está en el carrito"));

        item.setCantidad(itemDTO.getCantidad());
        carrito_productosRepository.save(item);
    }


    //metodo para eliminar un producto
    public void eliminarProducto(Long productoId) {
        CarritoEntity carrito = obtenerCarrito();
        Carrito_ProductosEntity item = carrito_productosRepository
                .findByCarritoIdAndProductoId(carrito.getId(), productoId)
                .orElseThrow(() -> new RuntimeException("Producto no está en el carrito"));

        carrito_productosRepository.delete(item);
    }

}
