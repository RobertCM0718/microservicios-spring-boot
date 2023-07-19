package com.quetzalcode.usuarios.service;


import com.quetzalcode.commons.usuarios.entity.Usuario;
import com.quetzalcode.usuarios.repository.UsuarioRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl implements IUsuarioService{

    @Autowired
	private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Override
    public Usuario findByUsername(String username) {
        return usuarioRepository.findByUsername(username).orElseThrow();
    }

    @Override
    public Usuario findById(Long id) {
//        return usuarioRepository.findById(id).orElse(null);
        return usuarioRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario save(Usuario usuario) {
         usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario update(Usuario usuario, Long id) {
        Usuario usuarioBDD = findById(id);
        if (usuarioBDD!=null){
            usuario.setId(id);
            BeanUtils.copyProperties(usuario,usuarioBDD);
//            usuarioBDD.setActivo(usuario.getActivo());
//            usuarioBDD.setPassword(usuario.getPassword());
//            usuarioBDD.setUsername(usuario.getUsername());
//            usuarioBDD.setNombre(usuario.getNombre());
//            usuarioBDD.setApellido(usuario.getApellido());
//            usuarioBDD.setEmail(usuario.getEmail());
//            usuarioBDD.setRoles(usuario.getRoles());
//            usuarioBDD.setIntentos(usuario.getIntentos());
            usuarioBDD = usuarioRepository.save(usuarioBDD);
        }
        return usuarioBDD;
    }

    @Override
    public void eliminar(Long id) {
        usuarioRepository.deleteById(id);
    }
}
