package com.wane.orderservice.service;

import com.wane.orderservice.data.dto.OrderRequest;

public interface OrderService {
    void placeOrder(OrderRequest orderRequest);
}
