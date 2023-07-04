package com.quetzalcode.usuarios.service;


import com.quetzalcode.commons.usuarios.entity.Usuario;

import java.util.List;

public interface IUsuarioService {
    public Usuario findByUsername(String username);

    public List<Usuario> findAll();
}
