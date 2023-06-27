package com.quetzalcode.items.service;

import com.quetzalcode.commons.entity.Producto;
import com.quetzalcode.items.dto.Item;


import java.util.List;

public interface  IItemService {
    public List<Item> findAll();
    public Item findById(Long id,Integer cantidad);

    public Producto save(Producto producto);

    public Producto update(Producto producto);
    public void delete(Long id);
}
