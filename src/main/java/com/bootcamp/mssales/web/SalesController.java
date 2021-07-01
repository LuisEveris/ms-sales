package com.bootcamp.mssales.web;

import com.bootcamp.mssales.dto.DTO;
import com.bootcamp.mssales.dto.SaleDTO;
import com.bootcamp.mssales.service.ExternalClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    ExternalClientService service;

    @GetMapping()
    public Flux<SaleDTO> getAllSales(){
        log.info("gettin all sales");
        return service.getAllSales();
    }

    @GetMapping("/{id}")
    public Mono<SaleDTO> getSaleById(@PathVariable Integer id) {
        log.info("getting a sale with ID [{}]", id);
        return service.getSale(id)
                .switchIfEmpty(Mono.error(new HttpClientErrorException(HttpStatus.BAD_REQUEST, "The Client was not saved.")));
    }

    @PostMapping
    public Mono<SaleDTO> saveSale(@RequestBody DTO dto) {
        log.info("saving a sale with ID[{}]", dto.getIdSale());
        return service.saveSale(dto.getIdSale(), dto.getIdClient(), dto.getIdProduct())
                .switchIfEmpty(Mono.error(new HttpClientErrorException(HttpStatus.BAD_REQUEST, "The Client was not saved.")));
    }

}
