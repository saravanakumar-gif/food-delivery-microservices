package com.fooddelivery.orderservice.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fooddelivery.orderservice.model.OrderItem;



public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {

}
