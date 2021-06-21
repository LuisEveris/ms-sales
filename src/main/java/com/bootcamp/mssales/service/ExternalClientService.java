package com.bootcamp.mssales.service;

import com.bootcamp.mssales.dto.ClientDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

public class ExternalClientService {
    @Autowired
    private WebClient.Builder webClientBuilder;
    private static final String TYPE = MediaType.APPLICATION_JSON_VALUE;
    private static final String BASE_URL = "http://localhost:8002/clients";
    private static final String USER_AGENT = "Spring 5 webClient";

    public Flux<ClientDTO> getAllClients() {
        return webClientBuilder.build()
                .get()
                .uri("http://localhost:8002/clients")
                .retrieve()
                .bodyToFlux(ClientDTO.class)
                ;
    }

}
