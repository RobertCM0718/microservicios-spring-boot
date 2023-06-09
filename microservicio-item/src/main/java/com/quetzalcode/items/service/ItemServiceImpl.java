package com.quetzalcode.items.service;

import com.quetzalcode.items.dto.Item;
import com.quetzalcode.items.dto.Producto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("serviceRestTemplate")
public class ItemServiceImpl implements IItemService{

    private static final Logger LOG = LoggerFactory.getLogger(ItemServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;
    @Override
    public List<Item> findAll() {
        LOG.info("restTemplate findAll");
        List<Producto> productos = Arrays.asList(restTemplate.getForObject("http://microservicio-productos/listar",Producto[].class));
        return productos.stream().map(producto -> new Item(producto,1)).collect(Collectors.toList());
    }

    @Override
    public Item findById(Long id, Integer cantidad) {
        LOG.info("restTemplate findById("+id+","+cantidad+")");
        Map<String,String> pathVariables = new HashMap<>();
        pathVariables.put("id",id.toString());
        Producto producto = restTemplate.getForObject("http://microservicio-productos/ver/{id}",Producto.class,pathVariables);
        return new Item(producto,cantidad);
    }
}
