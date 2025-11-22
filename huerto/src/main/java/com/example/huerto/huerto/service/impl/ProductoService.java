package com.example.huerto.huerto.service.impl;

import com.example.huerto.huerto.model.Producto;
import com.example.huerto.huerto.repository.ProductoRepository;
import com.example.huerto.huerto.service.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService implements IProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public List<Producto> listarTodos() {
        return productoRepository.findAll();
    }

    @Override
    public Optional<Producto> obtenerPorId(Long id) {
        if (id == null) throw new IllegalArgumentException("El id no puede ser null");
        return productoRepository.findById(id);
    }

    @Override
    public Producto guardar(Producto producto) {
        if (producto == null) throw new IllegalArgumentException("El producto no puede ser null");
        return productoRepository.save(producto);
    }

    @Override
    public Optional<Producto> actualizar(Long id, Producto detalles) {
        if (id == null) throw new IllegalArgumentException("El id no puede ser null");
        if (detalles == null) throw new IllegalArgumentException("El producto no puede ser null");
        return productoRepository.findById(id).map(producto -> {
            producto.setNombre(detalles.getNombre());
            producto.setPrecio(detalles.getPrecio());
            producto.setDescripcion(detalles.getDescripcion());
            producto.setDisponible(detalles.getDisponible());
            producto.setCategoria(detalles.getCategoria());
            producto.setImagen(detalles.getImagen());
            producto.setImagenes(detalles.getImagenes());
            return productoRepository.save(producto);
        });
    }

    @Override
    public void eliminar(Long id) {
        if (id == null) throw new IllegalArgumentException("El id no puede ser null");
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
        }
    }
}