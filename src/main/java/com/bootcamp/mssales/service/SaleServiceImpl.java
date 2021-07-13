package com.bootcamp.mssales.service;

import com.bootcamp.mssales.dto.ClientDTO;
import com.bootcamp.mssales.dto.ProductDTO;
import com.bootcamp.mssales.dto.SaleDTO;
import com.bootcamp.mssales.repository.SaleRepository;
import com.bootcamp.mssales.utils.AppUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@Qualifier("SaleServiceImpl")
public class SaleServiceImpl implements SalesService {

    @Autowired
    SaleRepository repository;

    @Autowired
    private WebClient.Builder webClient;

    public Flux<SaleDTO> getAllSales() {
        log.debug("getAll sales | method from SaleService");
        return repository.findAll().map(AppUtils::entityToDTO);
    }

    public Mono<SaleDTO> getSale(Integer id) {
        log.debug("get 1 sale by id | method from ProductService {}", id);
        return repository.findById(id).map(AppUtils::entityToDTO);
    }

    public Mono<SaleDTO> saveSale(Integer idSale, Integer idClient, Integer idProduct) {
        log.debug("save 1 sale by id | method from SaleService [{}], [{}], [{}]", idSale, idClient, idProduct);
        ProductDTO product = getProduct(idProduct);
        ClientDTO client = getClient(idClient);
        SaleDTO saleDTO = new SaleDTO(idSale, client, product);

        Mono<SaleDTO> saleDTOMono = Mono.just(saleDTO);


        return saleDTOMono.map(AppUtils::dtoToEntity)
                .flatMap(repository::insert)
                .map(AppUtils::entityToDTO);

    }



    public ProductDTO getProduct(Integer idProduct) {
        return webClient.build()
                .get()
                .uri("https://lel-bank.azurewebsites.net/products/{idProduct}", idProduct)
//                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(ProductDTO.class)
                .switchIfEmpty(Mono.error(new Exception("not found the product id " + idProduct)))
                .share()
                .block()
                ;
    }

    public ClientDTO getClient(Integer id) {
        return webClient.build()
                .get()
                .uri("https://lel-bank.azurewebsites.net/clients/{id}", id)
//                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(ClientDTO.class)
                .switchIfEmpty(Mono.error(new Exception("not found the product id " + id)))
                .share()
                .block()
                ;
    }
}
