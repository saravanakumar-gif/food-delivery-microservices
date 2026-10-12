package com.fooddelivery.orderservice.dto.response;

import java.util.UUID;

public class OrderItemResponse {
	private UUID id;
    private UUID menuItemId;
    private String menuItemName;
    private Integer quantity;
    private Double priceAtOrder;
    private Double itemTotal;
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public UUID getMenuItemId() {
		return menuItemId;
	}
	public void setMenuItemId(UUID menuItemId) {
		this.menuItemId = menuItemId;
	}
	public String getMenuItemName() {
		return menuItemName;
	}
	public void setMenuItemName(String menuItemName) {
		this.menuItemName = menuItemName;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Double getPriceAtOrder() {
		return priceAtOrder;
	}
	public void setPriceAtOrder(Double priceAtOrder) {
		this.priceAtOrder = priceAtOrder;
	}
	public Double getItemTotal() {
		return itemTotal;
	}
	public void setItemTotal(Double itemTotal) {
		this.itemTotal = itemTotal;
	}
	public OrderItemResponse(UUID id, UUID menuItemId, String menuItemName, Integer quantity, Double priceAtOrder,
			Double itemTotal) {
		super();
		this.id = id;
		this.menuItemId = menuItemId;
		this.menuItemName = menuItemName;
		this.quantity = quantity;
		this.priceAtOrder = priceAtOrder;
		this.itemTotal = itemTotal;
	}
	public OrderItemResponse() {
		super();
	}
    
    
}
