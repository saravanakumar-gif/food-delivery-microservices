package com.fooddelivery.orderservice.dto.request;

import java.util.UUID;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class OrderItemRequest {
	@NotNull(message = "Menu item is required")
    private UUID menuItemId;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

	public UUID getMenuItemId() {
		return menuItemId;
	}

	public void setMenuItemId(UUID menuItemId) {
		this.menuItemId = menuItemId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public OrderItemRequest(@NotNull(message = "Menu item is required") UUID menuItemId,
			@NotNull(message = "Quantity is required") @Min(value = 1, message = "Quantity must be at least 1") Integer quantity) {
		super();
		this.menuItemId = menuItemId;
		this.quantity = quantity;
	}

	public OrderItemRequest() {
		super();
	}

}
