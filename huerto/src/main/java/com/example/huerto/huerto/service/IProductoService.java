package com.example.huerto.huerto.service;

import com.example.huerto.huerto.model.Producto;

import java.util.List;
import java.util.Optional;

public interface IProductoService {

    List<Producto> listarTodos();
    Optional<Producto> obtenerPorId(Long id);
    Producto guardar(Producto producto);
    Optional<Producto> actualizar(Long id, Producto producto);

    void eliminar(Long id);
}
