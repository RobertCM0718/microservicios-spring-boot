package com.quetzalcode.items.service;

import com.quetzalcode.items.dto.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ItemServiceImpl implements IItemService{
    @Autowired
    private RestTemplate restTemplate;
    @Override
    public List<Item> findAll() {
        return null;
    }

    @Override
    public Item findById(Long id, Integer cantidad) {
        return null;
    }
}
