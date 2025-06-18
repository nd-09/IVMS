package com.user.imvs.controller;

import com.user.imvs.dtos.AuthServiceResponse;
import com.user.imvs.dtos.LoginRequestDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/auth")
public class InventoryAuthController {

    private final WebClient.Builder webClientBuilder;

    @Value("${auth.service.url}")
    private String authServiceUrl;

    public InventoryAuthController(WebClient.Builder webClientBuilder){
        this.webClientBuilder = webClientBuilder;
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<AuthServiceResponse>> login(@RequestBody LoginRequestDTO request) {
        return webClientBuilder.build()
                .post()
                .uri(authServiceUrl + "/api/v1/auth/login")
                .bodyValue(request)
                .retrieve()
                .toEntity(AuthServiceResponse.class)
                .onErrorResume(error -> Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
    }
}

