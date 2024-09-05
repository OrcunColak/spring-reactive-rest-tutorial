package com.colak.springtutorial.handler;


import com.colak.springtutorial.model.ApiRoute;
import com.colak.springtutorial.repository.ApiRouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ApiRouteHandler {

    private final ApiRouteRepository apiRouteRepository;


    public Mono<ServerResponse> create(ServerRequest request) {
        Mono<ApiRoute> apiRouteMono = request.bodyToMono(ApiRoute.class);
        return apiRouteMono.flatMap(apiRouteRepository::save)
                .flatMap(savedRoute -> ServerResponse.ok().bodyValue(savedRoute))
                .switchIfEmpty(ServerResponse.badRequest().build());
    }

    public Mono<ServerResponse> getAll() {
        Flux<ApiRoute> routes = apiRouteRepository.findAll();
        return ServerResponse.ok().body(routes, ApiRoute.class);
    }

    public Mono<ServerResponse> getById(ServerRequest request) {
        String routeId = request.pathVariable("routeId");
        return apiRouteRepository.findById(routeId)
                .flatMap(apiRoute -> ServerResponse.ok().bodyValue(apiRoute))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        String routeId = request.pathVariable("routeId");
        Mono<ApiRoute> apiRouteMono = request.bodyToMono(ApiRoute.class);

        return apiRouteRepository.findById(routeId)
                .flatMap(existingRoute -> apiRouteMono
                        .flatMap(updatedRoute -> {
                            existingRoute.setPath(updatedRoute.getPath());
                            existingRoute.setServiceId(updatedRoute.getServiceId());
                            existingRoute.setUrl(updatedRoute.getUrl());
                            return apiRouteRepository.save(existingRoute);
                        })
                        .flatMap(savedRoute -> ServerResponse.ok().bodyValue(savedRoute))
                )
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        String routeId = request.pathVariable("routeId");
        return apiRouteRepository.findById(routeId)
                .flatMap(existingRoute -> apiRouteRepository.delete(existingRoute)
                        .then(ServerResponse.noContent().build()))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}


