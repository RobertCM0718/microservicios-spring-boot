package com.quetzalcode.oauth.security.event;

import brave.Tracer;
import com.quetzalcode.commons.usuarios.entity.Usuario;
import com.quetzalcode.oauth.services.IUsuarioService;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AuthenticationSuccessErrorHandler implements AuthenticationEventPublisher {

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private Tracer tracer;

    private static Logger LOG = LoggerFactory.getLogger(AuthenticationSuccessErrorHandler.class);
    @Override
    public void publishAuthenticationSuccess(Authentication authentication) {
        if(authentication.getDetails() instanceof WebAuthenticationDetails){//Validamos que la instancia sea del loggin de un user y no del clientId
            return;
        }
//        if(authentication.getName().equalsIgnoreCase("frontEndApp")){////Validamos que la instancia sea del loggin de un user y no del clientId
//            return;
//        }
        UserDetails user =(UserDetails) authentication.getPrincipal();
        LOG.info("Succes Login: "+ user.getUsername());

        Usuario usuario = usuarioService.findByUsername(authentication.getName());
        if (usuario.getIntentos()!=null && usuario.getIntentos()  > 0) {
            usuario.setIntentos(0);
            usuarioService.update(usuario,usuario.getId());
        }
    }

    @Override
    public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
        String mensajeError = "Error en el Login: "+ exception.getMessage();
        LOG.info(mensajeError);
        try {
            StringBuilder errors = new StringBuilder();
            errors.append(mensajeError);

            Usuario usuario = usuarioService.findByUsername(authentication.getName());
            if(Objects.isNull(usuario)){
                throw  new UsernameNotFoundException(String.format("El usuario %s no existe en el sistema.",authentication.getName()));
            }
            if (usuario.getIntentos()== null) {
                usuario.setIntentos(0);
            }

            usuario.setIntentos(usuario.getIntentos()+1);
            LOG.info(String.format("El usuario %s lleva %s intentos.",usuario.getUsername(),usuario.getIntentos()));
            errors.append(" - " + String.format("El usuario %s lleva %s intentos.",usuario.getUsername(),usuario.getIntentos()));

            if(usuario.getIntentos() >= 3){
                String maximosIntentos = String.format("El usuario %s es deshabilitado por máximo de intentos.",usuario.getUsername());
                LOG.error(maximosIntentos);
                errors.append(" - " + maximosIntentos);
                usuario.setActivo(false);
            }
            usuarioService.update(usuario,usuario.getId());
            tracer.currentSpan().tag("error.mensaje",errors.toString());
        }catch (FeignException e){
            LOG.error(String.format("El usuario %s no existe en el sistema.",authentication.getName()));
//            LOG.error(e.getMessage());
        }catch (UsernameNotFoundException e){
            LOG.error(String.format("El usuario %s no existe en el sistema.",authentication.getName()));
        }


    }
}
