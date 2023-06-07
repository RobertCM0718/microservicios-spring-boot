package com.quetzalcode.productos.service;

import com.quetzalcode.productos.entity.Producto;

import java.util.List;

public interface IProductoService {
    public List<Producto> findAll();
    public Producto findById(Long id);
}
