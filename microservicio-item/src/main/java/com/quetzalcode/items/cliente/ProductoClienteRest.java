package com.quetzalcode.items.cliente;

import com.quetzalcode.commons.entity.Producto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "microservicio-productos")
public interface ProductoClienteRest {

    @GetMapping("/listar")
    public List<Producto> listar();

    @GetMapping("/ver/{id}")
    public Producto detalle(@PathVariable Long id);

    @PostMapping("crear")
    public Producto crear(@RequestBody Producto producto);

    @PutMapping("/editar")
    public Producto editar(@RequestBody Producto producto);

    @DeleteMapping("/eliminar/{id}")
    public void eliminar(@PathVariable Long id);

}
