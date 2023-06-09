package com.quetzalcode.items.service;

import com.quetzalcode.items.cliente.ProductoClienteRest;
import com.quetzalcode.items.dto.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
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
}
