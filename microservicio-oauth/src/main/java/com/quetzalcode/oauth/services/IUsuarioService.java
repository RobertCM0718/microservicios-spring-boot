package com.quetzalcode.oauth.services;

import com.quetzalcode.commons.usuarios.entity.Usuario;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface IUsuarioService {
    public Usuario findByUsername(String username);
    public Usuario update(Usuario usuario, Long id);
}
