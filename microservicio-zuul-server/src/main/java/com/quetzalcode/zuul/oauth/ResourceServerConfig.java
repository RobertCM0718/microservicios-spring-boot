package com.quetzalcode.zuul.oauth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Base64;

@RefreshScope//Indica que es suceptible a cambios provenientes de la configuración git.
@EnableResourceServer
@Configuration
public class ResourceServerConfig extends ResourceServerConfigurerAdapter{

    @Value("${config.security.oauth.client.jwt.key}")//Esto vale quetzalcode.codigo.microservicios y esta en un archivo en git.
    private String jwtKey;
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenStore(tokenStore());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/api/security/oauth/token").permitAll()
                .antMatchers(HttpMethod.GET,"/api/productos/listar","/api/items/listar","/api/usuarios/usuarios/").permitAll()
                .antMatchers(HttpMethod.GET,"/api/productos/ver/{id}",
                                            "/api/items/ver/{id}/cantidad/{cantidad}",
                                            "/api/usuarios/usuarios/{id}").hasAnyRole("ADMIN","USER")
                .antMatchers("/api/productos/**","/api/items/**","/api/usuarios/**").hasRole("ADMIN")
                .anyRequest().authenticated()
//                .antMatchers(HttpMethod.POST,"/api/productos/crear","/api/items/crear","/api/usuarios/usuarios").hasRole("ADMIN")
//                .antMatchers(HttpMethod.PUT,"/api/productos/editar/{id}","/api/items/editar/{id}","/api/usuarios/usuarios/{id}").hasRole("ADMIN")
//                .antMatchers(HttpMethod.DELETE,"/api/productos/eliminar/{id}","/api/items/eliminar/{id}","/api/usuarios/usuarios/{id}").hasRole("ADMIN")
//                .anyRequest().authenticated();
                .and().cors().configurationSource(corsConfigurationSource());//Aqui registramos la configuración cors en spring security.
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {//Se crea la configuración del cors que se aplica a todas las rutas de spring security
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(Arrays.asList("*"));//Permite el acceso CORS de los clientes * es para todos tambien se puede usar: addAllowedOrigin("*")
        corsConfiguration.setAllowedMethods(Arrays.asList("POST","GET","PUT","DELETE","OPTIONS"));
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedHeaders(Arrays.asList("Authorization","Content-Type"));

        UrlBasedCorsConfigurationSource source= new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",corsConfiguration);//Se agrega la configuración para todas las urls del proyecto spring security, en este caso de Zuul.
        return source;
    }

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter(){//Este metodo esta destinando a configurar el cors no solo a nivel de spring security, si no a nivel global a toda la app en general.
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>(new CorsFilter(corsConfigurationSource()));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;//Con este metodo registramos el corsFilter a nivel global, no solo spring security si no a todoel enrutado de Zuul.
    }

    @Bean
    public JwtTokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
        tokenConverter.setSigningKey(Base64.getEncoder().encodeToString(jwtKey.getBytes()));
        return tokenConverter;
    }
}
