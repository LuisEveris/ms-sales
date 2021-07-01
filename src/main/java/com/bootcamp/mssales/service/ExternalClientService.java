package com.bootcamp.mssales.service;

import com.bootcamp.mssales.dto.ClientDTO;
import com.bootcamp.mssales.dto.ProductDTO;
import com.bootcamp.mssales.dto.SaleDTO;
import com.bootcamp.mssales.repository.SaleRepository;
import com.bootcamp.mssales.utils.AppUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class ExternalClientService {

    @Autowired
    SaleRepository repository;

    //    @Autowired
    private WebClient webClient = WebClient.builder()
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();

    public ClientDTO getClient(Integer id) {
        return webClient
                .get()
                .uri("http://ms-client:8002/clients/{id}", id)
                .retrieve()
                .bodyToMono(ClientDTO.class)
                .switchIfEmpty(Mono.error(new Exception("not found the product id " + id)))
                .share()
                .block()
                ;
    }

    public ProductDTO getProduct(Integer idProduct) {
        return webClient
                .get()
                .uri("http://ms-product:8001/products/{id}", idProduct)
                .retrieve()
                .bodyToMono(ProductDTO.class)
                .switchIfEmpty(Mono.error(new Exception("not found the product id " + idProduct)))
                .share()
                .block()
                ;
    }

    public void a(Integer idSale, Integer idClient, Integer idProduct) {
        ClientDTO client = getClient(idClient);
        ProductDTO product = getProduct(idProduct);
        SaleDTO saleDTO = new SaleDTO();
        saleDTO.setId(idSale);
        saleDTO.setClient(client);
        saleDTO.setProduct(product);


    }

    public Flux<SaleDTO> getAllSales() {
        log.debug("getAll sales | method from ProductService");
        return repository.findAll().map(AppUtils::entityToDTO);
    }

    public Mono<SaleDTO> getSale(Integer id) {
        log.debug("get 1 dale by id | method from ProductService {}", id);
        return repository.findById(id).map(AppUtils::entityToDTO);
    }

    public Mono<SaleDTO> saveSale(Integer idSale, Integer idClient, Integer idProduct) {
        ClientDTO client = getClient(idClient);
        ProductDTO product = getProduct(idProduct);
        SaleDTO saleDTO = new SaleDTO(idSale, client, product);

        Mono<SaleDTO> saleDTOMono = Mono.just(saleDTO);


        return saleDTOMono.map(AppUtils::dtoToEntity)
                .flatMap(repository::insert)
                .map(AppUtils::entityToDTO);

    }


}
