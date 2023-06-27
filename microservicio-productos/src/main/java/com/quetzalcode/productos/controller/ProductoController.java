package com.quetzalcode.productos.controller;

import com.quetzalcode.commons.entity.Producto;
import com.quetzalcode.productos.service.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RestController
public class ProductoController {

    @Autowired
    private Environment environment;

    @Value("${server.port}")
    private Integer port;
    @Autowired
    private IProductoService iProductoService;

    @GetMapping("/listar")
    public List<Producto> listar(){
        return iProductoService.findAll().stream().map(producto -> {
            producto.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
            //producto.setPort(port);
            return producto;
        }).collect(Collectors.toList());
    }

    @GetMapping("/ver/{id}")
    public Producto detalle(@PathVariable Long id) throws InterruptedException {

        if(id.equals(10L)){
            throw  new IllegalStateException("Producto no disponible!");
        }

        if(id.equals(7L)){
            TimeUnit.SECONDS.sleep(3L);
        }

        Producto producto = iProductoService.findById(id);
        producto.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
        //producto.setPort(port);

        /*try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        return producto;
    }

    @PostMapping("crear")
    public ResponseEntity<?> crear(@RequestBody Producto producto){
        return new ResponseEntity<Producto>(iProductoService.save(producto), HttpStatus.CREATED) ;
    }

    @PutMapping("/editar")
    public ResponseEntity<?> editar(@RequestBody Producto producto){
        Producto productoDb = iProductoService.findById(producto.getId());
        productoDb.setNombre(producto.getNombre());
        productoDb.setPrecio(producto.getPrecio());
        return new ResponseEntity<Producto>(iProductoService.save(productoDb), HttpStatus.CREATED) ;
    }

    @DeleteMapping("/eliminar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id){
        iProductoService.deleteById(id);
    }
}
