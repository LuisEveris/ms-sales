package com.bootcamp.mssales.repository;

import com.bootcamp.mssales.entity.Client;
import com.bootcamp.mssales.entity.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends ReactiveMongoRepository<Client, Integer> {
}
