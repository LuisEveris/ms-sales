package com.bootcamp.mssales.service;

import com.bootcamp.mssales.dto.SaleDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SalesService {
    Flux<SaleDTO> getAllSales();
    Mono<SaleDTO> getSale(Integer id);
    Mono<SaleDTO> saveSale(Integer idSale, Integer idClient, Integer idProduct);
}
