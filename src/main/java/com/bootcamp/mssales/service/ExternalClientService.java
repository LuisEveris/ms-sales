package com.bootcamp.mssales.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

public class ExternalClientService {
    private final WebClient webClient;
    private static final String TYPE = MediaType.APPLICATION_JSON_VALUE;
    private static final String BASE_URL = "http://localhost:8002/clients";
    private static final String USER_AGENT = "Spring 5 webClient";

    public ExternalClientService() {
        this.webClient = WebClient.builder()
        .baseUrl(BASE_URL)
        .defaultHeader(HttpHeaders.CONTENT_TYPE, TYPE)
        .build();
    }
}
