package com.fooddelivery.orderservice.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;



public class RestaurantResponse {
	private UUID id;
    private String name;
    private String status;   // "OPEN" or "CLOSED"
    private UUID ownerId;
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public UUID getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(UUID ownerId) {
		this.ownerId = ownerId;
	}
    
}
