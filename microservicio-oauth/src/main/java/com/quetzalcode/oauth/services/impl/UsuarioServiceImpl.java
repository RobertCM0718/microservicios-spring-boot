package com.quetzalcode.oauth.services.impl;

import com.quetzalcode.commons.usuarios.entity.Usuario;
import com.quetzalcode.oauth.clients.UsuarioFeignClient;
import com.quetzalcode.oauth.services.IUsuarioService;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements IUsuarioService, UserDetailsService {

    private Logger LOG = LoggerFactory.getLogger(UsuarioServiceImpl.class);

    @Autowired
    private UsuarioFeignClient usuarioFeignClient;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        try {
            Usuario usuario = usuarioFeignClient.findByUserName(username);

            if(usuario == null){
                LOG.error("El usuario "+username+" no existe.");
                throw  new UsernameNotFoundException("El usuario "+username+" no existe.");
            }
            LOG.info("Usuario auntenticado: "+username);
            List<GrantedAuthority> authorities = usuario.getRoles()
                    .stream().map(rol -> new SimpleGrantedAuthority(rol.getNombre()))
                    .peek(authority -> LOG.info("Rol: "+authority.getAuthority()))
                    .collect(Collectors.toList());
            return new User(usuario.getUsername(),usuario.getPassword(),usuario.getActivo(),true,true,true,authorities);
        } catch (FeignException e) {
            LOG.error("El usuario "+username+" no existe.");
            throw  new UsernameNotFoundException("El usuario "+username+" no existe.");
        }
    }

    @Override
    public Usuario findByUsername(String username) {
        return usuarioFeignClient.findByUserName(username);
    }

    @Override
    public Usuario update(Usuario usuario, Long id) {
        return usuarioFeignClient.update(usuario,id);
    }
}
