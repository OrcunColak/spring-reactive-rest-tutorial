package com.colak.springtutorial.handler;

import com.colak.springtutorial.model.ApiRoute;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApiRouteHandlerTest {

    // Spring Boot will create a `WebTestClient` for you,
    // already configure and ready to issue requests against "localhost:RANDOM_PORT"
    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testCreateApiRoute() {
        // Create a sample ApiRoute object
        ApiRoute apiRoute = new ApiRoute("1", "/sample-path", "sample-service", "http://example.com");

        // Send a POST request to create the ApiRoute
        webTestClient.post()
                .uri("/routes")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(apiRoute), ApiRoute.class)
                .exchange()
                .expectStatus().isOk()  // Verify that the status is 200 OK
                .expectHeader().contentType(MediaType.APPLICATION_JSON)  // Verify the Content-Type header
                .expectBody(ApiRoute.class)  // Verify the body contains the created ApiRoute
                .consumeWith(response -> {
                    ApiRoute createdRoute = response.getResponseBody();
                    assert createdRoute != null;
                    assert createdRoute.getId().equals("1");
                    assert createdRoute.getPath().equals("/sample-path");
                    assert createdRoute.getServiceId().equals("sample-service");
                    assert createdRoute.getUrl().equals("http://example.com");
                });
    }
}