package com.cllaque.compositems.config;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.CompositeReactiveHealthContributor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.ReactiveHealthContributor;
import org.springframework.boot.actuate.health.ReactiveHealthIndicator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Configuration
public class HealthCheckConfig {
    private WebClient.Builder builder;
    @Value("${eurekaurl.personams}")
    private String personaBaseUrl;
    @Value("${eurekaurl.cuentams}")
    private String cuentaBaseUrl;
    @Value("${eurekaurl.movimientoms}")
    private String movimientoBaseUrl;

    public HealthCheckConfig(WebClient.Builder builder){
        this.builder = builder;
    }

    @Bean
    public ReactiveHealthContributor coreServicesHealth(){
        var healthRegistry = new HashMap<String, ReactiveHealthIndicator>();
        healthRegistry.put("personams", ()->this.getPersonaHealth());
        healthRegistry.put("cuentams", ()->this.getCuentaHealth());
        healthRegistry.put("movimientoms", ()->this.getMovimientoHealth());

        return CompositeReactiveHealthContributor.fromMap(healthRegistry);
    }

    private Mono<Health> getPersonaHealth(){
        return this.getHealth(personaBaseUrl);
    }

    private Mono<Health> getCuentaHealth(){
        return this.getHealth(cuentaBaseUrl);
    }

    private Mono<Health> getMovimientoHealth(){
        return this.getHealth(movimientoBaseUrl);
    }

    private Mono<Health> getHealth(String baseUrl){
        return this.builder.baseUrl(baseUrl).build()
            .get()
            .uri("/actuator/health")
            .retrieve()
            .bodyToMono(String.class)
            .map(s-> new Health.Builder().up().build())
            .onErrorResume(e-> Mono.just(new Health.Builder().down(e).build()));
    }
}
