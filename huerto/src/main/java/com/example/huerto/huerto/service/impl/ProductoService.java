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
        return productoRepository.findById(id);
    }

    @Override
    public Producto guardar(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    public Producto actualizar(Long id, Producto detalles) {
        return productoRepository.findById(id).map(producto -> {
            producto.setNombre(detalles.getNombre());
            producto.setPrecio(detalles.getPrecio());
            producto.setDescripcion(detalles.getDescripcion());
            producto.setDisponible(detalles.getDisponible());
            producto.setCategoria(detalles.getCategoria());
            producto.setImagen(detalles.getImagen());
            return productoRepository.save(producto);
        }).orElse(null);
    }

    @Override
    public void eliminar(Long id) {
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
        }
    }
}
