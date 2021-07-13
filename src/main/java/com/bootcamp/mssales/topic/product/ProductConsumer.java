package com.bootcamp.mssales.topic.product;

import com.bootcamp.mssales.entity.Client;
import com.bootcamp.mssales.entity.Product;
import com.bootcamp.mssales.repository.ClientRepository;
import com.bootcamp.mssales.repository.ProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class ProductConsumer {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ClientRepository clientRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "product-topic", groupId = "com.bootcamp")
    public Disposable retrieveCustomer(String productSent) throws JsonProcessingException {
        log.info("this is the product retrieved: [{}]", productSent);

        Product product = objectMapper.readValue(productSent, Product.class);

        return Mono.just(product)
                .log()
                .flatMap(productRepository::save)
                .subscribe();
    }

    @KafkaListener(topics = "client-topic", groupId = "com.bootcamp")
    public Disposable retrieveClient(String clientSent) throws JsonProcessingException {
        log.info("this is the client retrieved: [{}]", clientSent);

        Client client = objectMapper.readValue(clientSent, Client.class);

        return Mono.just(client)
                .log()
                .flatMap(clientRepository::save)
                .subscribe();
    }
}
