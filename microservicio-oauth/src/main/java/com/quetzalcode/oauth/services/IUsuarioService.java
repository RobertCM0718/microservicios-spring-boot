package com.quetzalcode.oauth.services;

import com.quetzalcode.commons.usuarios.entity.Usuario;

public interface IUsuarioService {
    public Usuario findByUsername(String username);
}
