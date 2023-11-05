package com.wane.orderservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wane.orderservice.data.dto.OrderLineItemsDto;
import com.wane.orderservice.data.dto.OrderRequest;
import com.wane.orderservice.data.entity.Order;
import com.wane.orderservice.data.entity.OrderLineItems;
import com.wane.orderservice.data.repository.OrderRepository;
import com.wane.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(orderLineItemsDto -> objectMapper.convertValue(orderLineItemsDto, OrderLineItems.class))
                .toList();
          order.setOrderLineItemsList(orderLineItems);
          orderRepository.save(order);
    }
}
