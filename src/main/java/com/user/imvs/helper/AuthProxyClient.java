package com.user.imvs.helper;

import com.user.imvs.dtos.RegisterRequestDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class AuthProxyClient {

    private final WebClient webClient;

    public AuthProxyClient(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://localhost:9090/api/v1/auth").build();
    }

    public void registerUser(RegisterRequestDTO request) {
        webClient.post()
                .uri("/register")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Void.class)
                .block(); // Blocking call, can change if using reactive
    }
}

