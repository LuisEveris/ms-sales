package com.bootcamp.mssales.web;

import com.bootcamp.mssales.dto.DTO;
import com.bootcamp.mssales.dto.SaleDTO;
import com.bootcamp.mssales.service.SaleServiceImpl;
import com.bootcamp.mssales.service.SalesService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//@RequestParam is for query param
@Slf4j
@RestController
@RequestMapping("/sales")
public class SalesController {

    @Autowired
    @Qualifier("SaleServiceImpl")
    SalesService service;

    @GetMapping(produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<SaleDTO> getAllSales(){
        log.info("gettin all sales");
        return service.getAllSales();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Mono<SaleDTO> getSaleById(@PathVariable Integer id) {
        log.info("getting a sale with ID [{}]", id);
        return service.getSale(id)
                .switchIfEmpty(Mono.error(new HttpClientErrorException(HttpStatus.BAD_REQUEST, "The Client was not saved.")));
    }

    @PostMapping
    @CircuitBreaker(name = "savingSale", fallbackMethod = "savingSale")
    public Mono<SaleDTO> saveSale(@RequestBody DTO dto) {
        log.info("saving a sale with ID[{}]", dto.getIdSale());
        return service.saveSale(dto.getIdSale(), dto.getIdClient(), dto.getIdProduct())
                .switchIfEmpty(Mono.error(new HttpClientErrorException(HttpStatus.BAD_REQUEST, "The Client was not saved.")));
    }

    //fallback
    public Mono<ResponseEntity<String>> savingSale(Exception e) {
        log.info("FallBackMethod");
        return Mono.just("Service is down. Please, try later." +
                "\nSend this message to the administrator"+e.getMessage())
                .map(p-> new ResponseEntity<>(p, HttpStatus.INTERNAL_SERVER_ERROR));
    }


}
