package com.quetzalcode.oauth.clients;

import com.quetzalcode.commons.usuarios.entity.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name="microservicio-usuarios")
public interface UsuarioFeignClient {
    @GetMapping("/usuarios/findByUsername")
    public Usuario findByUserName(@RequestParam String username);
    @PutMapping("/usuarios/{id}")
    public Usuario update(@RequestBody Usuario usuario, @PathVariable Long id);
}
