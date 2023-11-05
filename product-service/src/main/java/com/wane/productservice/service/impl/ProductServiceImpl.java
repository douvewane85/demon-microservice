package com.wane.productservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wane.productservice.data.dto.ProductRequest;
import com.wane.productservice.data.dto.ProductResponse;
import com.wane.productservice.data.entity.Product;
import com.wane.productservice.data.repository.ProductRepository;
import com.wane.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl  implements ProductService {
    private final ProductRepository productRepository;
    @Autowired
    private ObjectMapper objectMapper;

    public void createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();

        productRepository.save(product);
        log.info("Product {} is saved", product.getId());
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(product -> {
          return   objectMapper.convertValue(product, ProductResponse.class);
        }).toList();
    }


}
