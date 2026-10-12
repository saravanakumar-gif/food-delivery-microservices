package com.fooddelivery.orderservice.model;

import java.util.UUID;
import jakarta.persistence.*;

@Entity
@Table(name = "order_items")
public class OrderItem {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id")
	private Order order;

	private UUID menuItemId;
	private String menuItemName;
	private Integer quantity;
	private Double priceAtOrder;

	public UUID getId() { return id; }
	public void setId(UUID id) { this.id = id; }
	public Order getOrder() { return order; }
	public void setOrder(Order order) { this.order = order; }
	public UUID getMenuItemId() { return menuItemId; }
	public void setMenuItemId(UUID menuItemId) { this.menuItemId = menuItemId; }
	public String getMenuItemName() { return menuItemName; }
	public void setMenuItemName(String menuItemName) { this.menuItemName = menuItemName; }
	public Integer getQuantity() { return quantity; }
	public void setQuantity(Integer quantity) { this.quantity = quantity; }
	public Double getPriceAtOrder() { return priceAtOrder; }
	public void setPriceAtOrder(Double priceAtOrder) { this.priceAtOrder = priceAtOrder; }
}