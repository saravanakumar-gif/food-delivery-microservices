package com.fooddelivery.restaurantservice.dto.response;

import java.util.UUID;

public class MenuItemResponse {
	private UUID id;
    private UUID restaurantId;
    private String name;
    private String description;
    private Double price;
    private String category;
    private String imageUrl;
    private boolean available;
    private boolean veg;
    
    
    
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public boolean isAvailable() {
		return available;
	}
	public void setAvailable(boolean available) {
		this.available = available;
	}
	public boolean isVeg() {
		return veg;
	}
	public void setVeg(boolean veg) {
		this.veg = veg;
	}
	
	
	
	public MenuItemResponse(UUID id, UUID restaurantId, String name, String description, Double price, String category,
			String imageUrl, boolean available, boolean veg) {
		super();
		this.id = id;
		this.restaurantId = restaurantId;
		this.name = name;
		this.description = description;
		this.price = price;
		this.category = category;
		this.imageUrl = imageUrl;
		this.available = available;
		this.veg = veg;
	}
	
	
	
	public MenuItemResponse() {
		super();
	}
    
}
