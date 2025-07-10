package com.ugovslima.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("shortener-service", r -> r
                        .path("/api/links/**")
                        .uri("http://localhost:8081"))
                .route("qrcode-service", r -> r
                        .path("/api/qrcode**")
                        .uri("http://localhost:8082"))
                .build();
    }
}
