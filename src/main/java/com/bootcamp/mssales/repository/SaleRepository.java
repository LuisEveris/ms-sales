package com.bootcamp.mssales.repository;

import com.bootcamp.mssales.entity.Sale;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepository extends ReactiveMongoRepository<Sale, Integer> {
}
