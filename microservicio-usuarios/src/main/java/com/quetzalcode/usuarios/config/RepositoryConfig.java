package com.quetzalcode.usuarios.config;


import com.quetzalcode.commons.usuarios.entity.Rol;
import com.quetzalcode.commons.usuarios.entity.Usuario;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

@Configuration
public class RepositoryConfig {

    @Bean
    public RepositoryRestConfigurer repositoryRestConfigurer() {
        return RepositoryRestConfigurer.withConfig(config -> {
            config.exposeIdsFor(Usuario.class, Rol.class);
        });
    }

}
