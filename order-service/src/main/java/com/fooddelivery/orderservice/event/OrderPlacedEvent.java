package com.fooddelivery.orderservice.event;

import java.io.Serializable;
import java.util.UUID;

public class OrderPlacedEvent implements Serializable {
	private UUID orderId;
    private UUID customerId;
    private String customerName;
    private String restaurantName;
    private Double total;

    public OrderPlacedEvent() {}
    public OrderPlacedEvent(UUID orderId, UUID customerId, String customerName, String restaurantName, Double total) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.restaurantName = restaurantName;
        this.total = total;
    }
    public UUID getOrderId() { return orderId; }
    public void setOrderId(UUID orderId) { this.orderId = orderId; }
    public UUID getCustomerId() { return customerId; }
    public void setCustomerId(UUID customerId) { this.customerId = customerId; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public String getRestaurantName() { return restaurantName; }
    public void setRestaurantName(String restaurantName) { this.restaurantName = restaurantName; }
    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }
}
