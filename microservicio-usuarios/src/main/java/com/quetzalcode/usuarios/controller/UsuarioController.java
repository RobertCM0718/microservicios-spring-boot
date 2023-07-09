package com.quetzalcode.usuarios.controller;


import com.quetzalcode.commons.usuarios.entity.Usuario;
import com.quetzalcode.usuarios.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.PathParam;
import java.util.List;

@RestController
@RequestMapping("usuarios")
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping("/")
    public List<Usuario> findAll(){
        return usuarioService.findAll();
    }

    @GetMapping("/{id}")
    public Usuario findById(@PathVariable Long id){
        return usuarioService.findById(id);
    }

    @GetMapping(value = "/findByUsername",params = "username")
    public Usuario findByUsername(@PathParam("username") String username){
        return usuarioService.findByUsername(username);
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario crear(@RequestBody Usuario usuario){
        return usuarioService.save(usuario);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario modificar(@RequestBody Usuario usuario, @PathVariable Long id){
        return usuarioService.update(usuario,id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id){
        usuarioService.eliminar(id);
    }

}
