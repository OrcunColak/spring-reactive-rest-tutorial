package com.colak.springtutorial.config;

import com.colak.springtutorial.handler.ApiRouteHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;

@Configuration(proxyBeanMethods = false)
public class RouterFunctionConfig {

    // http://localhost:8080/routes
    // http://localhost:8080/routes/1
    @Bean
    public RouterFunction<ServerResponse> route(ApiRouteHandler apiRouteHandler) {
        return RouterFunctions.nest(path("/routes"),
                RouterFunctions.route(POST("").and(accept(APPLICATION_JSON)), apiRouteHandler::create)
                        .andRoute(GET(""), apiRouteHandler::getAll)
                        .andRoute(GET("/{routeId}"), apiRouteHandler::getById)
                        .andRoute(PUT("/{routeId}").and(accept(APPLICATION_JSON)), apiRouteHandler::update)
                        .andRoute(DELETE("/{routeId}"), apiRouteHandler::delete)
        );
    }

}
