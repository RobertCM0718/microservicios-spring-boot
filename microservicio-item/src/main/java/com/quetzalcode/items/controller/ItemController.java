package com.quetzalcode.items.controller;

import com.quetzalcode.items.dto.Item;
import com.quetzalcode.items.dto.Producto;
import com.quetzalcode.items.service.IItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ItemController {

    private final Logger LOG = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    private CircuitBreakerFactory circuitBreakerFactory;

    @Autowired
    @Qualifier("serviceFeign")
    private IItemService iItemService;

    @GetMapping("/listar")
    public List<Item> listar(@RequestParam(name="nombre",required = false) String nombre, @RequestHeader(name="token-request",required = false) String token){
        System.out.println(nombre);
        System.out.println(token);
        return iItemService.findAll();
    }
    @GetMapping("/ver/{id}/cantidad/{cantidad}")
    public Item detalle(@PathVariable Long id,@PathVariable Integer cantidad){
        /*return iItemService.findById(id, cantidad);*/
        return circuitBreakerFactory.create("items").run(() ->  iItemService.findById(id, cantidad)/*, e -> metodoAlternativo(id,cantidad,e)*/);
    }

    public Item metodoAlternativo(Long id, Integer cantidad, Throwable e) {

        LOG.error(e.getMessage());

        Item item = new Item();
        Producto producto = new Producto();

        item.setCantidad(cantidad);
        producto.setId(id);
        producto.setNombre("Camara Sony");
        producto.setPrecio(500.00);
        item.setProducto(producto);
        return item;
    }



}
