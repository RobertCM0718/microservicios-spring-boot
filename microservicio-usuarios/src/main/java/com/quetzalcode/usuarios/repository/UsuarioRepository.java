package com.quetzalcode.usuarios.repository;


import com.quetzalcode.commons.usuarios.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

//@RepositoryRestResource(path = "usuarios")
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

//    @RestResource(path = "findByUsername") //Sobre escribe el nombre del recurso
    public Optional<Usuario> findByUsername(String username); //Param sobre escribe el nombre del parametro

    @Query("SELECT u FROM Usuario u where u.username =:username")
    public Usuario obtenerPorUsername(@Param("username") String username);
}
