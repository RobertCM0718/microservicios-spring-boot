package com.quetzalcode.items.service;

import com.quetzalcode.commons.entity.Producto;
import com.quetzalcode.items.cliente.ProductoClienteRest;
import com.quetzalcode.items.dto.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("serviceFeign")
public class ItemServiceFeign implements IItemService{

    @Autowired
    private ProductoClienteRest productoClienteRest;

    @Override
    public List<Item> findAll() {
        return productoClienteRest.listar().stream().map(producto -> new Item(producto,1)).collect(Collectors.toList());
    }

    @Override
    public Item findById(Long id, Integer cantidad) {
        return new Item(productoClienteRest.detalle(id),cantidad) ;
    }

    @Override
    public Producto save(Producto producto) {
        return productoClienteRest.crear(producto);
    }

    @Override
    public Producto update(Producto producto) {
        return productoClienteRest.editar(producto);
    }

    @Override
    public void delete(Long id) {
        productoClienteRest.eliminar(id);
    }
}
