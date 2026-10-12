package com.fooddelivery.orderservice.feignclient;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.fooddelivery.orderservice.dto.response.MenuItemResponse;
import com.fooddelivery.orderservice.dto.response.RestaurantResponse;

@FeignClient(name = "RESTAURANT-SERVICE")
public interface RestaurantServiceClient {
	 @GetMapping("/api/restaurants/{id}")
	    FeignApiResponse<RestaurantResponse> getRestaurantById(@PathVariable UUID id);

	    @GetMapping("/api/restaurants/menu-items/{id}")
	    FeignApiResponse<MenuItemResponse> getMenuItemById(@PathVariable UUID id);
}