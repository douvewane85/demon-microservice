package com.wane.productservice.service;

import com.wane.productservice.data.dto.ProductRequest;
import com.wane.productservice.data.dto.ProductResponse;

import java.util.List;

public interface ProductService {
    void createProduct(ProductRequest productRequest);
    List<ProductResponse> getAllProducts() ;
}
