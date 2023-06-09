package com.quetzalcode.items.service;

import com.quetzalcode.items.dto.Item;

import java.util.List;

public interface IItemService {
    public List<Item> findAll();
    public Item findById(Long id,Integer cantidad);
}
