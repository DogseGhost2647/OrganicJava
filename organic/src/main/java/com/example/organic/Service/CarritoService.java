package com.example.organic.Service;

import java.util.List;
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

    //metodo para obtener el carritp de un usuario
    public CarritoEntity obtenerCarrito(Long usuarioId) {
        Optional<CarritoEntity> carritoExistente = carritoRepository.findByUsuarioId(usuarioId);

        if (carritoExistente.isPresent()) {
            return carritoExistente.get();
        }else{
            //si el carrito no existe lo crea
            UsuarioEntity usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado."));
            CarritoEntity nuevoCarrito = new CarritoEntity();
            return carritoRepository.save(nuevoCarrito);
        }
    }

    //metodo para añadir productos al carrito
    public void agregarProductos(Long usuarioId, CarritoItemRequestDTO itemDTO) {
        CarritoEntity carrito = obtenerCarrito(usuarioId);

        ProductosEntity productos = productosRepository.findById(itemDTO.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado."));

        Optional<Carrito_ProductosRepository> itemExistente = carrito_productosRepository.findByCarritoIdAndProductoId(carrito.getId(), productos.getId());

        if (itemExistente.isPresent()) {
            //si el producto ya esta en el carrito, se actualiza la cantidad
            Carrito_ProductosEntity item = (Carrito_ProductosEntity) itemExistente.get();
            item.setCantidad(item.getCantidad() + itemDTO.getCantidad());
            carrito_productosRepository.save(item);

        }else{
            //si el producto no esta, crea un nuevo item
            Carrito_ProductosEntity nuevoItem = new Carrito_ProductosEntity();
            nuevoItem.setCarrito(carrito);
            nuevoItem.setProducto(productos);
            nuevoItem.setCantidad(itemDTO.getCantidad());
            carrito_productosRepository.save(nuevoItem);
        }
    }

    //
    public CarritoResponseDTO obtenerCarritoDTO(Long usuarioId) {
        CarritoEntity carrito = obtenerCarrito(usuarioId);

        CarritoResponseDTO carritoDTO = new CarritoResponseDTO();
        carritoDTO.setCarritoId(carrito.getId());

        carritoDTO.setProductos(carrito.getItems().stream
                .map(item -> {
            ProductoEnCarritoDTO dto = new ProductoEnCarritoDTO();
            dto.setNombreProducto(item.getProducto().getNombre());
            dto.setPrecio(item.getProducto().getPrecio());
            dto.setCantidad(item.getCantidad());
            return dto;

        })
                .collect(Collectors.toList()));

        return carritoDTO;

    }

}
