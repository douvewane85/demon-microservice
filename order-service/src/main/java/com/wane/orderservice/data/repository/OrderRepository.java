package com.wane.orderservice.data.repository;

import com.wane.orderservice.data.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}