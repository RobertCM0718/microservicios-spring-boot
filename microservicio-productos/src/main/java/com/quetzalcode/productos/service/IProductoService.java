package com.quetzalcode.productos.service;

import com.quetzalcode.productos.entity.Producto;

import java.util.List;

/**
 *  producto service
 *
 */
public interface IProductoService {
    public List<Producto> findAll();
    public Producto findById(Long id);
}
