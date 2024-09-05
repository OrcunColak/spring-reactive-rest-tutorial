package com.colak.springtutorial.repository;

import com.colak.springtutorial.model.ApiRoute;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ApiRouteRepository {

    private final Map<String, ApiRoute> routes = new ConcurrentHashMap<>();

    public <S extends ApiRoute> Mono<S> save(S entity) {
        routes.put(entity.getId(), entity);
        return Mono.just(entity);
    }

    public Mono<ApiRoute> findById(String id) {
        ApiRoute route = routes.get(id);
        return route != null ? Mono.just(route) : Mono.empty();
    }

    public Flux<ApiRoute> findAll() {
        return Flux.fromIterable(routes.values());
    }

    public Mono<Void> delete(ApiRoute entity) {
        routes.remove(entity.getId());
        return Mono.empty();
    }

    // Other methods are left as no-op for simplicity
    public Mono<Void> deleteById(String id) {
        routes.remove(id);
        return Mono.empty();
    }

    public Mono<Void> deleteAll() {
        routes.clear();
        return Mono.empty();
    }

    public Mono<Long> count() {
        return Mono.just((long) routes.size());
    }

    public Flux<ApiRoute> findAllById(Iterable<String> ids) {
        List<ApiRoute> foundRoutes = new ArrayList<>();
        for (String id : ids) {
            ApiRoute route = routes.get(id);
            if (route != null) {
                foundRoutes.add(route);
            }
        }
        return Flux.fromIterable(foundRoutes);
    }

    public Mono<Boolean> existsById(String id) {
        return Mono.just(routes.containsKey(id));
    }
}

