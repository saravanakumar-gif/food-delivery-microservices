package com.fooddelivery.restaurantservice.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MenuItemRequest {
	 private String name;
	    private String description;
	    private Double price;
	    private String category;
	    private String imageUrl;
	    private boolean available;
	    private boolean veg;

	    @JsonCreator
	    public MenuItemRequest(
	            @JsonProperty("name") String name,
	            @JsonProperty("description") String description,
	            @JsonProperty("price") Double price,
	            @JsonProperty("category") String category,
	            @JsonProperty("imageUrl") String imageUrl,
	            @JsonProperty("available") boolean available,
	            @JsonProperty("veg") boolean veg) {
	        this.name = name;
	        this.description = description;
	        this.price = price;
	        this.category = category;
	        this.imageUrl = imageUrl;
	        this.available = available;
	        this.veg = veg;
	    }

	    public String getName() { return name; }
	    public String getDescription() { return description; }
	    public Double getPrice() { return price; }
	    public String getCategory() { return category; }
	    public String getImageUrl() { return imageUrl; }
	    public boolean isAvailable() { return available; }
	    public boolean isVeg() { return veg; }
}
