package com.quetzalcode.oauth.clients;

import com.quetzalcode.commons.usuarios.entity.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="microservicio-usuarios")
public interface UsuarioFeignClient {
    @GetMapping("/usuarios/search/findByUsername")
    public Usuario findByUserName(@RequestParam String username);
}
