package com.fooddelivery.orderservice.dto.response;

import java.util.UUID;

public class MenuItemResponse {
	private UUID id;
    private UUID restaurantId;
    private String name;
    private Double price;
    private boolean available;
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public UUID getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(UUID restaurantId) {
		this.restaurantId = restaurantId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public boolean isAvailable() {
		return available;
	}
	public void setAvailable(boolean available) {
		this.available = available;
	}
    
    
    
    
}
