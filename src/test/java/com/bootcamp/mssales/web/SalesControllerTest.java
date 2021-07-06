package com.bootcamp.mssales.web;

import com.bootcamp.mssales.dto.ClientDTO;
import com.bootcamp.mssales.dto.ProductDTO;
import com.bootcamp.mssales.dto.SaleDTO;
import com.bootcamp.mssales.repository.SaleRepository;
import com.bootcamp.mssales.service.SaleService;
import com.bootcamp.mssales.utils.AppUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = SalesController.class)
@Import(SaleService.class)
class SalesControllerTest {

    @MockBean
    SaleRepository repository;

    @MockBean
    SaleService service;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void getAllSales() {

        Mockito.when(service.getClient(1))
                .thenReturn(new ClientDTO(1, "name", 98764512, "type", "address", "email", 987654321));

        Mockito.when(service.getProduct(1))
                .thenReturn(new ProductDTO(1, "nameProduct", "typeProduct"));

        SaleDTO clientDTO = new SaleDTO(1, service.getClient(1), service.getProduct(1));
        Mockito.when(repository.findAll())
                .thenReturn(Flux.just(clientDTO).map(AppUtils::dtoToEntity));

        webTestClient.get()
                .uri("/sales")
                .accept(MediaType.APPLICATION_NDJSON)
                .exchange()
                .expectStatus().isOk();
    }
}