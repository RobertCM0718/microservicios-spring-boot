package com.quetzalcode.gateway.filters.factory;

import com.quetzalcode.gateway.filters.GlobalFilterCustom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class EjemploGatewayFilterFactory extends AbstractGatewayFilterFactory<EjemploGatewayFilterFactory.Configuracion> {

    public EjemploGatewayFilterFactory() {
        super(Configuracion.class);
    }

    private static Logger LOG = LoggerFactory.getLogger(EjemploGatewayFilterFactory.class);

    @Override
    public GatewayFilter apply(Configuracion config) {
        return new OrderedGatewayFilter((exchange, chain) -> {
            LOG.info("Ejecutando pre gateway filter factory: "+config.mensaje);
            return chain.filter(exchange).then(Mono.fromRunnable(() ->{
                Optional.ofNullable(config.cookieValor).ifPresent( valor -> {
                    exchange.getResponse().addCookie(ResponseCookie.from(config.cookieNombre,valor).build());
                });
                LOG.info("Ejecutando post gateway filter factory"+config.mensaje);
            }));
        },2);
    }

    @Override
    public String name() {
        return "EjemploCookie";
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("mensaje","cookieNombre","cookieValor");
    }

    public static class Configuracion {
        public Configuracion() {
        }

        private String mensaje;
        private String cookieValor;
        private String cookieNombre;

        public String getMensaje() {
            return mensaje;
        }

        public void setMensaje(String mensaje) {
            this.mensaje = mensaje;
        }

        public String getCookieValor() {
            return cookieValor;
        }

        public void setCookieValor(String cookieValor) {
            this.cookieValor = cookieValor;
        }

        public String getCookieNombre() {
            return cookieNombre;
        }

        public void setCookieNombre(String cookieNombre) {
            this.cookieNombre = cookieNombre;
        }
    }
}
