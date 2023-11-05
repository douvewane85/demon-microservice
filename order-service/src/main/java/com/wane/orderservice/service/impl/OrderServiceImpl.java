package com.wane.orderservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wane.orderservice.data.dto.InventoryResponse;
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
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;
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

        List<String> skuCodes = order.getOrderLineItemsList().stream()
                .map(OrderLineItems::getSkuCode)
                .toList();

        // Call Inventory Service, and place order if product is in
        // stock

        InventoryResponse[] inventoryResponsArray = webClientBuilder.build().get()
                .uri("http://localhost:9003/api/v1/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCodes", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        boolean allProductsInStock = Arrays.stream(inventoryResponsArray)
                .allMatch(InventoryResponse::isInStock);

        if(allProductsInStock){
            orderRepository.save(order);
        } else {
            throw new IllegalArgumentException("Product is not in stock, please try again later");
        }
    }
}
