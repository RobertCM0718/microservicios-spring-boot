package com.quetzalcode.usuarios.service;


import com.quetzalcode.commons.usuarios.entity.Usuario;

import java.util.List;

public interface IUsuarioService {
    public Usuario findByUsername(String username);
    public Usuario findById(Long id);
    public List<Usuario> findAll();
    public Usuario save(Usuario usuario);
    public Usuario update(Usuario usuario,Long id);
    public void eliminar(Long id);
}
