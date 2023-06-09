package com.quetzalcode.items.service;

import com.quetzalcode.items.dto.Item;
import com.quetzalcode.items.dto.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements IItemService{
    @Autowired
    private RestTemplate restTemplate;
    @Override
    public List<Item> findAll() {
        List<Producto> productos = Arrays.asList(restTemplate.getForObject("http://localhost:8080/listar",Producto[].class));
        return productos.stream().map(producto -> new Item(producto,1)).collect(Collectors.toList());
    }

    @Override
    public Item findById(Long id, Integer cantidad) {
        Map<String,String> pathVariables = new HashMap<>();
        pathVariables.put("id",id.toString());
        Producto producto = restTemplate.getForObject("http://localhost:8080/ver/{id}",Producto.class,pathVariables);
        return new Item(producto,cantidad);
    }
}
