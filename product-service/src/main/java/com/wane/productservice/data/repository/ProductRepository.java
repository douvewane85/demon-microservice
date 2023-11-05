package com.wane.productservice.data.repository;

import com.wane.productservice.data.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}