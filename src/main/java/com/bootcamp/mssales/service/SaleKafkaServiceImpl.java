package com.bootcamp.mssales.service;

import com.bootcamp.mssales.dto.SaleDTO;
import com.bootcamp.mssales.entity.Client;
import com.bootcamp.mssales.entity.Product;
import com.bootcamp.mssales.repository.ClientRepository;
import com.bootcamp.mssales.repository.ProductRepository;
import com.bootcamp.mssales.repository.SaleRepository;
import com.bootcamp.mssales.utils.AppUtils;
import com.bootcamp.mssales.utils.ClientAppUtils;
import com.bootcamp.mssales.utils.ProductAppUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@Qualifier("SaleKafkaServiceImpl")
public class SaleKafkaServiceImpl implements SalesService {

    @Autowired
    private SaleRepository repository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ClientRepository clientRepository;

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

        Product product = Mono.just(idProduct).flatMap(productRepository::findById).block();
        Client client = Mono.just(idProduct).flatMap(clientRepository::findById).block();

        SaleDTO saleDTO = new SaleDTO(idSale, ClientAppUtils.entityToDTO(client), ProductAppUtils.entityToDTO(product));

        return Mono.just(saleDTO).map(AppUtils::dtoToEntity)
                .flatMap(repository::insert)
                .map(AppUtils::entityToDTO);
    }

}
