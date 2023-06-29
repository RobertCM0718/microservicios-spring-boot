package com.quetzalcode.items.service;

import com.quetzalcode.commons.entity.Producto;
import com.quetzalcode.items.dto.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("serviceRestTemplate")
public class ItemServiceRestTemplate implements IItemService{

    private static final Logger LOG = LoggerFactory.getLogger(ItemServiceRestTemplate.class);

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

    @Override
    public Producto save(Producto producto) {
        HttpEntity<Producto> request = new HttpEntity<Producto>(producto);
        ResponseEntity<Producto> response = restTemplate.exchange("http://microservicio-productos/crear", HttpMethod.POST,request,Producto.class );
        return response.getBody();
    }

    @Override
    public Producto update(Producto producto) {
        HttpEntity<Producto> request = new HttpEntity<Producto>(producto);
        ResponseEntity<Producto> response = restTemplate.exchange("http://microservicio-productos/editar", HttpMethod.PUT,request,Producto.class );
        return response.getBody();
    }

    @Override
    public void delete(Long id) {
        Map<String,String> pathVariables = new HashMap<>();
        pathVariables.put("id",id.toString());

        restTemplate.delete("http://microservicio-productos/eliminar/{id}",pathVariables);
    }
}
