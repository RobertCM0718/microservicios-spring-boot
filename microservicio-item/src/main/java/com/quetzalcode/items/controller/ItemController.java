package com.quetzalcode.items.controller;

import com.quetzalcode.commons.entity.Producto;
import com.quetzalcode.items.dto.Item;
import com.quetzalcode.items.service.IItemService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RefreshScope
@RestController
public class ItemController {
    private final Logger LOG = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    private Environment environment;

    @Value("${configuracion.texto}")
    private String texto;

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
        return circuitBreakerFactory.create("items").run(() ->  iItemService.findById(id, cantidad), e -> metodoAlternativo(id,cantidad,e));
    }

    @CircuitBreaker(name = "items", fallbackMethod = "metodoAlternativo")
    @GetMapping("/ver2/{id}/cantidad/{cantidad}")
    public Item detalle2(@PathVariable Long id,@PathVariable Integer cantidad){
        return iItemService.findById(id, cantidad);
    }

    @CircuitBreaker(name = "items", fallbackMethod = "metodoAlternativo2")
    @TimeLimiter(name = "items" )
    @GetMapping("/ver3/{id}/cantidad/{cantidad}")
    public CompletableFuture<Item> detalle3(@PathVariable Long id, @PathVariable Integer cantidad){
        return CompletableFuture.supplyAsync(() -> iItemService.findById(id, cantidad));
    }

    public Item metodoAlternativo(Long id, Integer cantidad, Throwable e) {

        LOG.error(e.getMessage());

        Item item = new Item();
        Producto producto = new Producto();

        item.setCantidad(cantidad);
        producto.setId(id);
        producto.setNombre("Uuuuuu ya trono");
        producto.setPrecio(500.00);
        item.setProducto(producto);
        return item;
    }

    @PostMapping("crear")
    public ResponseEntity<?> crear(@RequestBody Producto producto){
        return new ResponseEntity<Producto>(iItemService.save(producto), HttpStatus.CREATED) ;
    }

    @PutMapping("/editar")
    public ResponseEntity<?> editar(@RequestBody Producto producto){
        return new ResponseEntity<Producto>(iItemService.update(producto), HttpStatus.CREATED) ;
    }

    @DeleteMapping("/eliminar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id){
        iItemService.delete(id);
    }

    public CompletableFuture<Item> metodoAlternativo2(Long id, Integer cantidad, Throwable e) {

        LOG.error(e.getMessage());

        Item item = new Item();
        Producto producto = new Producto();

        item.setCantidad(cantidad);
        producto.setId(id);
        producto.setNombre("Uuuuuu ya trono");
        producto.setPrecio(500.00);
        item.setProducto(producto);
        return CompletableFuture.supplyAsync(() -> item);
    }

    @GetMapping("/obtener-config")
    public ResponseEntity<?> obtenerConfig(@Value("${server.port}")String puerto){
        Map<String, String> json = new HashMap<>();
        json.put("texto",texto);
        json.put("puerto",puerto);

        if (environment.getActiveProfiles().length > 0 && environment.getActiveProfiles()[0].equals("dev")){
            json.put("autor.nombre", environment.getProperty("configuracion.autor.nombre"));
            json.put("autor.email", environment.getProperty("configuracion.autor.email"));
        }

        LOG.info(texto);
        return new ResponseEntity<Map<String, String>>(json, HttpStatus.OK);
    }
}
