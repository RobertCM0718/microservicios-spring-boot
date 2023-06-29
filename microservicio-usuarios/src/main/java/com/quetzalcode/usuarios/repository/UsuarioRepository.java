package com.quetzalcode.usuarios.repository;

import com.quetzalcode.usuarios.entity.Usuario;
import jakarta.ws.rs.Path;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "usuarios")
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    public Usuario findByUsername(String username);

    @Query("SELECT u FROM Usuario u where u.username =:username")
    public Usuario obtenerPorUsername(@Param("username") String username);
}
