package com.bootcamp.mssales.service;

import com.bootcamp.mssales.dto.ClientDTO;
import com.bootcamp.mssales.dto.ProductDTO;
import com.bootcamp.mssales.dto.SaleDTO;
import com.bootcamp.mssales.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ExternalClientService {

    @Autowired
    SaleRepository repository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    public Mono<ClientDTO> getClient(Integer id) {
        return webClientBuilder.build()
                .get()
                .uri("http://localhost:8002/clients/" + id)
                .retrieve()
                .bodyToMono(ClientDTO.class)
                ;
    }

    public Mono<ProductDTO> getProduct(Integer id) {
        return webClientBuilder.build()
                .get()
                .uri("http://localhost:8001/products/" + id)
                .retrieve()
                .bodyToMono(ProductDTO.class)
                ;
    }

    public void a(Integer idSale, Integer idClient, Integer idProduct) {
        Mono<ClientDTO> client = getClient(idClient);
        Mono<ProductDTO> product = getProduct(idProduct);
        SaleDTO saleDTO = new SaleDTO();
        saleDTO.setId(idSale);
        saleDTO.setClient(client);
        saleDTO.setProduct(product);
        

    }

}
