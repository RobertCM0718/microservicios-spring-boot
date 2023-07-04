package com.quetzalcode.usuarios.controller;


import com.quetzalcode.commons.usuarios.entity.Usuario;
import com.quetzalcode.usuarios.service.IUsuarioService;
import jakarta.ws.rs.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("usuarios")
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping("/")
    public List<Usuario> findAll(){
        return usuarioService.findAll();
    }
    @GetMapping(value = "/findByUsername",params = "username")
    public Usuario findByUsername(@PathParam("username") String username){
        return usuarioService.findByUsername(username);
    }

}
