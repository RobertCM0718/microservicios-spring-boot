package com.quetzalcode.productos.service;





import com.quetzalcode.commons.entity.Producto;

import java.util.List;

/**
 *  producto service
 *
 */
public interface IProductoService {
    public List<Producto> findAll();
    public Producto findById(Long id);
    public Producto save(Producto producto);
    public void deleteById(Long id);
}
