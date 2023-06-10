package com.quetzalcode.productos.controller;

import com.quetzalcode.productos.entity.Producto;
import com.quetzalcode.productos.service.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductoController {

    //@Autowired
    //private Environment environment;

    @Value("${server.port}")
    private Integer port;
    @Autowired
    private IProductoService iProductoService;

    @GetMapping("/listar")
    public List<Producto> listar(){
        return iProductoService.findAll().stream().map(producto -> {
            //producto.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
            producto.setPort(port);
            return producto;
        }).collect(Collectors.toList());
    }

    @GetMapping("/ver/{id}")
    public Producto detalle(@PathVariable Long id){
        Producto producto = iProductoService.findById(id);
        //producto.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
        producto.setPort(port);

        /*try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        return producto;
    }
}
