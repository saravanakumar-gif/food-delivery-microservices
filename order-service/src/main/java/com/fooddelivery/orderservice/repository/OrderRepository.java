package com.fooddelivery.orderservice.repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.GrantedAuthority;

import com.fooddelivery.orderservice.model.Order;

public interface OrderRepository extends JpaRepository<Order, UUID> {

	 List<Order> findByDeliveryBoyIdOrderByCreatedAtDesc(UUID userId);
	 List<Order> findByCustomerIdOrderByCreatedAtDesc(UUID userId);
	

}
