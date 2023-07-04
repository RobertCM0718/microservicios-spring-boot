package com.quetzalcode.oauth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.Arrays;

@RefreshScope//Indica que es suceptible a cambios provenientes de la configuración git.
@EnableAuthorizationServer
@Configuration
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Value("${config.security.oauth.client.id}")//Esto vale frontEndApp y esta en un archivo en git.
    private String clientId;

    @Value("${config.security.oauth.client.secret}")//Esto vale 12345 y esta en un archivo en git.
    private String passwordClient;

    @Value("${config.security.oauth.client.jwt.key}")//Esto vale quetzalcode.codigo.microservicios y esta en un archivo en git.
    private String jwtKey;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private InfoAdicionalToken infoAdicionalToken;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()")//tokenKeyAccess es el endpoint que genera el token y cualquiera puede acceder a esta ruta con permitAll
                .checkTokenAccess("isAuthenticated()");//checkTokenAccess valida el token , isAuthenticated permite validar que el cliente este autenticado.
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory().withClient(clientId)//Registramos nuestra app AngularApp , ReactApp , VueApp etc...
                .secret(passwordEncoder.encode(passwordClient))//Contraseña de la app registrada arriba
                .scopes("read","write")//Permisos de la app registrada
                .authorizedGrantTypes("password","refresh_token")//password: El token lo va a obtener por password (con credenciales del usuario) , refresh_token:Este token permite obtener un nuevo token justo antes de que caduque el actual
                .accessTokenValiditySeconds(3600)//Tiempo en que caduda el token
                .refreshTokenValiditySeconds(3600)//Tiempo en el que se refresca el token
                .and()//Para configurar otro cliente se ocupa el metodo .add() seguido de la nueva configuración.
                .withClient("androidApp")//Registramos otra app AngularApp , ReactApp , VueApp etc...
                .secret(passwordEncoder.encode("12345"))//Contraseña de la app registrada arriba
                .scopes("read","write")//Permisos de la app registrada
                .authorizedGrantTypes("password","refresh_token")//password: El token lo va a obtener por password (con credenciales del usuario) , refresh_token:Este token permite obtener un nuevo token justo antes de que caduque el actual
                .accessTokenValiditySeconds(3600)//Tiempo en que caduda el token
                .refreshTokenValiditySeconds(3600);//Tiempo en el que se refresca el token
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(infoAdicionalToken,accessTokenConverter()));

        endpoints.authenticationManager(authenticationManager)
                .tokenStore(tokenStore())
                .accessTokenConverter(accessTokenConverter())
                .tokenEnhancer(tokenEnhancerChain);
    }

    @Bean
    public JwtTokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
        tokenConverter.setSigningKey(jwtKey);
        return tokenConverter;
    }
}
