package com.quetzalcode.items.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class AppConfig {
    @Bean
    @LoadBalanced
    public RestTemplate registrarRestTemplate(){
        return new RestTemplate();
    }

    @Bean
    public Customizer<Resilience4JCircuitBreakerFactory> defaultCostuomizer(){
        return factory -> factory.configureDefault(id -> {
            return new Resilience4JConfigBuilder(id)
                    .circuitBreakerConfig(CircuitBreakerConfig.custom()
                            .slidingWindowSize(10)//indica el numero de peticiones a monitorear
                            .failureRateThreshold(50)//indica un umbral de error del 50%
                            .waitDurationInOpenState(Duration.ofSeconds(10L))//indica que el estado abierto de la corto es de 10s
                            .permittedNumberOfCallsInHalfOpenState(5)//indica que el numero de peticiones a monitorear en estado semi-abierto es de 5
                            .slowCallRateThreshold(50)//indica que el porcentaje de llamadas lentas es de 50%
                            .slowCallDurationThreshold(Duration.ofSeconds(2L))//indica que el tiempo de una llamada lenta es de maximo 2s
                            .build())
                    .timeLimiterConfig(TimeLimiterConfig.custom()
                            .timeoutDuration(Duration.ofSeconds(4L)).build())//indica que el timeout de una petición es de 4s
                    .build();
        });
    }
}
