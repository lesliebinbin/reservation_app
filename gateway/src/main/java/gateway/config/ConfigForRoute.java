package gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigForRoute {
    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p
                        .path("/data/**")
                        .filters(f -> f.rewritePath("/data/(?<remains>.*)", "/${remains}"))
                        .uri("lb://mysql-db")
                        .id("data"))

                .route(p -> p.path("/auth/**")
                        .filters(f -> f.rewritePath("/auth/(?<remains>.*)", "/${remains}"))
                        .uri("lb://authentication")
                        .id("auth"))
                .route(p -> p.path("/notifications/**")
                        .filters(f -> f.rewritePath("/notifications/(?<remains>.*)", "/${remains}"))
                        .uri("lb://notifications")
                        .id("notifications"))

                .route(p -> p.path("/informative/**")
                        .filters(f -> f.rewritePath("/informative/(?<remains>.*)", "/${remains}"))
                        .uri("lb://informative-rest")
                        .id("informative"))
                .build();
    }
}
